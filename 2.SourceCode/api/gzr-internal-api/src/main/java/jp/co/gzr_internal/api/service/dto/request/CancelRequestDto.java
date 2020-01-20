/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
 */
package jp.co.gzr_internal.api.service.dto.request;

import jp.co.gzr_internal.api.web.rest.validator.annotation.notnull.NotNull;

/**
 * The Class ApprovalRequestDto.
 */
public class CancelRequestDto
{

    private String updateAt;

    @NotNull(itemName = "{request id}", message = "{ERROR_009}", errorCode = "ERROR_009")
    private String requestId;

    /**
     * @return the updateAt
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * @param updateAt the updateAt to set
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
}
