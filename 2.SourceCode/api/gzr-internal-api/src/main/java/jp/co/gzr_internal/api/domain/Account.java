package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private String id;

    /** The role id. */
    @NotNull
    @Size(max = 1)
    @Column(name = "role_id", length = 1, nullable = false)
    private Integer roleId;

    /** The password. */
    @NotNull
    @Column(name = "password")
    private String password;

    /** The establishment. */
    @Column(name = "employee_code")
    private String employeeCode;

    /** The last logout date. */
    @Column(name = "last_logout_date")
    private ZonedDateTime lastLogoutDate;

    /** The last access date. */
    @Column(name = "last_access_date")
    private ZonedDateTime lastAccessDate;

    /** The create time. */
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    /** The update time. */
    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    /** The delete flag. */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
     * Gets the last logout date.
     *
     * @return the lastLogoutDate
     */
    public ZonedDateTime getLastLogoutDate() {
        return lastLogoutDate;
    }

    /**
     * Sets the last logout date.
     *
     * @param lastLogoutDate the lastLogoutDate to set
     */
    public void setLastLogoutDate(ZonedDateTime lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    /**
     * Gets the last access date.
     *
     * @return the lastAccessDate
     */
    public ZonedDateTime getLastAccessDate() {
        return lastAccessDate;
    }

    /**
     * Sets the last access date.
     *
     * @param lastAccessDate the lastAccessDate to set
     */
    public void setLastAccessDate(ZonedDateTime lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    /**
     * Gets the creates the time.
     *
     * @return the createTime
     */
    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    /**
     * Sets the creates the time.
     *
     * @param createTime the createTime to set
     */
    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the update time.
     *
     * @return the updateTime
     */
    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the update time.
     *
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * Gets the delete flag.
     *
     * @return the deleteFlag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the delete flag.
     *
     * @param deleteFlag the deleteFlag to set
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
