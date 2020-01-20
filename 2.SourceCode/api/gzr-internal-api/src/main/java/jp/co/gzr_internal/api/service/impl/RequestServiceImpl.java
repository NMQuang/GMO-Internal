/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.owasp.encoder.Encode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.gzr_internal.api.config.MailConfiguration;
import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.domain.Project;
import jp.co.gzr_internal.api.domain.Request;
import jp.co.gzr_internal.api.repository.CustomEmployeeRepository;
import jp.co.gzr_internal.api.repository.CustomProjectRepository;
import jp.co.gzr_internal.api.repository.EmployeeRepository;
import jp.co.gzr_internal.api.repository.ListApprovalRepository;
import jp.co.gzr_internal.api.repository.ListRequestRepository;
import jp.co.gzr_internal.api.repository.OTTimeApprovalRepository;
import jp.co.gzr_internal.api.repository.ProjectDetailRepository;
import jp.co.gzr_internal.api.repository.ProjectRepository;
import jp.co.gzr_internal.api.repository.RequestDetailRepository;
import jp.co.gzr_internal.api.repository.RequestRepository;
import jp.co.gzr_internal.api.service.ExportFileService;
import jp.co.gzr_internal.api.service.MailService;
import jp.co.gzr_internal.api.service.RequestService;
import jp.co.gzr_internal.api.service.dto.EmployeeByPositionDto;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.EmployeeOfProjectDto;
import jp.co.gzr_internal.api.service.dto.ListApprovalDto;
import jp.co.gzr_internal.api.service.dto.ListRequestDto;
import jp.co.gzr_internal.api.service.dto.MailInfoDto;
import jp.co.gzr_internal.api.service.dto.OTTimeApprovalDto;
import jp.co.gzr_internal.api.service.dto.PageDto;
import jp.co.gzr_internal.api.service.dto.PositionByEmployeeDto;
import jp.co.gzr_internal.api.service.dto.ProjectListDto;
import jp.co.gzr_internal.api.service.dto.RequestDetailDto;
import jp.co.gzr_internal.api.service.dto.RequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalDetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalSearchRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CancelRequestDto;
import jp.co.gzr_internal.api.service.dto.request.CreateRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ListRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.RequestDetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.response.DataAndPageResponseDto;
import jp.co.gzr_internal.api.service.dto.response.ListApprovalResponseDto;
import jp.co.gzr_internal.api.service.dto.response.ListRequestResponseDto;
import jp.co.gzr_internal.api.service.dto.response.RequestDetailResponseDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.commons.enums.RequestStatusEnum;
import jp.co.gzr_internal.api.web.rest.errors.InsertErrorException;
import jp.co.gzr_internal.api.web.rest.errors.SelectDataNotExistErrorException;
import jp.co.gzr_internal.api.web.rest.errors.SelectErrorException;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * The Class RequestServiceImpl.
 */
@Service
public class RequestServiceImpl implements RequestService {

    /** The export file service. */
    private final ExportFileService exportFileService;

    /** The list request repository. */
    private final ListRequestRepository listRequestRepository;

    /** The request repository. */
    private final RequestRepository requestRepository;

    /** The list approval repository. */
    private final ListApprovalRepository listApprovalRepository;

    /** The request detail repository. */
    private final RequestDetailRepository requestDetailRepository;

    /** The mail service. */
    private final MailService mailService;

    /** The employee repository. */
    private final EmployeeRepository employeeRepository;

    /** The ot time approval repository. */
    private final OTTimeApprovalRepository otTimeApprovalRepository;

    private final CustomProjectRepository customProjectRepository;

    private final CustomEmployeeRepository customEmployeeRepository;

    private final ProjectRepository projectRepository;

    private final ProjectDetailRepository projectDetailRepository;

    @Autowired
    private MailConfiguration mailConfiguration;

    /**
     * Instantiates a new request service impl.
     *
     * @param requestRepository the request repository
     * @param requestDetailRepository the request detail repository
     * @param listRequestRepository the list request repository
     * @param mailService the mail service
     * @param employeeRepository the employee repository
     * @param listApprovalRepository the list approval repository
     * @param exportFileService the export file service
     * @param otTimeApprovalRepository the ot time approval repository
     */
    public RequestServiceImpl(RequestRepository requestRepository, RequestDetailRepository requestDetailRepository,
        ListRequestRepository listRequestRepository, MailService mailService, EmployeeRepository employeeRepository,
        ListApprovalRepository listApprovalRepository, ExportFileService exportFileService,
        OTTimeApprovalRepository otTimeApprovalRepository, CustomProjectRepository customProjectRepository,
        CustomEmployeeRepository customEmployeeRepository, ProjectRepository projectRepository,
        ProjectDetailRepository projectDetailRepository) {
        this.requestRepository = requestRepository;
        this.requestDetailRepository = requestDetailRepository;
        this.listRequestRepository = listRequestRepository;
        this.mailService = mailService;
        this.employeeRepository = employeeRepository;
        this.listApprovalRepository = listApprovalRepository;
        this.exportFileService = exportFileService;
        this.otTimeApprovalRepository = otTimeApprovalRepository;
        this.customProjectRepository = customProjectRepository;
        this.customEmployeeRepository = customEmployeeRepository;
        this.projectRepository = projectRepository;
        this.projectDetailRepository = projectDetailRepository;
    }

