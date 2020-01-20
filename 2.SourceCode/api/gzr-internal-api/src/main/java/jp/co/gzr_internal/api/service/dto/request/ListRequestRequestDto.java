/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 27, 2019
 */
package jp.co.gzr_internal.api.service.dto.request;

/**
 * The Class ListRequestRequestDto.
 */
public class ListRequestRequestDto
{
    /** The project name. */
    private String projectName;
    
    /** The request ot date. */
    private String requestOtDate;
    
    /** The status. */
    private String status;
    
    /** The current page. */
    private String currentPage;
    
    /** The total records. */
    private String totalRecords;
    
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
     * Gets the request ot date.
     *
     * @return the request ot date
     */
    public String getRequestOtDate() {
        return requestOtDate;
    }

    /**
     * Sets the request ot date.
     *
     * @param requestOtDate the new request ot date
     */
    public void setRequestOtDate(String requestOtDate) {
        this.requestOtDate = requestOtDate;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
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
