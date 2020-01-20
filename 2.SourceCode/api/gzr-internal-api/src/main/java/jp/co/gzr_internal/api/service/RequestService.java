/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.service;

import org.springframework.http.ResponseEntity;

import jp.co.gzr_internal.api.service.dto.request.ApprovalRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalSearchRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CancelRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CreateRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ListRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateRequestRequestDto;

/**
 * The Interface CreateRequestService.
 */
public interface RequestService
{
    /**
     * Creates the request.
     *
     * @param requestRequestDto the request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> createRequest(CreateRequestRequestDto requestRequestDto) throws Exception;

    /**
     * Gets the list request.
     *
     * @param listRequestRequestDto the list request request dto
     * @return the list request
     * @throws Exception the exception
     */
    ResponseEntity<?> getListRequest(ListRequestRequestDto listRequestRequestDto) throws Exception;

    /**
     * Gets the detail request.
     *
     * @param requestId the request id
     * @return the detail request
     * @throws Exception the exception
     */
    ResponseEntity<?> getDetailRequest(DetailRequestDto detailRequestDto) throws Exception;

    /**
     * Update request.
     *
     * @param requestRequestDto the request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> updateRequest(UpdateRequestRequestDto requestRequestDto) throws Exception;

    /**
     * Gets the approval request.
     *
     * @param approvalRequestDto the approval request dto
     * @return the approval request
     * @throws Exception the exception
     */
    ResponseEntity<?> getApprovalRequest(ApprovalRequestDto approvalRequestDto) throws Exception;

    /**
     * Cancel request.
     *
     * @param cancelRequestDto the cancel request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> cancelRequest(CancelRequestDto cancelRequestDto) throws Exception;

    /**
     * Gets the projects.
     *
     * @return the projects
     * @throws Exception the exception
     */
    ResponseEntity<?> getProjects() throws Exception;

    /**
     * Gets the list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the list approval
     * @throws Exception the exception
     */
    ResponseEntity<?> getListApproval(ApprovalSearchRequestDto approvalSearchRequestDto) throws Exception;

    /**
     * Ex port list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> exportListApproval(ApprovalSearchRequestDto approvalSearchRequestDto) throws Exception;
    
    
    ResponseEntity<?> getProjectByEmployee() throws Exception;
    
    ResponseEntity<?>  getListEmployeeByProjectCode(String projectCode) throws Exception;
    
    ResponseEntity<?>  getPositionByEmployee(String projectCode, String employeeCode) throws Exception;
}
