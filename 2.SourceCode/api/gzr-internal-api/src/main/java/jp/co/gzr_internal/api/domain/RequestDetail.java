/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
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
 * The Class RequestDetail.
 */
@Entity
@Table(name="request_detail")
public class RequestDetail implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 8, nullable = false)
    private Integer id;
    
    /** The request id. */
    @Column(name="request_id", nullable =false )
    private Integer requestId;
    
    /** The employee code. */
    @Column(name="employee_code", nullable =false )
    private String employeeCode;
    
    /** The date ot. */
    @Column(name="date_ot", nullable =false )
    private Date dateOt;
    
    /** The time ot. */
    @Column(name="time_ot", nullable =false )
    private Integer timeOt;
    
    /** The actual time ot. */
    @Column(name="actual_time_ot")
    private Integer actualTimeOt;
    
    /** The note. */
    @Column(name="note")
    private String note;
    
    /** The delete flag. */
    @Column(name="delete_flag", nullable = false)
    private Integer deleteFlag;
    
    /** The create time. */
    @Column(name="create_time", nullable =false )
    private ZonedDateTime createTime;
    
    /** The update time. */
    @Column(name="update_time", nullable =false )
    private ZonedDateTime updateTime;

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "RequestDetail [id=" + id + ", requestId=" + requestId + ", employeeCode=" + employeeCode + ", dateOt="
            + dateOt + ", timeOt=" + timeOt + ", actualTimeOt=" + actualTimeOt + ", note=" + note + ", deleteFlag="
            + deleteFlag + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
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
     * Gets the date ot.
     *
     * @return the date ot
     */
    public Date getDateOt() {
        return dateOt;
    }

    /**
     * Sets the date ot.
     *
     * @param dateOt the new date ot
     */
    public void setDateOt(Date dateOt) {
        this.dateOt = dateOt;
    }

    /**
     * Gets the time ot.
     *
     * @return the time ot
     */
    public Integer getTimeOt() {
        return timeOt;
    }

    /**
     * Sets the time ot.
     *
     * @param timeOt the new time ot
     */
    public void setTimeOt(Integer timeOt) {
        this.timeOt = timeOt;
    }

    /**
     * Gets the actual time ot.
     *
     * @return the actual time ot
     */
    public Integer getActualTimeOt() {
        return actualTimeOt;
    }

    /**
     * Sets the actual time ot.
     *
     * @param actualTimeOt the new actual time ot
     */
    public void setActualTimeOt(Integer actualTimeOt) {
        this.actualTimeOt = actualTimeOt;
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

    /**
     * Gets the serialversionuid.
     *
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
