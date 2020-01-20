package jp.co.gzr_internal.api.service.dto.request;

import java.util.List;

public class SearchProjectRequestDto {

    private String projectName;
    
    private String startDate;
    
    private String endDate;
    
    private String page;
    
    private String totalRecordOfPage;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
    
    
}
