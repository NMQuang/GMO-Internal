/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.service.dto.EmployeeByPositionDto;
import jp.co.gzr_internal.api.service.dto.RequestDetailDto;

/**
 * The Class RequestDetailResponeDto.
 */
public class RequestDetailResponseDto
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
    
    /** The request details. */
    private List<RequestDetailDto> requestDetail;
    
    private List<EmployeeByPositionDto> employeeList;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
     * Sets the request detail.
     *
     * @param requestDetail the new request detail
     */
    public void setRequestDetail(List<RequestDetailDto> requestDetail) {
        this.requestDetail = requestDetail;
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
     * Gets the request details.
     *
     * @return the request details
     */
    public List<RequestDetailDto> getRequestDetail() {
        return requestDetail;
    }

    /**
     * Sets the request details.
     *
     * @param requestDetail the new request details
     */
    public void setRequestDetails(List<RequestDetailDto> requestDetail) {
        this.requestDetail = requestDetail;
    }


    /**
     * Instantiates a new request detail respone dto.
     *
     * @param requestId the request id
     * @param projectName the project name
     * @param employeeCode the employee code
     * @param reason the reason
     * @param updateAt the update at
     */
    public RequestDetailResponseDto(Integer requestId, String projectName,String employeeCode, String reason, String updateAt) {
        super();
        this.requestId = requestId;
        this.projectName = projectName;
        this.reason = reason;
        this.employeeCode = employeeCode;
        this.updateAt = updateAt;
    }


    /**
     * Instantiates a new request detail respone dto.
     *
     * @param requestId the request id
     * @param projectName the project name
     * @param reason the reason
     * @param employeeCode the employee code
     * @param updateAt the update at
     * @param requestDetail the request detail
     */
    public RequestDetailResponseDto(Integer requestId, String projectName, String reason, 
        String employeeCode, String updateAt, List<RequestDetailDto> requestDetail) {
        super();
        this.requestId = requestId;
        this.projectName = projectName;
        this.reason = reason;
        this.employeeCode = employeeCode;
        this.updateAt = updateAt;
        this.requestDetail = requestDetail;
    }

    
    
    /**
     * Instantiates a new request detail response dto.
     *
     * @param requestId the request id
     * @param projectName the project name
     * @param reason the reason
     * @param employeeCode the employee code
     * @param updateAt the update at
     * @param status the status
     * @param email the email
     * @param requestDetail the request detail
     */
    public RequestDetailResponseDto(Integer requestId, String projectName, String reason, String employeeCode,
        String updateAt, Integer status, String email, String employeeName, List<RequestDetailDto> requestDetail) {
        super();
        this.requestId = requestId;
        this.projectName = projectName;
        this.reason = reason;
        this.employeeCode = employeeCode;
        this.updateAt = updateAt;
        this.status = status;
        this.email = email;
        this.employeeName = employeeName;
        this.requestDetail = requestDetail;
    }

    /**
     * Instantiates a new request detail respone dto.
     */
    public RequestDetailResponseDto() {
        super();
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

    public List<EmployeeByPositionDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeByPositionDto> employeeList) {
        this.employeeList = employeeList;
    }
    
}
