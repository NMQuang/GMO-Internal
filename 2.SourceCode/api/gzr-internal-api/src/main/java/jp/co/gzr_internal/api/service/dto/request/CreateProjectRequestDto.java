/*	
 * Aviva SMS Revamp
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: HiepNH
 * Creation Date : Dec 16, 2019
 */
package jp.co.gzr_internal.api.service.dto.request;

import java.io.Serializable;

import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.validator.annotation.date.DateExist;
import jp.co.gzr_internal.api.web.rest.validator.annotation.date.DateFormat;
import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;
import jp.co.gzr_internal.api.web.rest.validator.annotation.project.CreateProject;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class CreateProjectRequestDto.
 */
@CreateProject()
public class CreateProjectRequestDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The project name JP. */
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
    
    /** The project managerment. */
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =10)
    private String projectManagement;
    
    /** The br SE. */
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =10)
    private String brSE;
    
    /** The team lead. */
    @MaxLength(partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =10)
    private String teamLead;
    
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

    /**
     * Gets the project name JP.
     *
     * @return the project name JP
     */
    public String getProjectNameJP() {
        return projectNameJP;
    }

    /**
     * Sets the project name JP.
     *
     * @param projectNameJP the new project name JP
     */
    public void setProjectNameJP(String projectNameJP) {
        this.projectNameJP = projectNameJP;
    }

    /**
     * Gets the project name VN.
     *
     * @return the project name VN
     */
    public String getProjectNameVN() {
        return projectNameVN;
    }

    /**
     * Sets the project name VN.
     *
     * @param projectNameVN the new project name VN
     */
    public void setProjectNameVN(String projectNameVN) {
        this.projectNameVN = projectNameVN;
    }

    /**
     * Gets the billable effort.
     *
     * @return the billable effort
     */
    public String getBillableEffort() {
        return billableEffort;
    }

    /**
     * Sets the billable effort.
     *
     * @param billableEffort the new billable effort
     */
    public void setBillableEffort(String billableEffort) {
        this.billableEffort = billableEffort;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name.
     *
     * @param customerName the new customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the sale.
     *
     * @return the sale
     */
    public String getSale() {
        return sale;
    }

    /**
     * Sets the sale.
     *
     * @param sale the new sale
     */
    public void setSale(String sale) {
        this.sale = sale;
    }

    /**
     * Gets the project rank.
     *
     * @return the project rank
     */
    public String getProjectRank() {
        return projectRank;
    }

    /**
     * Sets the project rank.
     *
     * @param projectRank the new project rank
     */
    public void setProjectRank(String projectRank) {
        this.projectRank = projectRank;
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     *
     * @param scope the new scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets the objectives.
     *
     * @return the objectives
     */
    public String getObjectives() {
        return objectives;
    }

    /**
     * Sets the objectives.
     *
     * @param objectives the new objectives
     */
    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    /**
     * Gets the project managerment.
     *
     * @return the project managerment
     */
    public String getProjectManagement() {
        return projectManagement;
    }

    /**
     * Sets the project managerment.
     *
     * @param projectManagerment the new project managerment
     */
    public void setProjectManagement(String projectManagement) {
        this.projectManagement = projectManagement;
    }

    /**
     * Gets the br SE.
     *
     * @return the br SE
     */
    public String getBrSE() {
        return brSE;
    }

    /**
     * Sets the br SE.
     *
     * @param brSE the new br SE
     */
    public void setBrSE(String brSE) {
        this.brSE = brSE;
    }

    /**
     * Gets the team lead.
     *
     * @return the team lead
     */
    public String getTeamLead() {
        return teamLead;
    }

    /**
     * Sets the team lead.
     *
     * @param teamLead the new team lead
     */
    public void setTeamLead(String teamLead) {
        this.teamLead = teamLead;
    }

    /**
     * Gets the email CC.
     *
     * @return the email CC
     */
    public String[] getEmailCC() {
        return emailCC;
    }

    /**
     * Sets the email CC.
     *
     * @param emailCC the new email CC
     */
    public void setEmailCC(String[] emailCC) {
        this.emailCC = emailCC;
    }
    
}
