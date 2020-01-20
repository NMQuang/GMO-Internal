/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: GiangTT
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.service.dto.request;

import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class ForgotPasswordDto.
 *
 * @author GiangTT
 */
public class ForgotPasswordRequestDto {

    @NotNull(itemName = "email", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_010}", errorCode = "ERROR_010", pattern = ValidateCommon.EMAIL_REGEX)
    @MaxLength(itemName = "email", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength = 256)
    private String email;

    /**
     * Instantiates a new forgot password request dto.
     */
    public ForgotPasswordRequestDto() {
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ForgotPasswordDto [email=" + email + "]";
    }
}
