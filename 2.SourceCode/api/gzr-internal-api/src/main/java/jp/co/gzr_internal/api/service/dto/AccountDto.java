package jp.co.gzr_internal.api.service.dto;

/**
 * The Class AccountDto.
 */
public class AccountDto {

    /** The email. */
    private String email;

    /** The password. */
    private String password;

    /** The employee code. */
    private String employeeCode;

    /** The role id. */
    private Integer roleId;

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
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the employee code.
     *
     * @return the employeeCode
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the employeeCode to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Gets the role id.
     *
     * @return the roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * Sets the role id.
     *
     * @param roleId the roleId to set
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * Instantiates a new account dto.
     *
     * @param email the email
     * @param password the password
     * @param employeeCode the employee code
     * @param roleId the role id
     */
    public AccountDto(String email, String password, String employeeCode, Integer roleId) {
        this.email = email;
        this.password = password;
        this.employeeCode = employeeCode;
        this.roleId = roleId;
    }

    /**
     * Instantiates a new account dto.
     */
    public AccountDto() {
    }
}
