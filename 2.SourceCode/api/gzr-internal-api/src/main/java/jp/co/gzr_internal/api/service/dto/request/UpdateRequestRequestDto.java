/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
 */
package jp.co.gzr_internal.api.service.dto.request;

import java.util.List;

import jp.co.gzr_internal.api.web.rest.validator.annotation.maxlength.MaxLength;
import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;

/**
 * The Class RequestOTRequestDto.
 */
public class UpdateRequestRequestDto
{
    /** The project name. */
    @MaxLength(itemName = "project name", partition = "{maxLength}", message = "{ERROR_008}", errorCode = "ERROR_008", maxLength =10)
    @NotNull(itemName = "{project name}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String projectCode;
    
    /** The reason. */
    @NotNull(itemName = "{reason}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String reason;
    
    /** The request detail. */
    @NotNull(itemName = "{requestDetail}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private List<RequestDetailRequestDto> requestDetail;
    
    /** The update at. */
    private String updateAt;
    
    /** The request id. */
    @NotNull(itemName = "{request id}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String requestId;


    /**
     * Gets the project code.
     *
     * @return the project code
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the project code.
     *
     * @param projectCode the new project code
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

    /**
     * Gets the update at.
     *
     * @return the updateAt
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * Sets the update at.
     *
     * @param updateAt the updateAt to set
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * Gets the request id.
     *
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the request id.
     *
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
