/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
 */
package jp.co.gzr_internal.api.service.dto.request;

import java.util.List;

import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;

/**
 * The Class RequestOTRequestDto.
 */
public class CreateRequestRequestDto
{
    /** The project name. */
    @NotNull(itemName = "{project code}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String projectCode;
    
    /** The reason. */
    @NotNull(itemName = "{reason}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String reason;
    
    /** The request detail. */
    @NotNull(itemName = "{requestDetail}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private List<RequestDetailRequestDto> requestDetail;

    /**
     * Gets the project name.
     *
     * @return the project name
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the new project name
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason.
     *
     * @param reason the new reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the request detail.
     *
     * @return the request detail
     */
    public List<RequestDetailRequestDto> getRequestDetail() {
        return requestDetail;
    }

    /**
     * Sets the request detail.
     *
     * @param requestDetail the new request detail
     */
    public void setRequestDetail(List<RequestDetailRequestDto> requestDetail) {
        this.requestDetail = requestDetail;
    }
}
