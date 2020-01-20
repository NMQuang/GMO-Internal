/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.service.dto;

/**
 * The Class RequestDto.
 */
public class RequestDto
{
    /** The request id. */
    private Integer requestId;

    private String projectCode;
    
    /** The project name. */
    private String projectName;

    /** The reason. */
    private String reason;

    /** The employee code. */
    private String employeeCode;

    /** The update at. */
    private String updateAt;
    
    /** The status. */
    private Integer status;

    /** The email. */
    private String email;
    
    private String employeeName;
    
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Instantiates a new request dto.
     */
    public RequestDto() {
        super();
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
     * Gets the request id.
     *
     * @return the request id
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Sets the request id.
     *
     * @param requestId the new request id
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets the project name.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the new project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason.
     *
     * @param reason the new reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the employee code.
     *
     * @return the employee code
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the new employee code
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Gets the update at.
     *
     * @return the update at
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * Sets the update at.
     *
     * @param updateAt the new update at
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public RequestDto(Integer requestId, String projectCode, String projectName, String reason, String employeeCode,
        String updateAt, Integer status, String email, String employeeName) {
        super();
        this.requestId = requestId;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.reason = reason;
        this.employeeCode = employeeCode;
        this.updateAt = updateAt;
        this.status = status;
        this.email = email;
        this.employeeName = employeeName;
    }

}
