package jp.co.gzr_internal.api.service.dto.request;

import jp.co.gzr_internal.api.web.rest.validator.annotation.UpdateEmployee;
import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.pattern.Pattern;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

@UpdateEmployee
public class UpdateEmployeeRequestDto {
 
    /** The employee code. */
    @NotNull(itemName = "employeeCode", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String employeeCode;

    /** The employee name. */
    @NotNull(itemName = "employeeName", message = "{ERROR_009}", errorCode = "ERROR_009")
    @MaxLength(itemName = "employeeName", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =50)
    private String employeeName;

    /** The email. */
    @NotNull(itemName = "email", message = "{ERROR_009}", errorCode = "ERROR_009")
    @MaxLength(itemName = "email", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =50)
    @Pattern(itemName = "email", message = "{ERROR_009}", errorCode = "ERROR_009", pattern = ValidateCommon.EMAIL_REGEX)
    private String email;

    /** The gender. */
    private Integer gender;

    /** The address. */
    @MaxLength(itemName = "address", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =200)
    private String address;
    
    private String phoneNumber;

    /** The birthday. */
    private String birthday;

    /** The probationary day. */
    @NotNull(itemName = "probationaryDay", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String probationaryDay;

    /** The official day. */
    @NotNull(itemName = "officialDay", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String officialDay;

    /** The work location. */
    @MaxLength(itemName = "workLocation", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =100)
    private String workLocation;

    /** The type contract. */
    @NotNull(itemName = "typeContract", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String typeContract;

    /** The status. */
    @NotNull(itemName = "status", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String status;

    /** The division. */
    @NotNull(itemName = "division", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String division;

    /** The position. */
    @NotNull(itemName = "position", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String position;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getTypeContract() {
        return typeContract;
    }

    public void setTypeContract(String typeContract) {
        this.typeContract = typeContract;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
    
}
