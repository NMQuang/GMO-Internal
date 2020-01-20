/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.service.dto.response;

import jp.co.gzr_internal.api.service.dto.PageDto;

/**
 * The Class DataAndPageResponseDto.
 */
public class DataAndPageResponseDto
{
    /** The page dto. */
    private PageDto pageDto;

    /** The data result. */
    private Object dataResult;

    /**
     * Gets the page dto.
     *
     * @return the page dto
     */
    public PageDto getPageDto() {
        return pageDto;
    }

    /**
     * Sets the page dto.
     *
     * @param pageDto the new page dto
     */
    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }

    /**
     * Gets the data result.
     *
     * @return the data result
     */
    public Object getDataResult() {
        return dataResult;
    }

    /**
     * Sets the data result.
     *
     * @param dataResult the new data result
     */
    public void setDataResult(Object dataResult) {
        this.dataResult = dataResult;
    }
}
