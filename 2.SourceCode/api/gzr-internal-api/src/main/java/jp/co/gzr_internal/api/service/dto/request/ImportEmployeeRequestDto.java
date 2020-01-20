package jp.co.gzr_internal.api.service.dto.request;

import java.time.LocalDateTime;
import java.util.Date;

import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;

public class ImportEmployeeRequestDto {

    /** The employee code. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private String employeeCode;

    /** The employee name. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private String employeeName;

    /** The email. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private String email;

    /** The gender. */
    private Integer gender;

    /** The address. */
    private String address;
    
    private String phoneNumber;

    /** The birthday. */
    private String birthday;

    /** The birthplace. */
    private String birthplace;

    /** The province pr. */
    private String provincePr;

    /** The district pr. */
    private String districtPr;

    /** The ward pr. */
    private String wardPr;

    /** The nation ca. */
    private String nationCa;

    /** The district ca. */
    private String districtCa;
    
    private String wardCa;

    /** The probationary day. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private String probationaryDay;

    /** The official day. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private String officialDay;

    /** The work location. */
    private String workLocation;

    /** The type contract. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private Integer typeContract;

    /** The status. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private Integer status;

    /** The reason leave. */
    private String reasonLeave;

    /** The day off. */
    private Date dayOff;

    /** The delete flag. */
    private Integer deleteFlag;

    /** The create time. */
    private LocalDateTime createTime;

    /** The update time. */
    private LocalDateTime updateTime;
    
    /** The division id. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
    private Integer divisionId;

    /** The position id. */
    @NotNull(message = "{"+ MessageContants.CONST_VALIDATE_NOT_NULL +"}")
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
