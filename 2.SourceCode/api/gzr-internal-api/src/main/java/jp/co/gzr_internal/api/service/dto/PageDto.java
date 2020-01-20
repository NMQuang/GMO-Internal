/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.service.dto;

public class PageDto
{
    /** The total record. */
    private int totalRecord;
    
    /** The current page. */
    private String currentPage;
    
    /** The total record at page. */
    private String totalRecordAtPage;

    /**
     * Instantiates a new page dto.
     */
    public PageDto() {}

    /**
     * Instantiates a new page dto.
     *
     * @param totalRecord the total record
     * @param currentPage the current page
     * @param totalRecordAtPage the total record at page
     */
    public PageDto(int totalRecord, String currentPage, String totalRecordAtPage) {
        super();
        this.totalRecord = totalRecord;
        this.currentPage = currentPage;
        this.totalRecordAtPage = totalRecordAtPage;
    }

    /**
     * Gets the total record.
     *
     * @return the totalRecord
     */
    public int getTotalRecord() {
        return totalRecord;
    }
    
    /**
     * Sets the total record.
     *
     * @param totalRecord the totalRecord to set
     */
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    
    /**
     * Gets the current page.
     *
     * @return the currentPage
     */
    public String getCurrentPage() {
        return currentPage;
    }
    
    /**
     * Sets the current page.
     *
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
    
    /**
     * Gets the total record at page.
     *
     * @return the totalRecordAtPage
     */
    public String getTotalRecordAtPage() {
        return totalRecordAtPage;
    }
    
    /**
     * Sets the total record at page.
     *
     * @param totalRecordAtPage the totalRecordAtPage to set
     */
    public void setTotalRecordAtPage(String totalRecordAtPage) {
        this.totalRecordAtPage = totalRecordAtPage;
    }
}
