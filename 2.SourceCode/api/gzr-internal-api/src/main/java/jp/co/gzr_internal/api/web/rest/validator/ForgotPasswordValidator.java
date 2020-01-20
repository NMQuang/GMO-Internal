/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.gzr_internal.api.repository.AccountRepository;
import jp.co.gzr_internal.api.service.dto.AccountAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.request.ForgotPasswordRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;

/**
 * The Class ListCourseValidator.
 */
@Component
public class ForgotPasswordValidator implements Validator {
    
    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * Supports.
     *
     * @param clazz the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ForgotPasswordRequestDto.class.equals(clazz);
    }

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        ForgotPasswordRequestDto requestDto = (ForgotPasswordRequestDto) target;

        AccountAfterLoginDto accountInfo = accountRepository.selectAccountInfo(requestDto.getEmail());
        if (accountInfo == null ) {
            errors.rejectValue("email",
                MessageContants.CONST_ERROR_CODE_EMAIL_NOT_REGISTER, new Object[]{}, null);
            return;
        }
    }
}
