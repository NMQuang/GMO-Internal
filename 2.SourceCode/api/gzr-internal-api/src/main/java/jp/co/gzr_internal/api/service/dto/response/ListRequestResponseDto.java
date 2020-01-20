/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 27, 2019 
 */
package jp.co.gzr_internal.api.service.dto.response;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc
/**
 * The Class ListRequestResponseDto.
 */
public class ListRequestResponseDto
{
    /** The request id. */
    private Integer requestId;

    /** The project code. */
    private String projectCode;
    
    /** The project name. */
    private String projectName;

    /** The employee code. */
    private String employeeCode;

    /** The employee name. */
    private String employeeName;
    
    /** The update at. */
    private String createAt;
    
    /** The update at. */
    private String updateAt;

    /** The total request. */
    private BigDecimal totalRequest;

    /** The total approval. */
    private BigDecimal totalApproval;

    /** The status. */
    private Integer status;

    /** The note. */
    private String note;

    /** The date ot. */
    private String dateOt;
    
    /** The project management. */
    private String projectManagement;
    
    /**
     * Gets the project code.
     *
     * @return the project code
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the project code.
     *
     * @param projectCode the new project code
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the employee name.
     *
     * @return the employee name
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Sets the employee name.
     *
     * @param employeeName the new employee name
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
     * Gets the total request.
     *
     * @return the total request
     */
    public BigDecimal getTotalRequest() {
        return totalRequest;
    }

    /**
     * Sets the total request.
     *
     * @param totalRequest the new total request
     */
    public void setTotalRequest(BigDecimal totalRequest) {
        this.totalRequest = totalRequest;
    }

    /**
     * Gets the total approval.
     *
     * @return the total approval
     */
    public BigDecimal getTotalApproval() {
        return totalApproval;
    }

    /**
     * Sets the total approval.
     *
     * @param totalApproval the new total approval
     */
    public void setTotalApproval(BigDecimal totalApproval) {
        this.totalApproval = totalApproval;
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
     * @param status the new status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets the note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note.
     *
     * @param note the new note
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * Instantiates a new list request response dto.
     *
     * @param requestId the request id
     * @param projectCode the project code
     * @param projectName the project name
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param createAt the create at
     * @param updateAt the update at
     * @param totalRequest the total request
     * @param totalApproval the total approval
     * @param status the status
     * @param note the note
     * @param dateOt the date ot
     * @param projectManagement the project management
     */
    public ListRequestResponseDto(Integer requestId, String projectCode, String projectName, String employeeCode,
        String employeeName, String createAt, String updateAt, BigDecimal totalRequest, BigDecimal totalApproval,
        Integer status, String note, String dateOt, String projectManagement) {
        super();
        this.requestId = requestId;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.totalRequest = totalRequest;
        this.totalApproval = totalApproval;
        this.status = status;
        this.note = note;
        this.dateOt = dateOt;
        this.projectManagement = projectManagement;
    }

    /**
     * Gets the creates the at.
     *
     * @return the creates the at
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * Sets the creates the at.
     *
     * @param createAt the new creates the at
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    /**
     * Instantiates a new list request response dto.
     */
    public ListRequestResponseDto() {
        super();
    }

    /**
     * Gets the date ot.
     *
     * @return the date ot
     */
    public String getDateOt() {
        return dateOt;
    }

    /**
     * Sets the date ot.
     *
     * @param dateOt the new date ot
     */
    public void setDateOt(String dateOt) {
        this.dateOt = dateOt;
    }

    /**
     * Gets the project management.
     *
     * @return the project management
     */
    public String getProjectManagement() {
        return projectManagement;
    }

    /**
     * Sets the project management.
     *
     * @param projectManagement the new project management
     */
    public void setProjectManagement(String projectManagement) {
        this.projectManagement = projectManagement;
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
    
    
}


