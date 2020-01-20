/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
 */
package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Request.
 */
@Entity
@Table(name="request")
public class Request implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 8, nullable = false)
    private Integer id;
    
    /** The employee code. */
    @Column(name="employee_code")
    private String employeeCode;
    
    /** The project name. */
    @Column(name="project_code", nullable = false)
    private String projectCode;
    
    /** The reason. */
    @Column(name="reason", nullable = false)
    private String reason;
    
    /** The note. */
    @Column(name="note" )
    private String note;
    
    /** The status. */
    @Column(name="status", nullable = false)
    private Integer status ;
    
    /** The delete flag. */
    @Column(name="delete_flag", nullable = false)
    private Integer deleteFlag ;
    
    /** The create time. */
    @Column(name="create_time", nullable = false)
    private LocalDateTime createTime;
    
    /** The update time. */
    @Column(name="update_time", nullable = false)
    private LocalDateTime updateTime;
    
    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Request [id=" + id + ", employeeCode=" + employeeCode + ", projectCode=" + projectCode + ", reason="
            + reason + ", note=" + note + ", status=" + status + ", deleteFlag=" + deleteFlag + ", createTime="
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
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the new project name
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
     * Gets the delete flag.
     *
     * @return the delete flag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the delete flag.
     *
     * @param deleteFlag the new delete flag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Gets the creates the time.
     *
     * @return the creates the time
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the creates the time.
     *
     * @param createTime the new creates the time
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the update time.
     *
     * @return the update time
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the update time.
     *
     * @param updateTime the new update time
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Gets the serialversionuid.
     *
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
