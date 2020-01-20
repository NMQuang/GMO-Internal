package jp.co.gzr_internal.api.web.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.gzr_internal.api.security.jwt.TokenProvider;
import jp.co.gzr_internal.api.service.AccountService;
import jp.co.gzr_internal.api.service.dto.request.ChangePasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ForgotPasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.LoginRequestDto;
import jp.co.gzr_internal.api.web.rest.validator.ChangePasswordValidator;
import jp.co.gzr_internal.api.web.rest.validator.ForgotPasswordValidator;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    /** The m user service. */
    private final AccountService accountService;
    
    private final ChangePasswordValidator changePasswordValidator;

    private final ForgotPasswordValidator forgotPasswordValidator;
    /**
     * Instantiates a new user JWT controller.
     *
     * @param tokenProvider the token provider
     * @param authenticationManager the authentication manager
     * @param mUserService the m user service
     */
    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
        AccountService mCompanyService, ChangePasswordValidator changePasswordValidator, ForgotPasswordValidator forgotPasswordValidator) {
        this.accountService = mCompanyService;
        this.changePasswordValidator= changePasswordValidator;
        this.forgotPasswordValidator= forgotPasswordValidator;
    }
    
    @InitBinder({"changePasswordRequestDto"})
    public void setupChangePasswordRequestBinder(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }
    
    @InitBinder({"forgotPasswordRequestDto"})
    public void setupForgotPasswordRequestBinder(WebDataBinder binder) {
        binder.addValidators(forgotPasswordValidator);
    }

    /**
     * Authorize.
     *
     * @param loginDto the login dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<?> executeAuthorize(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return accountService.login(loginRequestDto);
    }
    /**
     * Execute log out.
     *
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping("/logout")
    @Timed
    public ResponseEntity<?> executeLogOut() throws Exception {
        return accountService.logout();
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<?> executeRequestPasswordReset(
        @Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) throws Exception {

        // Execute reset password and send mail notify for current email
        return  accountService.requestPasswordReset(forgotPasswordRequestDto);
    }

    @PostMapping(path = "/change-password")
    public ResponseEntity<?> executeRequestPasswordChange(
        @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) throws Exception {
        return  accountService.changePassword(changePasswordRequestDto);
    }
    
    /**
     * Object to return as body in JWT Authentication.
     */
    public static class JWTToken {

        /** The id token. */
        private String idToken;

        /** The expiration date. */
        private Long expirationDate;

        /**
         * Instantiates a new JWT token.
         */
        public JWTToken() {
        }

        /**
         * Instantiates a new JWT token.
         *
         * @param idToken the id token
         */
        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        /**
         * Gets the id token.
         *
         * @return the id token
         */
        @JsonProperty(value = "idToken", index = 1)
        public String getIdToken() {
            return idToken;
        }

        /**
         * Sets the id token.
         *
         * @param idToken the new id token
         */
        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }

        /**
         * Gets the expiration date.
         *
         * @return the expiration date
         */
        @JsonProperty(value = "expirationDate", index = 2)
        public Long getExpirationDate() {
            return expirationDate;
        }

        /**
         * Sets the expiration date.
         *
         * @param expirationDate the new expiration date
         */
        public void setExpirationDate(Long expirationDate) {
            this.expirationDate = expirationDate;
        }

    }
}
