package jp.co.gzr_internal.api.service.dto.request;

import java.util.List;

public class ExportEmployeeRequestDto {

    private String employeeCode;
    
    private String empployeeName;
    
    private String birthday;
    
    private String position;
    
    private String division;
    
    private String probationaryDay;
    
    private String officialDay;
    
    private String workContact;
    
    private String status;
    
    private String page;
    
    private String totalRecordOfPage;
    
    private String optionFlag;

    private List<String> headerList;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmpployeeName() {
        return empployeeName;
    }

    public void setEmpployeeName(String empployeeName) {
        this.empployeeName = empployeeName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
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

    public String getWorkContact() {
        return workContact;
    }

    public void setWorkContact(String workContact) {
        this.workContact = workContact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalRecordOfPage() {
        return totalRecordOfPage;
    }

    public void setTotalRecordOfPage(String totalRecordOfPage) {
        this.totalRecordOfPage = totalRecordOfPage;
    }

    public String getOptionFlag() {
        return optionFlag;
    }

    public void setOptionFlag(String optionFlag) {
        this.optionFlag = optionFlag;
    }

    public List<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }


}
