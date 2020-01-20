package jp.co.gzr_internal.api.service.dto;

import java.io.Serializable;
import java.util.Date;

public class ListProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectCode;
    
    private String projectName;
    
    private String billableEffort;
    
    private String employeeName;
    
    private String positionName;
    
    private Date startDate;
    
    private Date endDate;

    public Integer getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Integer projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBillableEffort() {
        return billableEffort;
    }

    public void setBillableEffort(String billableEffort) {
        this.billableEffort = billableEffort;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
