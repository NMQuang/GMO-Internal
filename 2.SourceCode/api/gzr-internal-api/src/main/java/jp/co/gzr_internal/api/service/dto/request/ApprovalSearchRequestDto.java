/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 07, 2019
 */
package jp.co.gzr_internal.api.service.dto.request;

import javax.validation.constraints.NotNull;

/**
 * The Class ApprovalSearchRequestDto.
 */
public class ApprovalSearchRequestDto
{
    /** The project name. */
    private String projectName;

    /** The request date. */
    @NotNull
    private String requestDate;

    /** The current page. */
    private String currentPage;

    /** The total records. */
    private String totalRecords;

    /**
     * Gets the project name.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the new project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the request date.
     *
     * @return the request date
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the request date.
     *
     * @param requestDate the new request date
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * Gets the current page.
     *
     * @return the current page
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page.
     *
     * @param currentPage the new current page
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets the total records.
     *
     * @return the total records
     */
    public String getTotalRecords() {
        return totalRecords;
    }

    /**
     * Sets the total records.
     *
     * @param totalRecords the new total records
     */
    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

}
