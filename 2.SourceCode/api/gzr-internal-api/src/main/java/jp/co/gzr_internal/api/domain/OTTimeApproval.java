/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 22, 2019
 */
package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class OTTimeApproval.
 */
@Entity
@Table(name = "ot_time_approval")
public class OTTimeApproval implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Integer id;

    /** The employee code. */
    @Column(name = "employee_code")
    private String employeeCode;

    /** The project name. */
    @Column(name = "project_name", nullable = false)
    private String projectName;

    /** The Date ot. */
    @Column(name = "date_ot", nullable = false)
    private Date DateOt;

    /** The approve time ot. */
    @Column(name = "approve_time_ot")
    private String approveTimeOt;

    /** The request id. */
    @Column(name = "request_id", nullable = false)
    private String requestId;

    /** The create time. */
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    /** The update time. */
    @Column(name = "update_time", nullable = false)
    private ZonedDateTime updateTime;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "OTTimeApproval [id=" + id + ", employeeCode=" + employeeCode + ", projectName=" + projectName
            + ", DateOt=" + DateOt + ", approveTimeOt=" + approveTimeOt + ", requestId=" + requestId + ", createTime="
            + createTime + ", updateTime=" + updateTime + "]";
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
     * Gets the date ot.
     *
     * @return the date ot
     */
    public Date getDateOt() {
        return DateOt;
    }

    /**
     * Sets the date ot.
     *
     * @param dateOt the new date ot
     */
    public void setDateOt(Date dateOt) {
        DateOt = dateOt;
    }

    /**
     * Gets the approve time ot.
     *
     * @return the approve time ot
     */
    public String getApproveTimeOt() {
        return approveTimeOt;
    }

    /**
     * Sets the approve time ot.
     *
     * @param approveTimeOt the new approve time ot
     */
    public void setApproveTimeOt(String approveTimeOt) {
        this.approveTimeOt = approveTimeOt;
    }

    /**
     * Gets the request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the request id.
     *
     * @param requestId the new request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets the creates the time.
     *
     * @return the creates the time
     */
    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the creates the time.
     *
     * @param createTime the new creates the time
     */
    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the update time.
     *
     * @return the update time
     */
    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the update time.
     *
     * @param updateTime the new update time
     */
    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    /**
     * Instantiates a new OT time approval.
     */
    public OTTimeApproval() {
        super();
    }

    public OTTimeApproval(Integer id, String employeeCode, String projectName, Date dateOt, String approveTimeOt,
        String requestId, ZonedDateTime createTime, ZonedDateTime updateTime, Integer delete_flag) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.projectName = projectName;
        DateOt = dateOt;
        this.approveTimeOt = approveTimeOt;
        this.requestId = requestId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.delete_flag = delete_flag;
    }
}
