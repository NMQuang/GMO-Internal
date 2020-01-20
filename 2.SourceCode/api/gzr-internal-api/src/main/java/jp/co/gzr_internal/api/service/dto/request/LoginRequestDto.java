package jp.co.gzr_internal.api.service.dto.request;

import jp.co.gzr_internal.api.web.rest.validator.annotation.checktype.CheckType;
import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;
import jp.co.gzr_internal.api.web.rest.validator.annotation.sizelength.SizeLength;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * View Model object for storing a user's credentials.
 *
 */
public class LoginRequestDto {

    /** The email. */
    @NotNull(itemName = "email", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_010}", errorCode = "ERROR_010", pattern = ValidateCommon.EMAIL_REGEX)
    @MaxLength(itemName = "email", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength = 256)
    private String email;

    /** The password. */
    @NotNull(itemName = "{password}", message = "{ERROR_009}", errorCode = "ERROR_009")
    @Pattern(message = "{ERROR_011}", errorCode = "ERROR_011", pattern = ValidateCommon.PASSWORD_REGEX)
    @SizeLength(itemName = "{password}", partition = "{minLength} - {maxLength}", message = "{ERROR_012}", errorCode = "ERROR_012", maxLength = 128, minLength = 6)
    private String password;

    /** The remember me. */
    @CheckType(itemName = "remember me", errorCode = "ERROR_013", message = "{ERROR_013}", type = "boolean", defaultValue = "true")
    private String rememberMe;

    /**
     * Instantiates a new login request dto.
     */
    public LoginRequestDto() {
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

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks if is remember me.
     *
     * @return the boolean
     */
    public String isRememberMe() {
        return rememberMe;
    }

    /**
     * Sets the remember me.
     *
     * @param rememberMe the new remember me
     */
    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LoginDto{" + "username='" + email + '\'' + ", rememberMe=" + rememberMe + '}';
    }
}
