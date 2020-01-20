package jp.co.gzr_internal.api.service;

import org.springframework.http.ResponseEntity;

import jp.co.gzr_internal.api.service.dto.request.ChangePasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ForgotPasswordRequestDto;
import jp.co.gzr_internal.api.service.dto.request.LoginRequestDto;

/**
 * Service Interface for managing account.
 */
public interface AccountService {

    /**
     * Login.
     *
     * @author GiangTT
     * @param loginRequestDto the login request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> login(LoginRequestDto loginRequestDto) throws Exception;

    /**
     * Logout.
     *
     * @author GiangTT
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> logout() throws Exception;

    /**
     * Request password reset.
     *
     * @param forgotPasswordRequestDto the forgot password request dto
     * @return the response entity
     */
    ResponseEntity<?> requestPasswordReset(ForgotPasswordRequestDto forgotPasswordRequestDto);

    /**
     * Change password.
     *
     * @param requestDto the request dto
     * @return the response entity
     */
    ResponseEntity<?> changePassword(ChangePasswordRequestDto requestDto);
    
    /**
     * Gets the password bylogin.
     *
     * @return the password bylogin
     */
    String getPasswordBylogin(); 
}