    /**
     * Creates the request.
     *
     * @param requestRequestDto the request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createRequest(CreateRequestRequestDto requestRequestDto) throws Exception {

        LocalDateTime createTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);

        String employeeCode = Utils.getEmployeeCodeFromLogin();
        try {
            // Create date and get idRequest
            Integer requestId = createRequestTable(employeeCode, requestRequestDto.getProjectCode(),
                requestRequestDto.getReason(), Contants.CONST_INT_DELETE_FLAG_FALSE,
                RequestStatusEnum.VERIFY.getValue(), createTime, createTime);
            // check id Request, if null then return not found
            if (requestId == null) {
                return new ResponseEntity<>(
                    ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                    HttpStatus.NOT_FOUND);
            }
            // Loop insert request detail
            requestRequestDto.getRequestDetail().forEach(requestDetail -> {
                Date dateRequestDetails = Utils.stringToDate(requestDetail.getDateOT(),
                    Contants.CONST_STR_PATTERN_YYYYMMDD);
                createRequestDetail(requestId, requestDetail.getEmployeeCode(),
                    Integer.valueOf(requestDetail.getPositionCode()).intValue(), dateRequestDetails,
                    Integer.parseInt(requestDetail.getPlanTimeOT()), requestDetail.getNote(),
                    Contants.CONST_INT_DELETE_FLAG_FALSE, createTime);
            });

            Optional<Project> projectOptional = projectRepository
                .findByProjectCodeAndDeleteFlag(Integer.valueOf(requestRequestDto.getProjectCode()).intValue(), 0);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                // Get project Name
                String projectName = null;
                if (StringUtils.equals(project.getProjectNameVN(), Contants.CONST_STR_BLANK)
                    && !StringUtils.equals(project.getProjectNameJP(), Contants.CONST_STR_BLANK)) {
                    projectName = project.getProjectNameJP();
                } else if (!StringUtils.equals(project.getProjectNameVN(), Contants.CONST_STR_BLANK)
                    && StringUtils.equals(project.getProjectNameJP(), Contants.CONST_STR_BLANK)) {
                    projectName = project.getProjectNameVN();
                } else {
                    projectName = project.getProjectNameVN();
                }
                // Send mail for request status verify
                sendMailForRequestOTStatusVerify(projectName, requestRequestDto.getReason(), employeeCode);
                return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE),
                    HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(
                    ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                    HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw new InsertErrorException();
        }
    }

    /**
     * Update request. *
     * 
     * @param requestRequestDto the request request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateRequest(UpdateRequestRequestDto requestRequestDto) throws Exception {
        LocalDateTime currentTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);

        int updated = requestRepository.updateRequest(Integer.parseInt(requestRequestDto.getRequestId()),
            requestRequestDto.getProjectCode(), requestRequestDto.getReason(), requestRequestDto.getUpdateAt(),
            currentTime);

        if (updated != 1) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                HttpStatus.NOT_FOUND);
        }

        List<Integer> listRequestDetailOld = listRequestRepository
            .selectListDetailIdByRequestId(requestRequestDto.getRequestId());
        List<Integer> listDelete = new ArrayList<>(listRequestDetailOld);
        List<Integer> listRequestDetail = new ArrayList<>();
        for (RequestDetailRequestDto requestDetail : requestRequestDto.getRequestDetail()) {
            // Check if RequestDetailId is empty(add new a request), it will add a new record
            // if RequestDetailId is not empty, it will update
            if (!StringUtils.equals(Contants.CONST_STR_BLANK, requestDetail.getRequestDetailId())) {
                listRequestDetail.add(Integer.valueOf(requestDetail.getRequestDetailId()));
            } else {
                Date dateRequestDetails = Utils.stringToDate(requestDetail.getDateOT(),
                    Contants.CONST_STR_PATTERN_YYYYMMDD);
                createRequestDetail(Integer.parseInt(requestRequestDto.getRequestId()), requestDetail.getEmployeeCode(),
                    Integer.valueOf(requestDetail.getPositionCode()), dateRequestDetails,
                    Integer.parseInt(requestDetail.getPlanTimeOT()), requestDetail.getNote(),
                    Contants.CONST_INT_DELETE_FLAG_FALSE, currentTime);
            }
        }

        // Get list detailId to delete
        listDelete.removeAll(listRequestDetail);
        for (Integer item : listDelete) {
            int detailDelete = requestDetailRepository.deleteRequestDetail(item, currentTime);
            if (detailDelete != 1) {
                return new ResponseEntity<>(
                    ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                    HttpStatus.NOT_FOUND);
            }
        }

        // Loop update request detail
        for (RequestDetailRequestDto requestDetail : requestRequestDto.getRequestDetail()) {

            // if RequestDetailId is not empty, it will update
            if (!StringUtils.equals(Contants.CONST_STR_BLANK, requestDetail.getRequestDetailId())) {

                // Get list detailId to update
                for (Integer item : listRequestDetail) {
                    if (StringUtils.equals(item.toString(), requestDetail.getRequestDetailId())) {
                        Date dateOt = Utils.stringToDate(requestDetail.getDateOT(),
                            Contants.CONST_STR_PATTERN_YYYYMMDD);
                        int detailUpdated = requestDetailRepository.updateRequestDetail(item, dateOt,
                            requestDetail.getEmployeeCode(), Integer.parseInt(requestDetail.getPlanTimeOT()),
                            requestDetail.getNote(), currentTime);

                        if (detailUpdated != 1) {
                            return new ResponseEntity<>(
                                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                                HttpStatus.NOT_FOUND);
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE), HttpStatus.OK);
    }

    /**
     * Gets the approval request.
     *
     * @param requestDto the request dto
     * @return the approval request
     * @throws Exception the exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getApprovalRequest(ApprovalRequestDto requestDto) throws Exception {
        LocalDateTime currentTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);
        Integer role = Utils.getRoleFromLogin();
        String employeeCode = Utils.getEmployeeCodeFromLogin();
        int updated = 0;
        // Role QA
        if (Contants.CONST_ROLE_QA.equals(role)) {

            // Verify
            if (requestDto.getStatus().equals("0")) {
                // Update approval
                updated = requestRepository.updateStatusByIdAndStatusRequired(
                    Integer.valueOf(requestDto.getRequestId()), RequestStatusEnum.PASS.getValue(),
                    RequestStatusEnum.VERIFY.getValue(), employeeCode, requestDto.getUpdateAt(), currentTime);

                // Check update status success
                if (updated != 1) {
                    return new ResponseEntity<>(
                        ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                        HttpStatus.NOT_FOUND);
                }

                // Loop update request detail
                for (ApprovalDetailRequestDto requestDetail : requestDto.getRequestDetail()) {
                    int detailUpdated = requestDetailRepository.updateApprovalTimeOTById(
                        Integer.valueOf(requestDetail.getRequestDetailId()),
                        Integer.parseInt(requestDetail.getApprovalTimeOT()), requestDetail.getNote(), currentTime);

                    if (detailUpdated != 1) {
                        return new ResponseEntity<>(
                            ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                            HttpStatus.NOT_FOUND);
                    }
                }

                // Send mail
                sendMailForRequestOTApproval(requestDto.getRequestId(), true);
            }

            // UnVerify
            if (requestDto.getStatus().equals("1")) {
                updated = requestRepository.updateStatusByIdAndStatusRequired(
                    Integer.valueOf(requestDto.getRequestId()), RequestStatusEnum.VERIFY.getValue(),
                    RequestStatusEnum.PASS.getValue(), employeeCode, requestDto.getUpdateAt(), currentTime);

                if (updated != 1) {
                    return new ResponseEntity<>(
                        ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                        HttpStatus.NOT_FOUND);
                }

                for (ApprovalDetailRequestDto requestDetail : requestDto.getRequestDetail()) {
                    int detailUpdated = requestDetailRepository.updateApprovalTimeOTById(
                        Integer.valueOf(requestDetail.getRequestDetailId()), 0, requestDetail.getNote(), currentTime);

                    if (detailUpdated != 1) {
                        return new ResponseEntity<>(
                            ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                            HttpStatus.NOT_FOUND);
                    }
                }
            }

        } else {

            if (requestDto.getStatus().equals("0")) {
                // Update approval
                updated = requestRepository.updateStatusById(Integer.valueOf(requestDto.getRequestId()),
                    RequestStatusEnum.APPROVAL.getValue(), employeeCode, requestDto.getUpdateAt(), currentTime);
                // Check update status success
                if (updated != 1) {
                    return new ResponseEntity<>(
                        ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                        HttpStatus.NOT_FOUND);
                }
                // Update approval time
                for (ApprovalDetailRequestDto requestDetail : requestDto.getRequestDetail()) {

                    Date dateOT = Utils.stringToDate(requestDetail.getDateOT(), Contants.CONST_STR_PATTERN_YYYYMMDD);
                    String dateOTStr = Utils.convertDateToString(dateOT, Contants.CONST_STR_PATTERN_YYYYMMDD);
                    LocalDateTime createTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);

                    Optional<OTTimeApprovalDto> otTimeApprovalDtoOptional = this.otTimeApprovalRepository
                        .selectByRequestId(dateOTStr, requestDetail.getEmployeeCode(), requestDto.getProjectCode(),
                            requestDetail.getPositionCode());

                    // Check exist request id exist
                    if (otTimeApprovalDtoOptional.isPresent()) {
                        int detailUpdated = this.otTimeApprovalRepository.updateStatusByCondition(
                            Integer.valueOf(requestDto.getRequestId()),
                            Integer.parseInt(requestDetail.getApprovalTimeOT()), requestDto.getProjectCode(),
                            requestDetail.getEmployeeCode(), dateOT, currentTime, requestDetail.getPositionCode());
                        if (detailUpdated != 1) {
                            return new ResponseEntity<>(
                                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                                HttpStatus.NOT_FOUND);
                        }
                    } else {
                        int insert = 0;
                        insert = this.otTimeApprovalRepository.insertOTTimeApproval(
                            Integer.valueOf(requestDto.getRequestId()), requestDetail.getEmployeeCode(),
                            requestDto.getProjectCode(), requestDetail.getPositionCode(), dateOT,
                            Integer.parseInt(requestDetail.getApprovalTimeOT()), createTime, createTime);
                        // Check if insert fail then return
                        if (insert != 1) {
                            return new ResponseEntity<>(
                                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                                HttpStatus.NOT_FOUND);
                        }
                    }
                }
                // Send mail
                sendMailForRequestOTApproval(requestDto.getRequestId(), false);
            }

            if (requestDto.getStatus().equals("1")) {
                // Update approval
                updated = requestRepository.updateStatusById(Integer.valueOf(requestDto.getRequestId()),
                    RequestStatusEnum.VERIFY.getValue(), employeeCode, requestDto.getUpdateAt(), currentTime);
                // Check update status success
                if (updated != 1) {
                    return new ResponseEntity<>(
                        ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                        HttpStatus.NOT_FOUND);
                }

                // Update approval time
                for (ApprovalDetailRequestDto requestDetail : requestDto.getRequestDetail()) {

                    Date dateOT = Utils.stringToDate(requestDetail.getDateOT(), Contants.CONST_STR_PATTERN_YYYYMMDD);
                    String dateOTStr = Utils.convertDateToString(dateOT, Contants.CONST_STR_PATTERN_YYYYMMDD);

                    Optional<OTTimeApprovalDto> otTimeApprovalDtoOptional = this.otTimeApprovalRepository
                        .selectByRequestId(dateOTStr, requestDetail.getEmployeeCode(), requestDto.getProjectCode(),
                            requestDetail.getPositionCode());

                    // Check exist request id exist
                    if (otTimeApprovalDtoOptional.isPresent()) {
                        int detailUpdated = this.otTimeApprovalRepository.updateUnApproval(
                            Integer.valueOf(requestDto.getRequestId()), requestDto.getProjectCode(),
                            requestDetail.getEmployeeCode(), dateOT, currentTime, requestDetail.getPositionCode());
                        if (detailUpdated != 1) {
                            return new ResponseEntity<>(
                                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                                HttpStatus.NOT_FOUND);
                        }
                        
                        int detailRequest = requestDetailRepository.updateApprovalTimeOTById(
                            Integer.valueOf(requestDetail.getRequestDetailId()), 0, requestDetail.getNote(), currentTime);

                        if (detailRequest != 1) {
                            return new ResponseEntity<>(
                                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                                HttpStatus.NOT_FOUND);
                        }
                        
                    } else {
                        return new ResponseEntity<>(
                            ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                            HttpStatus.NOT_FOUND);
                    }
                }
            }
        }
        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE), HttpStatus.OK);
    }

    /**
     * Cancel request.
     *
     * @param cancelRequestDto the cancel request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> cancelRequest(CancelRequestDto cancelRequestDto) throws Exception {
        LocalDateTime currentTime = Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME);
        String employeeCode = Utils.getEmployeeCodeFromLogin();
        // Update approval
        int updated = requestRepository.updateStatusById(Integer.valueOf(cancelRequestDto.getRequestId()),
            RequestStatusEnum.CANCEL.getValue(), employeeCode, cancelRequestDto.getUpdateAt(), currentTime);

        // Check update status success
        if (updated != 1) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE), HttpStatus.OK);
    }

    /**
     * Gets the projects.
     *
     * @return the projects
     * @throws Exception the exception
     */
    /*
     * (non-Javadoc)
     * 
     * @see jp.co.gzr_internal.api.service.RequestService#getProjects()
     */
    @Override
    public ResponseEntity<?> getProjects() throws Exception {
        try {
            List<ProjectListDto> projectNames = customProjectRepository
                .getListProjectByEmployeeCode(Contants.CONST_STR_BLANK);

            return new ResponseEntity<>(ResponseCommon.createResponse(projectNames, MessageContants.SUCCESS_CODE),
                HttpStatus.OK);
        } catch (Exception e) {
            throw new SelectErrorException();
        }
    }

