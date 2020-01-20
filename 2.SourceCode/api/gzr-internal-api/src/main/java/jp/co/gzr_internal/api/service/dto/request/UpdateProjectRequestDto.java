package jp.co.gzr_internal.api.service.dto.request;

import java.io.Serializable;
import java.util.List;

import jp.co.gzr_internal.api.service.ListProjectDetailDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.date.DateExist;
import jp.co.gzr_internal.api.web.rest.validator.annotation.date.DateFormat;
import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.project.UpdateProject;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

@UpdateProject()
public class UpdateProjectRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =10)
    private String projectCode;
    
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =150)
    private String projectNameJP;
    
    /** The project name VN. */
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =150)
    private String projectNameVN;
    
    /** The billable effort. */
    @NotNull(itemName = "Billable Effort", message = "{ERROR_009}", errorCode = "ERROR_009")
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =250)
    private String billableEffort;
    
    /** The start date. */
    @NotNull(itemName = "startDate", message = "{ERROR_009}", errorCode = "ERROR_009")
    @DateFormat(regex = ValidateCommon.DATE_REGEX, formatdate = Contants.CONST_STR_PATTERN_YYYYMMDD, message = "{ERROR_016}")
    @DateExist(regex = ValidateCommon.DATE_REGEX, pattern = Contants.CONST_STR_PATTERN_YYYYMMDD, message = "{ERROR_017}")
    private String startDate;
    
    /** The end date. */
    @DateFormat(regex = ValidateCommon.DATE_REGEX, formatdate = Contants.CONST_STR_PATTERN_YYYYMMDD, message = "{ERROR_016}")
    @DateExist(regex = ValidateCommon.DATE_REGEX, pattern = Contants.CONST_STR_PATTERN_YYYYMMDD, message = "{ERROR_017}")
    private String endDate;
    
    /** The customer name. */
    @NotNull(itemName = "Customer Name", message = "{ERROR_009}", errorCode = "ERROR_009")
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =150)
    private String customerName;
    
    /** The sale. */
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =50)
    private String sale;
    
    /** The project rank. */
    @NotNull(itemName = "ProjectRank", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String projectRank;
    
    /** The scope. */
    @NotNull(itemName = "Scope", message = "{ERROR_009}", errorCode = "ERROR_009")
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =300)
    private String scope;
    
    /** The objectives. */
    @NotNull(itemName = "Objectives", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String objectives;
    
    /** The email CC. */
    private String[] emailCC;
    
    private List<ListProjectDetailDto> memberList;

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

    public String[] getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(String[] emailCC) {
        this.emailCC = emailCC;
    }

    public List<ListProjectDetailDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ListProjectDetailDto> memberList) {
        this.memberList = memberList;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    
}
