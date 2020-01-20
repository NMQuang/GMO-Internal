/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 07, 2019
 */
package jp.co.gzr_internal.api.service.dto.response;

/**
 * The Class ListApprovalResponseDto.
 */
public class ListApprovalResponseDto
{
    /** The id. */
    private Integer id;

    /** The employee code. */
    private String employeeCode;

    /** The employee name. */
    private String employeeName;

    /** The position name. */
    private String positionName;

    /** The project name. */
    private String projectName;

    /** The Approval OT. */
    private Integer planTimeOT;

    /** The approval time OT. */
    private Integer approvalTimeOT;
    /** The date ot. */
    private String dateOT;

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
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the plan time OT.
     *
     * @return the plan time OT
     */
    public Integer getPlanTimeOT() {
        return planTimeOT;
    }

    /**
     * Sets the plan time OT.
     *
     * @param planTimeOT the new plan time OT
     */
    public void setPlanTimeOT(Integer planTimeOT) {
        this.planTimeOT = planTimeOT;
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
     * Gets the position name.
     *
     * @return the position name
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Sets the position name.
     *
     * @param positionName the new position name
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
     * Instantiates a new list approval response dto.
     */
    public ListApprovalResponseDto() {
        super();
    }

    /**
     * Instantiates a new list approval response dto.
     *
     * @param id the id
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param positionName the position name
     * @param projectName the project name
     * @param planTimeOT the plan time OT
     * @param approvalTimeOT the approval time OT
     * @param dateOT the date OT
     */
    public ListApprovalResponseDto(Integer id, String employeeCode, String employeeName, String positionName,
        String projectName, Integer planTimeOT, Integer approvalTimeOT, String dateOT) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.positionName = positionName;
        this.projectName = projectName;
        this.planTimeOT = planTimeOT;
        this.approvalTimeOT = approvalTimeOT;
        this.dateOT = dateOT;
    }
}
