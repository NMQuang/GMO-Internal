/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.gzr_internal.api.service.AccountService;
import jp.co.gzr_internal.api.service.dto.request.ChangePasswordRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.errors.SelectDataNotExistErrorException;

/**
 * The Class ListCourseValidator.
 */
@Component
public class ChangePasswordValidator implements Validator {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    /**
     * Supports.
     *
     * @param clazz the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ChangePasswordRequestDto.class.equals(clazz);
    }

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordRequestDto requestDto = (ChangePasswordRequestDto) target;

        // Check password new and password confirm match
        if (!requestDto.getPasswordNew().equals(requestDto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm",
                MessageContants.CONST_ERROR_CODE_PASSWORD_AND_PASSWORD_CONFIRM_NOT_MATCH, new Object[]{}, null);
            return;
        }
        
        String passwordEncode = accountService.getPasswordBylogin();
        if (passwordEncode != null) {
            if (!bCryptPasswordEncoder.matches(requestDto.getPasswordCurrent(), passwordEncode)) {
                errors.rejectValue("passwordCurrent",
                    MessageContants.CONST_ERROR_CODE_PASSWORD_INCORRECT, new Object[]{}, null);
                return;
            }
        } else {
            throw new SelectDataNotExistErrorException();
        }
    }
}
