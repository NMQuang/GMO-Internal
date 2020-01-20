/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CommonListResponseUtil.
 */
public class CommonListResponseUtil {

    /** The current page. */
    @JsonProperty("current_page")
    private Integer currentPage;

    /** The no of pages. */
    @JsonProperty("no_of_page")
    private Integer noOfPages;

    /** The result list. */
    @JsonProperty("result_list")
    private List<?> resultList;

    /**
     * Instantiates a new common list response util.
     */
    @SuppressWarnings("rawtypes")
    public CommonListResponseUtil() {
        this.resultList = new ArrayList();
    }

    /**
     * Gets the current page.
     *
     * @return the current page
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page.
     *
     * @param currentPage the new current page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets the no of pages.
     *
     * @return the no of pages
     */
    public Integer getNoOfPages() {
        return noOfPages;
    }

    /**
     * Sets the no of pages.
     *
     * @param noOfPages the new no of pages
     */
    public void setNoOfPages(Integer noOfPages) {
        this.noOfPages = noOfPages;
    }

    /**
     * Gets the result list.
     *
     * @return the result list
     */
    public List<?> getResultList() {
        return resultList;
    }

    /**
     * Sets the result list.
     *
     * @param resultList the new result list
     */
    public void setResultList(List<?> resultList) {
        this.resultList = resultList;
    }
}
