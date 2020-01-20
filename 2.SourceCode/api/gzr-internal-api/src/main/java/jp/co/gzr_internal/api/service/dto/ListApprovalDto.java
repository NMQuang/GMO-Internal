/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 07, 2019
 */
package jp.co.gzr_internal.api.service.dto;

import java.util.Date;

/**
 * The Class ListApprovalDto.
 */
public class ListApprovalDto
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

    /** The actual time ot. */
    private Integer planTimeOT;

    /** The date ot. */
    private Date dateOT;

    /** The approve time OT. */
    private Integer approveTimeOT;

    /**
     * Gets the approve time OT.
     *
     * @return the approve time OT
     */
    public Integer getApproveTimeOT() {
        return approveTimeOT;
    }

    /**
     * Sets the approve time OT.
     *
     * @param approveTimeOT the new approve time OT
     */
    public void setApproveTimeOT(Integer approveTimeOT) {
        this.approveTimeOT = approveTimeOT;
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
     * Instantiates a new list approval dto.
     */
    public ListApprovalDto() {
        super();
    }

    /**
     * Gets the date ot.
     *
     * @return the date ot
     */
    public Date getDateOT() {
        return dateOT;
    }

    /**
     * Sets the date ot.
     *
     * @param dateOt the new date ot
     */
    public void setDateOT(Date dateOT) {
        this.dateOT = dateOT;
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
     * Instantiates a new list approval dto.
     *
     * @param id the id
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param positionName the position name
     * @param projectName the project name
     * @param planTimeOT the plan time OT
     * @param planTimeOt the plan time ot
     * @param dateOt the date ot
     */
    public ListApprovalDto(Integer id, String employeeCode, String employeeName, String positionName,
        String projectName, Integer planTimeOT, Integer planTimeOt) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.positionName = positionName;
        this.projectName = projectName;
        this.planTimeOT = planTimeOT;
    }

    /**
     * Instantiates a new list approval dto.
     *
     * @param id the id
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param positionName the position name
     * @param projectName the project name
     * @param planTimeOT the plan time OT
     * @param dateOt the date ot
     * @param approveTimeOT the approve time OT
     */
    public ListApprovalDto(Integer id, String employeeCode, String employeeName, String positionName,
        String projectName, Integer planTimeOT, Date dateOT, Integer approveTimeOT) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.positionName = positionName;
        this.projectName = projectName;
        this.planTimeOT = planTimeOT;
        this.dateOT = dateOT;
        this.approveTimeOT = approveTimeOT;
    }
}
