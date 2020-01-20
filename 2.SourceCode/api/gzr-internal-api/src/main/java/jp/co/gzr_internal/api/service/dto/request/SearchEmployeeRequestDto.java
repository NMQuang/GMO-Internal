package jp.co.gzr_internal.api.service.dto.request;

import java.util.List;

public class SearchEmployeeRequestDto {

    private String employeeCode;
    
    private String employeeName;
    
    private String birthday;
    
    private String position;
    
    private String division;
    
    private String probationaryDay;
    
    private String officialDay;
    
    private String workContact;
    
    private String status;
    
    private String page;
    
    private String totalRecordOfPage;
    
    private String searchOrExportFlag;
    
    private String headerOptionFlag;

    private List<String> headerList;

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

    public List<String> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<String> headerList) {
        this.headerList = headerList;
    }

    public String getSearchOrExportFlag() {
        return searchOrExportFlag;
    }

    public void setSearchOrExportFlag(String searchOrExportFlag) {
        this.searchOrExportFlag = searchOrExportFlag;
    }

    public String getHeaderOptionFlag() {
        return headerOptionFlag;
    }

    public void setHeaderOptionFlag(String headerOptionFlag) {
        this.headerOptionFlag = headerOptionFlag;
    }
    
    
}
