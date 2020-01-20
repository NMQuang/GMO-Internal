package jp.co.gzr_internal.api.service.dto;

/**
 * The Class EmployeeDto.
 */
public class EmployeeDto {

    private Integer id;
    /** The employee code. */
    private String employeeCode;

    /** The employee name. */
    private String employeeName;

    /** The email. */
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
    private String probationaryDay;

    /** The official day. */
    private String officialDay;

    /** The work location. */
    private String workLocation;

    /** The type contract. */
    private String typeContract;

    /** The status. */
    private String status;

    /** The reason leave. */
    private String reasonLeave;

    /** The day off. */
    private String dayOff;

    /** The delete flag. */
    private Integer deleteFlag;

    /** The create time. */
    private String createTime;

    /** The update time. */
    private String updateTime;
    
    /** The division. */
    private String division;

    /** The position. */
    private String position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getReasonLeave() {
        return reasonLeave;
    }

    public void setReasonLeave(String reasonLeave) {
        this.reasonLeave = reasonLeave;
    }

    public String getDayOff() {
        return dayOff;
    }

    public void setDayOff(String dayOff) {
        this.dayOff = dayOff;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    
    public EmployeeDto(Integer id, String employeeCode, String employeeName, String email, Integer gender,
        String address, String phoneNumber, String birthday, String birthplace, String provincePr, String districtPr,
        String wardPr, String nationCa, String districtCa, String wardCa, String probationaryDay, String officialDay,
        String workLocation, String typeContract, String status, String reasonLeave, String dayOff, Integer deleteFlag,
        String createTime, String updateTime, String division, String position) {
        super();
        this.id = id;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.birthplace = birthplace;
        this.provincePr = provincePr;
        this.districtPr = districtPr;
        this.wardPr = wardPr;
        this.nationCa = nationCa;
        this.districtCa = districtCa;
        this.wardCa = wardCa;
        this.probationaryDay = probationaryDay;
        this.officialDay = officialDay;
        this.workLocation = workLocation;
        this.typeContract = typeContract;
        this.status = status;
        this.reasonLeave = reasonLeave;
        this.dayOff = dayOff;
        this.deleteFlag = deleteFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.division = division;
        this.position = position;
    }

    public EmployeeDto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EmployeeDto(String employeeName, String email) {
        super();
        this.employeeName = employeeName;
        this.email = email;
    }
    
    
    
    

}
