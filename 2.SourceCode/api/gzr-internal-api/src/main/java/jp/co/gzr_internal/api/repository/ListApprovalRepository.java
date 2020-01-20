/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 07, 2019
 */
package jp.co.gzr_internal.api.repository;

import java.util.List;
import jp.co.gzr_internal.api.service.dto.ListApprovalDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalSearchRequestDto;

/**
 * The Interface ListApprovalRepository.
 */
public interface ListApprovalRepository {
    /**
     * Find all approval with search setting.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @param limit the limit
     * @param offset the offset
     * @return the list
     * @throws Exception the exception
     */
    List<ListApprovalDto> findAllApproval(ApprovalSearchRequestDto approvalSearchRequestDto, Integer limit,
        Integer offset, boolean flag) throws Exception;

    /**
     * Count request with search setting.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the int
     */
    int countRequestWithSearchSetting(ApprovalSearchRequestDto approvalSearchRequestDto);
}
