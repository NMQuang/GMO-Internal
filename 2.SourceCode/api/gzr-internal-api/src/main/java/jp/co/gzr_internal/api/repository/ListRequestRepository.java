/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 27, 2019 
 */
package jp.co.gzr_internal.api.repository;

import java.util.List;
import jp.co.gzr_internal.api.service.dto.ListRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ListRequestRequestDto;

/**
 * The Interface ListRequestRepository.
 */
public interface ListRequestRepository 
{
    
    /**
     * Find all request with search setting.
     *
     * @param listRequestRequestDto the list request request dto
     * @param limit the limit
     * @param offset the offset
     * @return the list
     */
    List<ListRequestDto> findAllRequestWithSearchSetting(ListRequestRequestDto listRequestRequestDto, Integer limit, Integer offset);
    
    /**
     * Count request with search setting.
     *
     * @param listRequestRequestDto the list request request dto
     * @return the int
     */
    int countRequestWithSearchSetting(ListRequestRequestDto listRequestRequestDto);
    
    List<Integer> selectListDetailIdByRequestId(String requestId);
}
