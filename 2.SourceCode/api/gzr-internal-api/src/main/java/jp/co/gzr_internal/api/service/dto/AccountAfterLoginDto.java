package jp.co.gzr_internal.api.service.dto;

/**
 * The class AccountAfterLoginDto.
 * 
 * @author GiangTT
 *
 */
public class AccountAfterLoginDto {

    /** The employee code. */
    private String employeeCode;

    /** The employee name. */
    private String employeeName;
    
    /** The role id. */
    private Integer roleId;

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
     * Gets the employee name.
     *
     * @return the employeeName
     */
    public String getEmployeeName() {
      return employeeName;
    }

    /**
     * Sets the employee name.
     *
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
      this.employeeName = employeeName;
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
     * Instantiates a new account after login dto.
     *
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param roleId the role id
     */
    public AccountAfterLoginDto(String employeeCode, String employeeName, Integer roleId) {
      super();
      this.employeeCode = employeeCode;
      this.employeeName = employeeName;
      this.roleId = roleId;
    }

    /**
     * Instantiates a new account after login dto.
     */
    public AccountAfterLoginDto() {
      super();
    }
}
