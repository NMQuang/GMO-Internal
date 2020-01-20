package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Employee.
 */
@Entity
@Table(name="employees")
public class Employee implements Serializable
{
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;
    /** The employee code. */
    
    @Column(name = "employee_code")
    private String employeeCode;
    
    /** The employee name. */
    @Column(name="employee_name")
    private String employeeName;
    
    /** The email. */
    @Column(name="email")
    private String email;
    
    /** The gender. */
    @Column(name="gender")
    private Integer gender;
    
    /** The address. */
    @Column(name="address")
    private String address;
    
    /** The birthday. */
    @Column(name="birthday")
    private String birthday;
    
    @Column(name="phone_number")
    private String phoneNumber;
    
    /** The birthplace. */
    @Column(name="birthplace")
    private String birthplace;
    
    /** The province pr. */
    @Column(name="province_pr")
    private String provincePr;
    
    /** The district pr. */
    @Column(name="district_pr")
    private String districtPr;
    
    /** The ward pr. */
    @Column(name="ward_pr")
    private String wardPr;
    
    /** The nation ca. */
    @Column(name="nation_ca")
    private String nationCa;
    
    /** The district ca. */
    @Column(name="district_ca")
    private String districtCa;
    
    /** The ward pr. */
    @Column(name="ward_ca")
    private String wardCa;
    
    /** The probationary day. */
    @Column(name="probationary_day")
    private String probationaryDay;
    
    /** The official day. */
    @Column(name="official_day")
    private String officialDay;
    
    /** The work location. */
    @Column(name="work_location")
    private String workLocation;
    
    /** The type contract. */
    @Column(name="type_contract")
    private Integer typeContract;
    
    /** The status. */
    @Column(name="status")
    private Integer status;
    
    /** The reason leave. */
    @Column(name="reason_leave")
    private String reasonLeave;
    
    /** The day off. */
    @Column(name="day_off")
    private Date dayOff;
    
    /** The delete flag. */
    @Column(name="delete_flag")
    private Integer deleteFlag;
    
    /** The create time. */
    @Column(name="create_time")
    private LocalDateTime createTime;
    
    /** The update time. */
    @Column(name="update_time")
    private LocalDateTime updateTime;
    
    /** The division id. */
    @Column(name="division_id")
    private Integer divisionId;
    
    /** The position id. */
    @Column(name="position_id")
    private Integer positionId;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getProvincePr() {
        return provincePr;
    }

    public void setProvincePr(String provincePr) {
        this.provincePr = provincePr;
    }

    public String getDistrictPr() {
        return districtPr;
    }

    public void setDistrictPr(String districtPr) {
        this.districtPr = districtPr;
    }

    public String getWardPr() {
        return wardPr;
    }

    public void setWardPr(String wardPr) {
        this.wardPr = wardPr;
    }

    public String getNationCa() {
        return nationCa;
    }

    public void setNationCa(String nationCa) {
        this.nationCa = nationCa;
    }

    public String getDistrictCa() {
        return districtCa;
    }

    public void setDistrictCa(String districtCa) {
        this.districtCa = districtCa;
    }

    public String getWardCa() {
        return wardCa;
    }

    public void setWardCa(String wardCa) {
        this.wardCa = wardCa;
    }

    public String getProbationaryDay() {
        return probationaryDay;
    }

    public void setProbationaryDay(String probationaryDay) {
        this.probationaryDay = probationaryDay;
    }

    public String getOfficialDay() {
        return officialDay;
    }

    public void setOfficialDay(String officialDay) {
        this.officialDay = officialDay;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Integer getTypeContract() {
        return typeContract;
    }

    public void setTypeContract(Integer typeContract) {
        this.typeContract = typeContract;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReasonLeave() {
        return reasonLeave;
    }

    public void setReasonLeave(String reasonLeave) {
        this.reasonLeave = reasonLeave;
    }

    public Date getDayOff() {
        return dayOff;
    }

    public void setDayOff(Date dayOff) {
        this.dayOff = dayOff;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

}