    /**
     * Gets the list request.
     *
     * @param listRequestRequestDto the list request request dto
     * @return the list request
     * @throws Exception the exception
     */
    @Override
    public ResponseEntity<?> getListRequest(ListRequestRequestDto listRequestRequestDto) throws Exception {
        DataAndPageResponseDto response = new DataAndPageResponseDto();
        /* The total records want to get per page. */
        Integer limit = Integer.valueOf(listRequestRequestDto.getTotalRecords());

        /* The start position of records want to get in the list data. */
        Integer offset = (Integer.valueOf(listRequestRequestDto.getCurrentPage()) - 1) * limit;

        List<ListRequestDto> listRequestDto = listRequestRepository
            .findAllRequestWithSearchSetting(listRequestRequestDto, limit, offset);

        List<ListRequestResponseDto> listRequestResponseDto = new ArrayList<ListRequestResponseDto>();
        for (ListRequestDto requestDto : listRequestDto) {
            listRequestResponseDto.add(getEntity(requestDto));
        }

        response.setDataResult(listRequestResponseDto);
        int count = listRequestRepository.countRequestWithSearchSetting(listRequestRequestDto);
        /* Initialize page info for client to render pageable. */
        PageDto pageDto = new PageDto(count, listRequestRequestDto.getCurrentPage(),
            listRequestRequestDto.getTotalRecords());
        response.setPageDto(pageDto);
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    /**
     * Gets the detail request.
     *
     * @param requestId the request id
     * @return the detail request
     * @throws Exception the exception
     */
    @Override
    public ResponseEntity<?> getDetailRequest(DetailRequestDto detailRequestDto) throws Exception {
        // Check requestId is empty or null
        if (StringUtils.isEmpty(detailRequestDto.getRequestId())) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY),
                HttpStatus.NOT_FOUND);
        }
        try {
            Optional<RequestDto> requestDtoOptional = this.requestRepository
                .selectByRequestId(Integer.valueOf(detailRequestDto.getRequestId()));

            // Check exist requestId
            if (!requestDtoOptional.isPresent()) {
                return new ResponseEntity<>(
                    ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_DATA_NOT_FOUND),
                    HttpStatus.NOT_FOUND);
            }
            RequestDto requestDto = requestDtoOptional.get();
            List<RequestDetailDto> listRequestDetail = this.requestDetailRepository.selectDetailSearchByRequestId(
                Integer.valueOf(detailRequestDto.getRequestId()), detailRequestDto.getDateSearch());

            requestDto.setEmployeeName(Utils.upperCaseWords(requestDto.getEmployeeName()));
            List<EmployeeOfProjectDto> employeeList = customEmployeeRepository
                .getListEmployeeByProjectCode(requestDto.getProjectCode());

            List<EmployeeByPositionDto> employeeWithPosition = new ArrayList<>();

            employeeList.forEach(employee -> {

                EmployeeByPositionDto employeeByPositionDto = new EmployeeByPositionDto();
                List<PositionByEmployeeDto> positionByEmployeeDtos = new ArrayList<>();

                positionByEmployeeDtos = projectDetailRepository.getListPostionByEmployee(
                    Integer.valueOf(requestDto.getProjectCode()).intValue(), employee.getEmployeeCode());

                employeeByPositionDto.setEmployeeCode(employee.getEmployeeCode());
                employeeByPositionDto.setEmployeeName(employee.getEmployeeName());
                employeeByPositionDto.setPositionByEmployeeDtoList(positionByEmployeeDtos);

                employeeWithPosition.add(employeeByPositionDto);
            });

            RequestDetailResponseDto requestDetailResponseDto = responseRequestDetail(listRequestDetail, requestDto,
                employeeWithPosition);

            return new ResponseEntity<>(
                ResponseCommon.createResponse(requestDetailResponseDto, MessageContants.SUCCESS_CODE), HttpStatus.OK);
        } catch (Exception e) {
            throw new SelectErrorException();
        }
    }

    /**
     * Creates the request detail.
     *
     * @param requestId the request id
     * @param employeeCode the employee code
     * @param dateOT the date OT
     * @param timeOT the time OT
     * @param note the note
     * @param deleteFlag the delete flag
     * @param createTime the create time
     */
    private void createRequestDetail(Integer requestId, String employeeCode, int positionCode, Date dateOT,
        Integer timeOT, String note, Integer deleteFlag, LocalDateTime createTime) {
        this.requestDetailRepository.insertRequestDetail(requestId, employeeCode, positionCode, dateOT, timeOT, note,
            deleteFlag, createTime, createTime);
    }

    /**
     * Creates the request.
     *
     * @param userId the user id
     * @param projectName the project name
     * @param reason the reason
     * @param deleteFlag the delete flag
     * @param status the status
     * @param createTime the create time
     * @param updateTime the update time
     * @return the integer
     */
    private Integer createRequestTable(String userId, String projectCode, String reason, Integer deleteFlag,
        Integer status, LocalDateTime createTime, LocalDateTime updateTime) {
        Request requestOT = new Request();
        requestOT.setEmployeeCode(userId);
        requestOT.setProjectCode(projectCode);
        requestOT.setReason(reason);
        requestOT.setDeleteFlag(deleteFlag);
        requestOT.setStatus(status);
        requestOT.setCreateTime(createTime);
        requestOT.setUpdateTime(updateTime);
        requestRepository.save(requestOT);
        return requestOT.getId();
    }

    /**
     * Gets the entity.
     *
     * @param listRequestDto the list request dto
     * @return the entity
     */
    private static ListRequestResponseDto getEntity(ListRequestDto listRequestDto) {
        ListRequestResponseDto listRequestResponseDto = new ListRequestResponseDto();
        listRequestResponseDto.setProjectCode(listRequestDto.getProjectCode());
        listRequestResponseDto.setProjectName(listRequestDto.getProjectName());
        listRequestResponseDto.setEmployeeCode(listRequestDto.getEmployeeCode());
        listRequestResponseDto.setNote(listRequestDto.getNote());
        listRequestResponseDto.setRequestId(listRequestDto.getRequestId());
        listRequestResponseDto.setStatus(listRequestDto.getStatus());
        listRequestResponseDto.setTotalApproval(listRequestDto.getTotalApproval());
        listRequestResponseDto.setTotalRequest(listRequestDto.getTotalRequest());
        listRequestResponseDto.setEmployeeName(listRequestDto.getEmployeeName());
        listRequestResponseDto.setCreateAt(listRequestDto.getCreateAt());
        listRequestResponseDto.setUpdateAt(listRequestDto.getUpdaetAt());
        listRequestResponseDto.setDateOt(listRequestDto.getDateOt());
        listRequestResponseDto.setProjectManagement(listRequestDto.getProjectManagement());
        return listRequestResponseDto;
    }

    /**
     * Response request detail.
     *
     * @param listRequestDetailDto the list request detail dto
     * @param requestDto the request dto
     * @return the request detail respone dto
     */
    private static RequestDetailResponseDto responseRequestDetail(List<RequestDetailDto> listRequestDetailDto,
        RequestDto requestDto, List<EmployeeByPositionDto> employeeList) {
        RequestDetailResponseDto requestDetailResponseDto = new RequestDetailResponseDto();
        BeanUtils.copyProperties(requestDto, requestDetailResponseDto);
        requestDetailResponseDto.setRequestDetails(listRequestDetailDto);
        requestDetailResponseDto.setEmployeeList(employeeList);
        return requestDetailResponseDto;
    }

    /**
     * Send mail for request OT.
     *
     * @param projectName the project name
     * @param reason the reason
     * @param employeeCode the employee code
     * @throws Exception the exception
     */
    @Async
    private void sendMailForRequestOTStatusVerify(String projectName, String reason, String employeeCode)
        throws Exception {
        // Setting info mail
        MailInfoDto mailInfo = new MailInfoDto();
        mailInfo.setProjectName(projectName);
        String reasonNew = Contants.CONST_STR_BLANK;

        reasonNew = Encode.forHtmlContent(reason);
        reasonNew = reasonNew.replaceAll("(\r\n|\n)", "<br />");

        mailInfo.setReason(reasonNew);
        mailInfo.setRequestStatus(RequestStatusEnum.VERIFY.getName());
        // Get info employee create request OT
        EmployeeDto employeeOwner = employeeRepository.selectByEmployeeCode(employeeCode);
        if (employeeOwner == null) {
            throw new SelectDataNotExistErrorException();
        }
        mailInfo.setOwner(Utils.upperCaseWords(employeeOwner.getEmployeeName()));
        mailInfo.setToBcc(Arrays.asList(employeeOwner.getEmail()));

        // Get info employee role QA (4)
        Optional<Employee> employeeQA = employeeRepository.findByEmailAndDeleteFlag(mailConfiguration.getQa(), 0);

        if (!employeeQA.isPresent()) {
            throw new SelectDataNotExistErrorException();
        }

        List<String> toList = new ArrayList<String>();
        List<String> toNameList = new ArrayList<String>();

        toList.add(employeeQA.get().getEmail());
        toNameList.add(Utils.upperCaseWords(employeeQA.get().getEmployeeName()));

        mailInfo.setTo(toList);
        mailInfo.setToName(toNameList);

        // Get info employee role BM (1)
        Optional<Employee> employeeBM = employeeRepository.findByEmailAndDeleteFlag(mailConfiguration.getBm(), 0);
        if (!employeeBM.isPresent()) {
            throw new SelectDataNotExistErrorException();
        }

        List<String> toCcList = new ArrayList<String>();

        toCcList.add(employeeBM.get().getEmail());

        mailInfo.setToCc(toCcList);

        mailService.sendRequestOTMail(mailInfo);
    }

    /**
     * Send mail for request OT approval.
     *
     * @param requestId the request id
     * @param isStatusPass the is status pass
     * @throws Exception the exception
     */
    @Async
    private void sendMailForRequestOTApproval(String requestId, boolean isStatusPass) throws Exception {
        // Setting info mail
        MailInfoDto mailInfo = new MailInfoDto();

        // Get request overtime info
        Optional<RequestDto> requestOpt = requestRepository.selectByRequestId(Integer.valueOf(requestId));
        if (!requestOpt.isPresent()) {
            throw new SelectDataNotExistErrorException();
        }
        RequestDto requestDto = requestOpt.get();
        mailInfo.setProjectName(requestDto.getProjectName());
        mailInfo.setReason(requestDto.getReason());
        // Get info employee create request OT
        EmployeeDto employeePM = employeeRepository.selectByEmployeeCode(requestDto.getEmployeeCode());
        if (employeePM == null) {
            throw new SelectDataNotExistErrorException();
        }

        List<String> toCCList = new ArrayList<String>();

        // Get info mail to
        if (isStatusPass) {

            List<String> toList = new ArrayList<String>();
            List<String> toNameList = new ArrayList<String>();
            // Get info BM
            Optional<Employee> employeeBM = employeeRepository.findByEmailAndDeleteFlag(mailConfiguration.getBm(), 0);

            if (!employeeBM.isPresent()) {
                throw new SelectDataNotExistErrorException();
            } else {
                toList.add(employeeBM.get().getEmail());
                toNameList.add(Utils.upperCaseWords(employeeBM.get().getEmployeeName()));
            }

            mailInfo.setTo(toList);
            mailInfo.setToName(toNameList);
            mailInfo.setRequestStatus(RequestStatusEnum.PASS.getName());
            toCCList.add(employeePM.getEmail());
        } else {
            mailInfo.setTo(Arrays.asList(employeePM.getEmail()));
            mailInfo.setToName(Arrays.asList(Utils.upperCaseWords(employeePM.getEmployeeName())));
            mailInfo.setRequestStatus(RequestStatusEnum.APPROVAL.getName());
        }

        // Get info QA
        Optional<Employee> employeeQA = employeeRepository.findByEmailAndDeleteFlag(mailConfiguration.getQa(), 0);
        if (!employeeQA.isPresent()) {
            throw new SelectDataNotExistErrorException();
        }
        toCCList.add(employeeQA.get().getEmail());

        mailInfo.setOwner(Utils.upperCaseWords(employeeQA.get().getEmployeeName()));
        mailInfo.setToCc(toCCList);

        if (isStatusPass) {
            mailService.sendVerifyOTMail(mailInfo);
        } else {
            mailService.sendApprovalOTMail(mailInfo);
        }

    }

    /**
     * Gets the list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the list approval
     * @throws Exception the exception
     */
    @Override
    public ResponseEntity<?> getListApproval(ApprovalSearchRequestDto approvalSearchRequestDto) throws Exception {
        DataAndPageResponseDto response = new DataAndPageResponseDto();
        /* The total records want to get per page. */
        Integer limit = Integer.valueOf(approvalSearchRequestDto.getTotalRecords());

        /* The start position of records want to get in the list data. */
        Integer offset = (Integer.valueOf(approvalSearchRequestDto.getCurrentPage()) - 1) * limit;
        List<ListApprovalDto> listApproval = listApprovalRepository.findAllApproval(approvalSearchRequestDto, limit,
            offset, true);
        List<ListApprovalResponseDto> listApprovalResponseDto = new ArrayList<>();
        for (ListApprovalDto item : listApproval) {
            listApprovalResponseDto.add(setEntityApprovalList(item.getId(), item.getEmployeeCode(),
                item.getEmployeeName(), item.getPlanTimeOT(), item.getDateOT().toString(), item.getProjectName(),
                item.getPositionName(), item.getApproveTimeOT()));
        }
        response.setDataResult(listApprovalResponseDto);

        // Count all records from condition search
        int count = listApprovalRepository.countRequestWithSearchSetting(approvalSearchRequestDto);

        PageDto pageDto = new PageDto(count, approvalSearchRequestDto.getCurrentPage(),
            approvalSearchRequestDto.getTotalRecords());
        response.setPageDto(pageDto);
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    /**
     * Sets the entity approval list.
     *
     * @param id the id
     * @param employeeCode the employee code
     * @param employeeName the employee name
     * @param planTimeOT the plan time OT
     * @param dateOT the date OT
     * @param projectName the project name
     * @param positionName the position name
     * @param approvalTimeOT the approval time OT
     * @return the list approval response dto
     */
    private static ListApprovalResponseDto setEntityApprovalList(Integer id, String employeeCode, String employeeName,
        Integer planTimeOT, String dateOT, String projectName, String positionName, Integer approvalTimeOT) {
        ListApprovalResponseDto approvalResponseDto = new ListApprovalResponseDto();
        approvalResponseDto = new ListApprovalResponseDto();
        approvalResponseDto.setEmployeeCode(employeeCode);
        approvalResponseDto.setEmployeeName(employeeName);
        approvalResponseDto.setPlanTimeOT(planTimeOT);
        approvalResponseDto.setProjectName(projectName);
        approvalResponseDto.setPositionName(positionName);
        approvalResponseDto.setDateOT(dateOT);
        approvalResponseDto.setId(id);
        approvalResponseDto.setApprovalTimeOT(approvalTimeOT);
        return approvalResponseDto;
    }

    /**
     * Export list approval.
     *
     * @param approvalSearchRequestDto the approval search request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    public ResponseEntity<?> exportListApproval(ApprovalSearchRequestDto approvalSearchRequestDto) throws Exception {
        List<ListApprovalDto> listApproval = listApprovalRepository.findAllApproval(approvalSearchRequestDto, 0, 0,
            false);
        List<ListApprovalResponseDto> listApprovalResponseDto = new ArrayList<>();

        // Get year of condition
        int getYear = Integer.valueOf(approvalSearchRequestDto.getRequestDate().substring(0, 4));

        // Get month of condition
        int getMonth = Integer.valueOf(approvalSearchRequestDto.getRequestDate().substring(5, 7));
        ;

        int year = getYear;
        int month = getMonth - 1;
        Calendar calendar = new GregorianCalendar(year, month, 1);
        calendar.set(Calendar.YEAR, getYear);
        calendar.set(Calendar.MONTH, month, 1);

        // Get date of month of year
        int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        Map<String, String> mapWeekend = new HashMap<>();
        Calendar cal = new GregorianCalendar(year, month, 1);
        /*
         * Loop to get days sunday, saturday of week of month of year
         */
        do {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            /*
             * Check if day is weekend then put day of weekend into map
             **/
            if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                String weekend = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                mapWeekend.put(weekend, weekend);
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        } while (cal.get(Calendar.MONTH) == month);
        // Loop to set value from list approval to list approval response
        for (ListApprovalDto item : listApproval) {
            // Add into list
            listApprovalResponseDto.add(setEntityApprovalList(item.getId(), item.getEmployeeCode(),
                item.getEmployeeName(), item.getPlanTimeOT(), item.getDateOT().toString(), item.getProjectName(),
                item.getPositionName(), item.getApproveTimeOT()));
        }

        // Create variable list temp contain data
        List<ListApprovalResponseDto> listApprovalTemp = new ArrayList<>();

        // Create variable contain key is date and value is value of date
        List<Map<String, Integer>> listMapDateValue = new ArrayList<>();

        // Create date variable value of date
        Map<String, Integer> mapDateValue = new HashMap<>();

        // Create date variable value of total row
        Map<String, Integer> mapRowTotal = new HashMap<>();

        String keyTotalValue = "totalValueDate";
        String keyRowTotal = "rowTotal";
        String keyApprovedOT = "approvedOT";
        String keyTotalApprovedOT = "totalApprovedOT";
        for (ListApprovalResponseDto item : listApprovalResponseDto) {
            String keyDate = item.getDateOT().substring(8);
            /*
             * Check listMapDateValue exist data
             */
            if (listApprovalTemp.size() > 0) {
                mapDateValue = new HashMap<>();
                boolean isExist = false;
                int sumDateValue = 0;
                /*
                 * Loop to compare each data in list with item
                 */
                for (int i = 0; i < listApprovalTemp.size(); i++) {
                    if (listApprovalTemp.get(i).getEmployeeCode().equals(item.getEmployeeCode())
                        && listApprovalTemp.get(i).getProjectName().equals(item.getProjectName())
                        && listApprovalTemp.get(i).getPositionName().equals(item.getPositionName())) {

                        mapDateValue.put(keyDate, item.getPlanTimeOT());
                        // Check exist key 'totalValueDate'
                        if (listMapDateValue.get(i).containsKey(keyTotalValue)) {
                            Map<String, Integer> mapTemp = new HashMap<>();

                            sumDateValue = listMapDateValue.get(i).get(keyTotalValue) + item.getPlanTimeOT();
                            listMapDateValue.get(i).remove(keyTotalValue);
                            mapTemp = listMapDateValue.get(i);
                            mapTemp.put(keyDate, item.getPlanTimeOT());

                            // Put key is 'totalValueDate' and value into map mapDateValue
                            mapDateValue.put(keyTotalValue, sumDateValue);

                            // Put key is 'totalValueDate' to map Temp
                            mapTemp.put(keyTotalValue, sumDateValue);
                            listMapDateValue.set(i, mapTemp);
                        }

                        // Check exist key 'totalApprovedOT'
                        if (listMapDateValue.get(i).containsKey(keyApprovedOT)) {
                            Map<String, Integer> mapTemp = new HashMap<>();

                            sumDateValue = listMapDateValue.get(i).get(keyApprovedOT) + item.getApprovalTimeOT();
                            listMapDateValue.get(i).remove(keyApprovedOT);
                            mapTemp = listMapDateValue.get(i);

                            // Put key is 'totalValueDate' and value into map mapDateValue
                            mapDateValue.put(keyApprovedOT, sumDateValue);

                            // Put key is 'totalValueDate' to map Temp
                            mapTemp.put(keyApprovedOT, sumDateValue);
                            listMapDateValue.set(i, mapTemp);
                        }

                        // Total Row
                        // Check exist key then put key and sum of value approval ot, else put value approval ot
                        if (mapRowTotal.containsKey(keyDate)) {
                            int sumRowTotal = 0;
                            sumRowTotal = mapRowTotal.get(keyDate) + item.getPlanTimeOT();
                            mapRowTotal.put(keyDate, sumRowTotal);
                        } else {
                            mapRowTotal.put(keyDate, item.getPlanTimeOT());
                        }
                        // Set status is exist =true
                        isExist = true;
                    }
                }
                if (!isExist) {
                    // Put value in map if key is not exist
                    mapDateValue.put(keyDate, item.getPlanTimeOT());

                    // Put key 'totalValueDate'
                    mapDateValue.put(keyTotalValue, item.getPlanTimeOT());

                    // Put key 'totalApprovedOT'
                    mapDateValue.put(keyApprovedOT, item.getApprovalTimeOT());

                    listMapDateValue.add(mapDateValue);

                    // Total Row
                    // If key is exist then sum value
                    if (mapRowTotal.containsKey(keyDate)) {
                        int sumRowTotal = 0;
                        sumRowTotal = mapRowTotal.get(keyDate) + item.getPlanTimeOT();
                        mapRowTotal.put(keyDate, sumRowTotal);
                    } else {
                        mapRowTotal.put(keyDate, item.getPlanTimeOT());
                    }
                    // add item into list
                    listApprovalTemp.add(item);
                }
            } else {

                // Value date
                mapDateValue.put(keyDate, item.getPlanTimeOT());

                // Put key 'totalValueDate'
                mapDateValue.put(keyTotalValue, item.getPlanTimeOT());

                // Put key 'totalApprovedOT'
                mapDateValue.put(keyApprovedOT, item.getApprovalTimeOT());

                listMapDateValue.add(mapDateValue);

                // Row total
                mapRowTotal.put(keyDate, item.getPlanTimeOT());
                listApprovalTemp.add(item);
            }
        }
        int sum = 0;

        // Get sum value of row total
        for (String key : mapRowTotal.keySet()) {
            sum += mapRowTotal.get(key);
        }

        // Sum apptoval OT
        int sumApproval = 0;
        for (int i = 0; i < listMapDateValue.size(); i++) {
            sumApproval += listMapDateValue.get(i).get(keyApprovedOT);
        }

        // Add key and value into map
        mapRowTotal.put(keyRowTotal, sum);
        mapRowTotal.put(keyTotalApprovedOT, sumApproval);
        List<String> listValue = new ArrayList<>();
        listValue = getListCellOfRowHearder(numDays);
        return exportFileService.exportListApproval(keyTotalApprovedOT, keyApprovedOT, keyTotalValue, keyRowTotal,
            mapWeekend, mapRowTotal, listMapDateValue, listValue, listApprovalTemp, numDays,
            approvalSearchRequestDto.getRequestDate());
    }

    /**
     * Gets the list cell of row hearder.
     *
     * @param numDays the num days
     * @return the list cell of row hearder
     */
    private static List<String> getListCellOfRowHearder(Integer numDays) {
        // Set value for cell header
        List<String> listValue = new ArrayList<>();
        // add cell name header into table
        listValue.add("No");
        listValue.add("Mã Nhân Viên");
        listValue.add("Họ và tên");
        listValue.add("Vị trí");
        listValue.add("Tên dự án");
        // Loop to add numbers day into list
        for (int i = 1; i <= numDays; i++) {
            listValue.add(String.valueOf(i));
        }
        listValue.add("Total Plan OT");
        listValue.add("Total Approved OT");
        return listValue;
    }

    @Override
    public ResponseEntity<?> getProjectByEmployee() throws Exception {
        List<ProjectListDto> projectList = new ArrayList<>();
        String employeeCode = Utils.getEmployeeCodeFromLogin();
        if (!StringUtils.equals(employeeCode, Contants.CONST_STR_BLANK)) {
            projectList = customProjectRepository.getListProjectByEmployeeCode(employeeCode);
        } else {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_AUTHENTICATION_FAILURE),
                HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(ResponseCommon.createResponse(projectList, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListEmployeeByProjectCode(String projectCode) throws Exception {
        List<EmployeeOfProjectDto> employeeList = new ArrayList<>();
        List<EmployeeByPositionDto> employeeWithPosition = new ArrayList<>();

        if (projectCode == null) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_AUTHENTICATION_FAILURE),
                HttpStatus.NOT_FOUND);
        } else {
            employeeList = customEmployeeRepository.getListEmployeeByProjectCode(projectCode);

            employeeList.forEach(employee -> {

                EmployeeByPositionDto employeeByPositionDto = new EmployeeByPositionDto();
                List<PositionByEmployeeDto> positionByEmployeeDtos = new ArrayList<>();

                positionByEmployeeDtos = projectDetailRepository
                    .getListPostionByEmployee(Integer.valueOf(projectCode).intValue(), employee.getEmployeeCode());

                employeeByPositionDto.setEmployeeCode(employee.getEmployeeCode());
                employeeByPositionDto.setEmployeeName(employee.getEmployeeName());
                employeeByPositionDto.setPositionByEmployeeDtoList(positionByEmployeeDtos);

                employeeWithPosition.add(employeeByPositionDto);
            });

        }
        return new ResponseEntity<>(ResponseCommon.createResponse(employeeWithPosition, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPositionByEmployee(String projectCode, String employeeCode) throws Exception {
        List<PositionByEmployeeDto> positionByEmployeeDtos = new ArrayList<>();
        positionByEmployeeDtos = projectDetailRepository
            .getListPostionByEmployee(Integer.valueOf(projectCode).intValue(), employeeCode);
        return new ResponseEntity<>(ResponseCommon.createResponse(positionByEmployeeDtos, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }
}
