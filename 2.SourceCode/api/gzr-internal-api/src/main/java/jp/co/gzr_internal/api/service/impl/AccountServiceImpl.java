package jp.co.gzr_internal.api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.gzr_internal.api.repository.AccountRepository;
import jp.co.gzr_internal.api.repository.CustomProjectRepository;
import jp.co.gzr_internal.api.security.jwt.TokenProvider;
import jp.co.gzr_internal.api.service.AccountService;
import jp.co.gzr_internal.api.service.MailService;
import jp.co.gzr_internal.api.service.dto.AccountAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.request.ChangePasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ForgotPasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.LoginRequestDto;
import jp.co.gzr_internal.api.service.dto.response.LoginResponseDto;
import jp.co.gzr_internal.api.service.util.RandomUtil;
import jp.co.gzr_internal.api.web.rest.UserJWTController.JWTToken;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.errors.MutilUserActionException;
import jp.co.gzr_internal.api.web.rest.errors.UpdateErrorException;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * Service Implementation for managing MUser.
 */
@Service
public class AccountServiceImpl implements AccountService {

    /** The token provider. */
    private final TokenProvider tokenProvider;

    /** The authentication manager. */
    private final AuthenticationManager authenticationManager;

    /** The m company repository. */
    private final AccountRepository accountRepository;

    /** The b crypt password encoder. */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /** The mail service. */
    private final MailService mailService;
    
    private final CustomProjectRepository customProjectRepository;

    public AccountServiceImpl(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
        AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, CustomProjectRepository customProjectRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.customProjectRepository = customProjectRepository;
    }

    /*
     * (non-Javadoc)
     * 
     * @see jp.co.cafe.api.service.MCompanyService#login(jp.co.cafe.api.service.dto.request.LoginRequestDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) throws Exception {

        // Create token with email and password
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginRequestDto.isRememberMe() == null) ? false
            : Boolean.valueOf(loginRequestDto.isRememberMe());
        JWTToken jwt = tokenProvider.createToken(authentication, rememberMe);

        // Execute get company detail
        AccountAfterLoginDto accountInfo = accountRepository.selectAccountInfo(loginRequestDto.getEmail());
        
        // Set result for response
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setEmployeeCode(accountInfo.getEmployeeCode());
        responseDto.setEmployeeName(Utils.upperCaseWords(accountInfo.getEmployeeName()));
        responseDto.setRoleId(accountInfo.getRoleId());
        responseDto.setJwt(jwt);
        
        List<ProjectAfterLoginDto> projectInfo = new ArrayList<>();
        projectInfo = customProjectRepository.getRoleOfProject(accountInfo.getEmployeeCode());
        responseDto.setProjectInfo(projectInfo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Contants.AUTHORIZATION_HEADER, Contants.CONST_STR_BEARER + jwt.getIdToken());

        String employeeCode = Utils.getEmployeeCodeFromLogin();

        LocalDateTime currentDateTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);

        int result = accountRepository.updateLastAccessDateByEmployeeCode(employeeCode, currentDateTime);

        if (result != 1) {
            throw new UpdateErrorException();
        }

        return new ResponseEntity<>(ResponseCommon.createResponse(responseDto, MessageContants.SUCCESS_CODE),
            httpHeaders, HttpStatus.OK);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> logout() throws Exception {

        int result = -1;

        try {

            String userId = Utils.getEmployeeCodeFromLogin();
            LocalDateTime currentDateTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);
            result = accountRepository.updateLastLogOut(userId, currentDateTime);
        } catch (Exception e) {

            // If have exception when update will log bug and throw exception UpdateError
            throw new UpdateErrorException();
        }

        // If result = 0 it mean the company detail has been changed
        if (result == 0) {
            throw new MutilUserActionException();
        }
        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> requestPasswordReset(ForgotPasswordRequestDto requestDto) {
        // Check user with current email and return the user detail
        int result = -1;
        try {
            AccountAfterLoginDto accountInfo = accountRepository.selectAccountInfo(requestDto.getEmail());
            // Have company with current mail
//            if (accountInfo != null) {
                // Generate random password for comp
                String newPassword = RandomUtil.generatePassword();

                // Update password and updateTime for user with current email
                result = accountRepository.updatePasswordForAccount(requestDto.getEmail(),
                    bCryptPasswordEncoder.encode(newPassword),
                    Utils.getCurrentDate(Contants.CONST_STR_PATTERN_YYYYMMDDHHMMSS));

                // If result = 0 it mean the user detail has been changed
                if (result == 0) {
                    throw new MutilUserActionException();
                }

                // Send mail for user notify passwords change
                mailService.sendPasswordResetMail(accountInfo, newPassword, requestDto.getEmail());

                return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE),
                    HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(
//                    ResponseCommon.createMapResponseError(MessageContants.CONST_ERROR_CODE_EMAIL_NOT_REGISTER),
//                    HttpStatus.NOT_FOUND);
//            }
        } catch (Exception e) {
            // If have exception when update will log bug and throw exception UpdateError
            throw new UpdateErrorException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> changePassword(ChangePasswordRequestDto requestDto) {
        // Check user with current email and return the user detail
        int result = -1;
        try {
            // Update password and updateTime for user with current email
            result = accountRepository.updatePasswordForAccount(Utils.getEmailFromLogin(),
                bCryptPasswordEncoder.encode(requestDto.getPasswordNew()),
                Utils.getCurrentDate(Contants.CONST_STR_PATTERN_YYYYMMDDHHMMSS));
            // If result = 0 it mean the user detail has been changed
            if (result == 0) {
                throw new MutilUserActionException();
            }

            return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE),
                HttpStatus.OK);
        } catch (Exception e) {
            // If have exception when update will log bug and throw exception UpdateError
            throw new UpdateErrorException();
        }
    }

    @Override
    public String getPasswordBylogin() {
        return accountRepository.selectPasswordByEmail(Utils.getEmailFromLogin());
    }
}
