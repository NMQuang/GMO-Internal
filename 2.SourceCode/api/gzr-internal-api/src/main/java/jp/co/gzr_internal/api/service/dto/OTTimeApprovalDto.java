/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 22, 2019
 */
package jp.co.gzr_internal.api.service.dto;

/**
 * The Class OTTimeApprovalDto.
 */
public class OTTimeApprovalDto
{
    /** The id. */
    private Integer id;

    /** The employee code. */
    private String employeeCode;

    /** The project name. */
    private String projectName;

    /** The date OT. */
    private String dateOT;

    /** The approval time OT. */
    private Integer approvalTimeOT;

    /** The request id. */
    private Integer requestId;

    /**
     * Instantiates a new OT time approval dto.
     *
     * @param id the id
     * @param employeeCode the employee code
     * @param projectName the project name
     * @param dateOT the date OT
     * @param approvalTimeOT the approval time OT
     * @param requestId the request id
     */
    public OTTimeApprovalDto(Integer id, String employeeCode, String projectName, String dateOT, Integer approvalTimeOT,
        Integer requestId) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.projectName = projectName;
        this.dateOT = dateOT;
        this.approvalTimeOT = approvalTimeOT;
        this.requestId = requestId;
    }

    /**
     * Instantiates a new OT time approval dto.
     *
     * @param id the id
     */
    public OTTimeApprovalDto(Integer id) {
        super();
        this.id = id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * Gets the date OT.
     *
     * @return the date OT
     */
    public String getDateOT() {
        return dateOT;
    }

    /**
     * Sets the date OT.
     *
     * @param dateOT the new date OT
     */
    public void setDateOT(String dateOT) {
        this.dateOT = dateOT;
    }

    /**
     * Gets the approval time OT.
     *
     * @return the approval time OT
     */
    public Integer getApprovalTimeOT() {
        return approvalTimeOT;
    }

    /**
     * Sets the approval time OT.
     *
     * @param approvalTimeOT the new approval time OT
     */
    public void setApprovalTimeOT(Integer approvalTimeOT) {
        this.approvalTimeOT = approvalTimeOT;
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
}
