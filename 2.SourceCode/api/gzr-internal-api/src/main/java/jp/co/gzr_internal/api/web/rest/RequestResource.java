/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 26, 2019
 */
package jp.co.gzr_internal.api.web.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.gzr_internal.api.service.RequestService;
import jp.co.gzr_internal.api.service.dto.request.ApprovalRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalSearchRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CancelRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CreateRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ListRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.PositionByEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateRequestRequestDto;
import jp.co.gzr_internal.api.web.rest.validator.ApprovalRequestValidator;
import jp.co.gzr_internal.api.web.rest.validator.CreateRequestValidator;
import jp.co.gzr_internal.api.web.rest.validator.UpdateRequestValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestResource.
 */
@RestController
@RequestMapping("api")
public class RequestResource
{
    /** The request service. */
    private final RequestService requestService;

    /** The create request validator. */
    private final CreateRequestValidator createRequestValidator;

    /** The update request validator. */
    private final UpdateRequestValidator updateRequestValidator;

    /** The approval request validator. */
    private final ApprovalRequestValidator approvalRequestValidator;

    /**
     * Instantiates a new request resource.
     *
     * @param requestService the request service
     * @param createRequestValidator the create request validator
     * @param updateRequestValidator the update request validator
     * @param approvalRequestValidator the approval request validator
     */
    public RequestResource(RequestService requestService, CreateRequestValidator createRequestValidator,
        UpdateRequestValidator updateRequestValidator, ApprovalRequestValidator approvalRequestValidator) {
        this.requestService = requestService;
        this.createRequestValidator = createRequestValidator;
        this.updateRequestValidator = updateRequestValidator;
        this.approvalRequestValidator = approvalRequestValidator;
    }

    /**
     * Sets the up create request binder.
     *
     * @param binder the new up create request binder
     */
    @InitBinder({
        "createRequestRequestDto"
    })
    public void setupCreateRequestBinder(WebDataBinder binder) {
        binder.addValidators(createRequestValidator);
    }

    /**
     * Sets the up update request binder.
     *
     * @param binder the new up update request binder
     */
    @InitBinder({
        "updateRequestRequestDto"
    })
    public void setupUpdateRequestBinder(WebDataBinder binder) {
        binder.addValidators(updateRequestValidator);
    }

    /**
     * Sets the up approval request binder.
     *
     * @param binder the new up approval request binder
     */
    @InitBinder({
        "approvalRequestDto"
    })
    public void setupApprovalRequestBinder(WebDataBinder binder) {
        binder.addValidators(approvalRequestValidator);
    }

    /**
     * Creates the request.
     *
     * @param createRequestRequestDto the create request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/create")
    public ResponseEntity<?> createRequest(@Valid @RequestBody CreateRequestRequestDto createRequestRequestDto)
        throws Exception {
        return requestService.createRequest(createRequestRequestDto);
    }

    /**
     * Update request.
     *
     * @param updateRequestRequestDto the update request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/update")
    public ResponseEntity<?> updateRequest(@Valid @RequestBody UpdateRequestRequestDto updateRequestRequestDto)
        throws Exception {
        return requestService.updateRequest(updateRequestRequestDto);
    }

    /**
     * Detail request.
     *
     * @param detailRequestDto the detail request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/detail")
    public ResponseEntity<?> detailRequest(@Valid @RequestBody DetailRequestDto detailRequestDto) throws Exception {
        return requestService.getDetailRequest(detailRequestDto);
    }

    /**
     * List request.
     *
     * @param listRequestRequestDto the list request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/list")
    public ResponseEntity<?> listRequest(@Valid @RequestBody ListRequestRequestDto listRequestRequestDto)
        throws Exception {
        return requestService.getListRequest(listRequestRequestDto);
    }

    /**
     * Gets the approval request.
     *
     * @param approvalRequestDto the approval request dto
     * @return the approval request
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/get-approval")
    public ResponseEntity<?> getApprovalRequest(@Valid @RequestBody ApprovalRequestDto approvalRequestDto)
        throws Exception {
        return requestService.getApprovalRequest(approvalRequestDto);
    }

    /**
     * Cancel request.
     *
     * @param cancelRequestDto the cancel request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/cancel")
    public ResponseEntity<?> cancelRequest(@Valid @RequestBody CancelRequestDto cancelRequestDto)
        throws Exception {
        return requestService.cancelRequest(cancelRequestDto);
    }

    /**
     * Gets the projects.
     *
     * @return the projects
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/get-projects")
    public ResponseEntity<?> getProjects()
        throws Exception {
        return requestService.getProjects();
    }

    /**
     * Gets the list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the list approval
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/get-list-approval")
    public ResponseEntity<?> getListApproval(@Valid @RequestBody ApprovalSearchRequestDto approvalSearchRequestDto)
        throws Exception {
        return requestService.getListApproval(approvalSearchRequestDto);
    }
    
    /**
     * Ex port list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/export-list-approval")
    public ResponseEntity<?> exportListApproval(@RequestBody ApprovalSearchRequestDto approvalSearchRequestDto)
        throws Exception {
        return requestService.exportListApproval(approvalSearchRequestDto);
    }
    
    /**
     * Execute get project by employee code.
     *
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/project-list")
    public ResponseEntity<?> executeGetProjectByEmployeeCode()
        throws Exception {
        return requestService.getProjectByEmployee();
    }
    
    /**
     * Execute get employee by project code.
     *
     * @param requestDto the request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/employee-list")
    public ResponseEntity<?> executeGetEmployeeByProjectCode(@Valid @RequestBody DetailProjectRequestDto requestDto)
        throws Exception {
        return requestService.getListEmployeeByProjectCode(requestDto.getProjectCode());
    }
    
    /**
     * Execute get position by employee.
     *
     * @param requestDto the request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/request-ots/position-list")
    public ResponseEntity<?> executeGetPositionByEmployee(@Valid @RequestBody PositionByEmployeeRequestDto requestDto)
        throws Exception {
        return requestService.getPositionByEmployee(requestDto.getProjectCode(), requestDto.getEmployeeCode());
    }
}
