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
 * The Class ApprovalRequestDto.
 */
public class ApprovalRequestDto
{
    /** The request detail. */
    private List<ApprovalDetailRequestDto> requestDetail;

    /** The update at. */
    private String updateAt;

    /** The request id. */
    @NotNull(itemName = "{request id}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String requestId;

    /** The project name. */
    private String projectCode;
    
    private String status;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the request detail.
     *
     * @return the requestDetail
     */
    public List<ApprovalDetailRequestDto> getRequestDetail() {
        return requestDetail;
    }

    /**
     * Sets the request detail.
     *
     * @param requestDetail the requestDetail to set
     */
    public void setRequestDetail(List<ApprovalDetailRequestDto> requestDetail) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
