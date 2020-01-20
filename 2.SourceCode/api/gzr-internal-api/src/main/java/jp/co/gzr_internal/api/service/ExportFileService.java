/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 11, 2019
 */
package jp.co.gzr_internal.api.service;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import jp.co.gzr_internal.api.service.dto.response.ListApprovalResponseDto;

/**
 * The Interface ExportFileService.
 */
public interface ExportFileService
{

    /**
     * Export list approval.
     *
     * @param keyTotalValue the key total value
     * @param keyRowTotal the key row total
     * @param daysOfWeek the days of week
     * @param mapRowTotal the map row total
     * @param listMapDateValue the list map date value
     * @param columns the columns
     * @param employees the employees
     * @param numDays the num days
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> exportListApproval(String keyTotalApprovedOT, String approvalOT, String keyTotalValue,
        String keyRowTotal, Map<String, String> daysOfWeek,
        Map<String, Integer> mapRowTotal,
        List<Map<String, Integer>> listMapDateValue, List<String> columns, List<ListApprovalResponseDto> employees,
        Integer numDays, String dateSearch) throws Exception;
}
