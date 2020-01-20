/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: GiangTT
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.service.dto.request;

import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;
import jp.co.gzr_internal.api.web.rest.validator.annotation.sizelength.SizeLength;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class ChangePasswordRequestDto.
 *
 * @author GiangTT
 */
public class ChangePasswordRequestDto {

    /** The password current. */
    @NotNull(itemName = "{passwordCurrent}", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_011}", errorCode = "ERROR_011", pattern = ValidateCommon.PASSWORD_REGEX)
    @SizeLength(itemName = "{passwordCurrent}", partition = "{minLength} - {maxLength}", message = "{ERROR_012}", errorCode = "ERROR_012", maxLength = 128, minLength = 6)
    private String passwordCurrent;
    
    /** The password new. */
    @NotNull(itemName = "{passwordNew}", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_011}", errorCode = "ERROR_011", pattern = ValidateCommon.PASSWORD_REGEX)
    @SizeLength(itemName = "{passwordNew}", partition = "{minLength} - {maxLength}", message = "{ERROR_012}", errorCode = "ERROR_012", maxLength = 128, minLength = 6)
    private String passwordNew;
    
    /** The password confirm. */
    @NotNull(itemName = "{passwordConfirm}", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_011}", errorCode = "ERROR_011", pattern = ValidateCommon.PASSWORD_REGEX)
    @SizeLength(itemName = "{passwordConfirm}", partition = "{minLength} - {maxLength}", message = "{ERROR_012}", errorCode = "ERROR_012", maxLength = 128, minLength = 6)
    private String passwordConfirm;

    /**
     * Instantiates a new forgot password request dto.
     */
    public ChangePasswordRequestDto() {
    }

    /**
     * Gets the password confirm.
     *
     * @return the passwordConfirm
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the password confirm.
     *
     * @param passwordConfirm the passwordConfirm to set
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * Gets the password current.
     *
     * @return the passwordCurrent
     */
    public String getPasswordCurrent() {
        return passwordCurrent;
    }

    /**
     * Sets the password current.
     *
     * @param passwordCurrent the passwordCurrent to set
     */
    public void setPasswordCurrent(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }

    /**
     * Gets the password new.
     *
     * @return the passwordNew
     */
    public String getPasswordNew() {
        return passwordNew;
    }

    /**
     * Sets the password new.
     *
     * @param passwordNew the passwordNew to set
     */
    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
}