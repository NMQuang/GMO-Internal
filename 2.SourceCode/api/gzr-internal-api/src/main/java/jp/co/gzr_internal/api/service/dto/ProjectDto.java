package jp.co.gzr_internal.api.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class ProjectDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectCode;

    private String projectNameJP;

    private String projectNameVN;

    private String billableEffort;

    private String startDate;

    private String endDate;

    private String customerName;

    private String sale;

    private String projectRank;

    private String scope;

    private String objectives;

    private String emailCC;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

  
    public Integer getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Integer projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectNameJP() {
        return projectNameJP;
    }

    public void setProjectNameJP(String projectNameJP) {
        this.projectNameJP = projectNameJP;
    }

    public String getProjectNameVN() {
        return projectNameVN;
    }

    public void setProjectNameVN(String projectNameVN) {
        this.projectNameVN = projectNameVN;
    }

    public String getBillableEffort() {
        return billableEffort;
    }

    public void setBillableEffort(String billableEffort) {
        this.billableEffort = billableEffort;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getProjectRank() {
        return projectRank;
    }

    public void setProjectRank(String projectRank) {
        this.projectRank = projectRank;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String emailCC) {
        this.emailCC = emailCC;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

}
