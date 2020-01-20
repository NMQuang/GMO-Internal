package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_code")
    private Integer projectCode;
    
    @Column(name = "project_name_jp")
    private String projectNameJP;
    
    @Column(name = "project_name_vn")
    private String projectNameVN;
    
    @Column(name = "billable_effort")
    private String billableEffort;
    
    @Column(name = "start_date")
    private Date startDate;
    
    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "sale")
    private String sale;
    
    @Column(name = "project_rank")
    private Integer projectRank;
    
    @Column(name = "scope")
    private String scope;
    
    @Column(name = "objectives")
    private String objectives;
    
    @Column(name = "email_cc")
    private String emailCC;
    
    @Column(name = "create_time")
    private LocalDateTime createDate;
    
    @Column(name = "update_time")
    private LocalDateTime updateDate;
    
    @Column(name = "delete_flag")
    private Integer deleteFlag;

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

    public Integer getProjectRank() {
        return projectRank;
    }

    public void setProjectRank(Integer projectRank) {
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
