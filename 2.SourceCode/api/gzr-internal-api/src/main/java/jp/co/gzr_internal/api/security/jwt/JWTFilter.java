package jp.co.gzr_internal.api.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.gzr_internal.api.repository.CustomProjectRepository;
import jp.co.gzr_internal.api.service.AccountService;
import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    /** The Constant ARRAY_API_PERMIT_ALL. */
    private static final List<String> ARRAY_API_PERMIT_ALL = Arrays.asList("/gzr-internal/api/authenticate",
        "/gzr-internal/api/reset-password");

    /** The Constant ARRAY_API_ROLE_BM. */
    private static final List<String> ARRAY_API_ROLE_ADMIN = Arrays.asList("/gzr-internal/api/request-ots/get-approval",
        "/gzr-internal/api/request-ots/cancel", "/gzr-internal/api/request-ots/update",
        "/gzr-internal/api/request-ots/detail", "/gzr-internal/api/request-ots/list",
        "/gzr-internal/api/request-ots/get-list-approval", "/gzr-internal/api/request-ots/export-list-approval",
        "/gzr-internal/api/employees/detail", "/gzr-internal/api/employees/list");

    /** The Constant ARRAY_API_ROLE_QA. */
    private static final List<String> ARRAY_API_ROLE_SUB_ADMIN = Arrays.asList("/gzr-internal/api/employees/detail",
        "/gzr-internal/api/employees/list");

    /** The Constant ARRAY_API_ROLE_PM_TL. */
    private static final List<String> ARRAY_API_ROLE_USER = Arrays.asList("/gzr-internal/api/request-ots/cancel",
        "/gzr-internal/api/request-ots/create", "/gzr-internal/api/request-ots/update",
        "/gzr-internal/api/request-ots/detail", "/gzr-internal/api/request-ots/list",
        "/gzr-internal/api/request-ots/get-list-approval", "/gzr-internal/api/request-ots/export-list-approval");

    private static final List<String> ARRAY_API_ROLE_QA = Arrays.asList("/gzr-internal/api/request-ots/get-approval",
        "/gzr-internal/api/request-ots/cancel", "/gzr-internal/api/request-ots/update",
        "/gzr-internal/api/request-ots/detail", "/gzr-internal/api/request-ots/list",
        "/gzr-internal/api/request-ots/get-list-approval", "/gzr-internal/api/request-ots/export-list-approval");

    
    /** The token provider. */
    private TokenProvider tokenProvider;

    /** The account service. */
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CustomProjectRepository customProjectRepository;

    /** The flag is first create. */
    private boolean flagIsFirstCreate = true;

    /**
     * Instantiates a new JWT filter.
     *
     * @param tokenProvider the token provider
     */
    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Creates the service.
     *
     * @param request the request
     */
    private void createService(ServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
            .getRequiredWebApplicationContext(servletContext);

        if (accountService == null) {
            accountService = webApplicationContext.getBean(AccountService.class);
        }
        
        if (customProjectRepository == null) {
            customProjectRepository = webApplicationContext.getBean(CustomProjectRepository.class);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        if (flagIsFirstCreate) {
            createService(servletRequest);
            flagIsFirstCreate = false;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Check if request had contain query param then return bad request
        if (request.getQueryString() != null && request.getQueryString().length() > 0) {
            createResponseResult(response, MessageContants.CONST_ERROR_CODE_BAD_REQUEST,
                HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String jwt = resolveToken(request);
        try {

            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {

                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Checks if is access by role
                if (!isAccessByRole(request.getRequestURI())) {
                    createResponseResult(response, MessageContants.CONST_ERROR_CODE_NOT_ACCESS,
                        HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } else if (!ARRAY_API_PERMIT_ALL.contains(request.getRequestURI())) {
                createResponseResult(response, MessageContants.CONST_ERROR_CODE_AUTHENTICATION_FAILURE,
                    HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (Exception e) {

            createResponseResult(response, MessageContants.CONST_ERROR_CODE_INTERNAL_SERVER_ERROR,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Resolve token.
     *
     * @param request the request
     * @return the string
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Contants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Contants.CONST_STR_BEARER)) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * Creates the response result.
     * 
     * @author VuDA
     * @param response the response
     * @param errorCode the error code
     * @param status the status
     */
    private void createResponseResult(HttpServletResponse response, String errorCode, int status) {

        try {

            Map<String, Object> responseError = ResponseCommon.createMapResponseError(errorCode);

            ObjectMapper mapper = new ObjectMapper();
            response.setContentType(Contants.CONST_STR_CONTENT_TYPE_JSON);
            response.setCharacterEncoding(Contants.CONST_STR_ENCODING_UTF8);
            response.setStatus(status);
            response.getWriter().write(mapper.writeValueAsString(responseError));
        } catch (Exception e) {
        }
    }

    /**
     * Checks if is access by role.
     *
     * @param uri the uri
     * @return true, if is access by role
     */
    private boolean isAccessByRole(String uri) {
        Integer role = Utils.getRoleFromLogin();
        // Check role ADMIN
        if (ARRAY_API_ROLE_ADMIN.contains(uri) && role.equals(Contants.CONST_ROLE_ADMIN)) {
            return role.equals(Contants.CONST_ROLE_ADMIN);
        }
        
     // Check role QA
        if (ARRAY_API_ROLE_QA.contains(uri) && role.equals(Contants.CONST_ROLE_QA)) {
            return role.equals(Contants.CONST_ROLE_QA);
        }

        // Check role SUB_ADMIN
        if (ARRAY_API_ROLE_SUB_ADMIN.contains(uri) && role.equals(Contants.CONST_ROLE_SUB_ADMIN)) {
            return role.equals(Contants.CONST_ROLE_SUB_ADMIN);
        }

        // Check role USER
        if (ARRAY_API_ROLE_USER.contains(uri) && role.equals(Contants.CONST_ROLE_USER)) {
            String employeeCode  = Utils.getEmployeeCodeFromLogin();
            List<ProjectAfterLoginDto> projectInfo = new ArrayList<>();
            projectInfo = customProjectRepository.getRoleOfProject(employeeCode);
            if (projectInfo != null && projectInfo.size() > 0) {
                return role.equals(Contants.CONST_ROLE_USER);
            } else {
                return false;
            }
            
        }

        // Default
        return true;
    }
}
