package jp.co.gzr_internal.api.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.domain.MDivision;
import jp.co.gzr_internal.api.domain.MPosition;
import jp.co.gzr_internal.api.domain.MStatus;
import jp.co.gzr_internal.api.domain.MTypeContact;
import jp.co.gzr_internal.api.repository.CustomEmployeeRepository;
import jp.co.gzr_internal.api.repository.EmployeeRepository;
import jp.co.gzr_internal.api.repository.MDivisionRepository;
import jp.co.gzr_internal.api.repository.MPositionRepository;
import jp.co.gzr_internal.api.repository.MStatusRepository;
import jp.co.gzr_internal.api.repository.MTypeContractRepository;
import jp.co.gzr_internal.api.service.EmployeeService;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.PageDto;
import jp.co.gzr_internal.api.service.dto.request.DetailEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ImportEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.response.DataAndPageResponseDto;
import jp.co.gzr_internal.api.service.dto.response.DetailEmployeeResponseDto;
import jp.co.gzr_internal.api.service.dto.response.EmployeeListResponseDto;
import jp.co.gzr_internal.api.service.dto.response.ExportEmployeeResponseDto;
import jp.co.gzr_internal.api.service.dto.response.SearchEmployeeResponseDto;
import jp.co.gzr_internal.api.service.mapper.EmployeeMapper;
import jp.co.gzr_internal.api.web.rest.commons.AbstractReadFile;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.commons.enums.DivisionEnum;
import jp.co.gzr_internal.api.web.rest.commons.enums.PositionEnum;
import jp.co.gzr_internal.api.web.rest.commons.enums.StatusEnum;
import jp.co.gzr_internal.api.web.rest.commons.enums.TypeContactEnum;
import jp.co.gzr_internal.api.web.rest.errors.SelectErrorException;
import jp.co.gzr_internal.api.web.rest.util.ApiResponseUtil;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * The Class EmployeeServiceImpl.
 */
@Service
public class EmployeeServiceImpl extends AbstractReadFile<ImportEmployeeRequestDto> implements EmployeeService {

    /** The employee repository. */
    private final EmployeeRepository employeeRepository;

    private final CustomEmployeeRepository customEmployeeRepository;

    private final MDivisionRepository mDivisionRepository;

    private final MPositionRepository mPositionRepository;

    private final MStatusRepository mStatusRepository;

    private final MTypeContractRepository mTypeContractRepository;

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CustomEmployeeRepository customEmployeeRepository,
        MDivisionRepository mDivisionRepository, MPositionRepository mPositionRepository,
        MStatusRepository mStatusRepository, MTypeContractRepository mTypeContractRepository) {
        super();
        this.employeeRepository = employeeRepository;
        this.customEmployeeRepository = customEmployeeRepository;
        this.mDivisionRepository = mDivisionRepository;
        this.mPositionRepository = mPositionRepository;
        this.mStatusRepository = mStatusRepository;
        this.mTypeContractRepository = mTypeContractRepository;
    }

    /**
     * Instantiates a new employee service impl.
     *
     * @param employeeRepository the employee repository
     */

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getListEmployee() throws Exception {

        try {
            Integer status = 1;
            Integer deleteFlag = 0;
            List<Employee> employeeList = employeeRepository.findByStatusAndDeleteFlag(status, deleteFlag);

            List<EmployeeListResponseDto> response = new ArrayList<EmployeeListResponseDto>();
            employeeList.forEach(employee -> {
                EmployeeListResponseDto employeeResponseDto = new EmployeeListResponseDto();
                employeeResponseDto.setEmployeeCode(employee.getEmployeeCode());
                employeeResponseDto.setEmployeeName(Utils.upperCaseWords(employee.getEmployeeName()));
                employeeResponseDto.setPosition(employee.getPositionId().toString());
                employeeResponseDto.setDivision(employee.getDivisionId().toString());
                response.add(employeeResponseDto);
            });
            return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
                HttpStatus.OK);
        } catch (Exception e) {
            throw new SelectErrorException();
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchEmployee(SearchEmployeeRequestDto requestDto) throws Exception {

        // Initial variable
        DataAndPageResponseDto response = new DataAndPageResponseDto();
        SearchEmployeeResponseDto employeeResponse = new SearchEmployeeResponseDto();
        List<EmployeeDto> employeeList = new ArrayList<>();
        List<MDivision> divisionList = new ArrayList<>();
        List<MPosition> positionList = new ArrayList<>();
        List<MStatus> statusList = new ArrayList<>();
        List<MTypeContact> typeContactList = new ArrayList<>();

        employeeList = customEmployeeRepository.getListEmployee(requestDto);
        divisionList = mDivisionRepository.findAll();
        positionList = mPositionRepository.findAll();
        statusList = mStatusRepository.findAll();
        typeContactList = mTypeContractRepository.findAll();

        employeeResponse.setDivisionList(divisionList);
        employeeResponse.setEmployeeList(employeeList);
        employeeResponse.setPositionList(positionList);
        employeeResponse.setStatusList(statusList);
        employeeResponse.setTypeContactList(typeContactList);

        BigInteger totalRecord = (BigInteger) customEmployeeRepository.countEmployee(requestDto);
        PageDto pageDto = new PageDto(totalRecord.intValue(), requestDto.getPage(), requestDto.getTotalRecordOfPage());
        // Set data and page to response
        response.setDataResult(employeeResponse);
        response.setPageDto(pageDto);
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> exportEmployee(SearchEmployeeRequestDto requestDto) throws Exception {

        // Initial variable
        ExportEmployeeResponseDto response = new ExportEmployeeResponseDto();
        // Get data following condition search
        List<EmployeeDto> result = customEmployeeRepository.getListEmployee(requestDto);
        
        response.setEmployeeList(result);
        
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> importEmployee(MultipartFile file) throws Exception {
        ResponseEntity<?> response = null;
        List<ImportEmployeeRequestDto> dataList = readFile(file);

        // Check value of file excel
        if (dataList.isEmpty()) {

            return new ResponseEntity<>(ResponseCommon.createResponseError(
                Utils.getMessageWithCode(MessageContants.CONST_ERROR_CODE_FILE_EMPTY)), HttpStatus.BAD_REQUEST);
        }

        // Set data insert Employee
        List<Employee> employeeList = employeeMapper.convertListDtoToListEntity(dataList);

        for (Employee employee : employeeList) {
            if (employee != null) {
                response = checkValidateEmployee(employee);
                if (response != null) {
                    return response;
                }
                employee.setCreateTime(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
                employee.setDeleteFlag(0);
                employee.setUpdateTime(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
                employeeRepository.saveAndFlush(employee);

            }
        }
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    private ResponseEntity<?> checkValidateEmployee(Employee employee) throws Exception {
        ResponseEntity<?> response = null;
        boolean result = true;
        List<ApiResponseUtil> error = new ArrayList<>();

        // Check validate employeeCode
        if (employee.getEmployeeCode() == null) {
            error.add(ResponseCommon.createResponseError("employee code", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        } else {
            BigInteger count = customEmployeeRepository.countEmployeeByCode(employee.getEmployeeCode());
            if (count.intValue() > 0) {
                error
                    .add(ResponseCommon.createResponseError("employee code", MessageContants.CONST_VALIDATE_DUPLICATE));
                result = false;
            }
        }

        // Check validate employeName
        if (employee.getEmployeeName() == null) {
            error.add(ResponseCommon.createResponseError("employee name", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check valiodate email
        if (employee.getEmail() == null) {
            error.add(ResponseCommon.createResponseError("email", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate probation day
        if (employee.getProbationaryDay() == null) {
            error.add(ResponseCommon.createResponseError("probation day", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate official day
        if (employee.getOfficialDay() == null) {
            error.add(ResponseCommon.createResponseError("official day", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate work condition
        if (employee.getWorkLocation() == null) {
            error.add(ResponseCommon.createResponseError("work condition", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate status
        if (employee.getStatus() == null) {
            error.add(ResponseCommon.createResponseError("status", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate division
        if (employee.getDivisionId() == null) {
            error.add(ResponseCommon.createResponseError("division", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        // Check validate position
        if (employee.getPositionId() == null) {
            error.add(ResponseCommon.createResponseError("position", MessageContants.CONST_VALIDATE_NOT_NULL));
            result = false;
        }

        if (!result) {
            // Create map of response
            Map<String, Object> resMap = new HashMap<>();
            resMap.put(Contants.CONST_STR_RESPONSE, error);
            response = new ResponseEntity<>(resMap, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    protected List<ImportEmployeeRequestDto> getData(Workbook workbook) throws Exception {
        List<ImportEmployeeRequestDto> result = new ArrayList<>();
        List<String> header = new ArrayList<>();
        ImportEmployeeRequestDto employeeDto = null;

        if (workbook != null) {

            Sheet sheet = workbook.getSheetAt(0);
            int numberOfRow = sheet.getPhysicalNumberOfRows();
            int numberCurrent = 0;
            for (int i = 0; i < numberOfRow; i++) {

                Row row = sheet.getRow(i);
                int numberOfCell = row.getPhysicalNumberOfCells();
                employeeDto = new ImportEmployeeRequestDto();

                for (int j = 0; j < numberOfCell; j++) {
                    Cell cell = row.getCell(j);
                    if (i == 0) {
                        numberCurrent = numberOfCell;
                        if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                            header.add(Utils.getValueOfFile(cell));
                        } else {
                            return new ArrayList<>();
                        }
                    } else {

                        if (numberCurrent == numberOfCell) {
                            setDatForObject(header.get(j), cell, employeeDto);
                        } else {
                            return new ArrayList<>();
                        }
                    }
                }
                if (i > 0) {
                    result.add(employeeDto);
                }
            }
        }

        return result;
    }

    private void setDatForObject(String header, Cell cell, ImportEmployeeRequestDto employeeDto) {
        switch (header) {
        case "Mã nhân viên (*)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setEmployeeCode(Utils.getValueOfFile(cell));
                break;
            }
        case "Họ và tên (*)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setEmployeeName(Utils.getValueOfFile(cell));
                break;
            }
        case "Giới tính":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                if (StringUtils.equals("Nam", Utils.getValueOfFile(cell))) {
                    employeeDto.setGender(1);
                } else if (StringUtils.equals("Nữ", Utils.getValueOfFile(cell))) {
                    employeeDto.setGender(0);
                }
                break;
            }
        case "Ngày sinh":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setBirthday(Utils.getValueOfFile(cell));
                break;
            }
        case "Chỗ ở hiện nay":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setAddress(Utils.getValueOfFile(cell));
                break;
            }
        case "ĐT di động":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setPhoneNumber(Utils.getValueOfFile(cell));
                break;
            }
        case "Email cơ quan":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setEmail(Utils.getValueOfFile(cell));
                break;
            }
        case "Nơi sinh":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setBirthplace(Utils.getValueOfFile(cell));
                break;
            }
        case "Tỉnh/Thành phố (hộ khẩu thường trú)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setProvincePr(Utils.getValueOfFile(cell));
                break;
            }
        case "Quận/Huyện (Hộ khẩu thường trú)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setDistrictPr(Utils.getValueOfFile(cell));
                break;
            }
        case "Xã/Phường (Hộ khẩu thường trú)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setWardPr(Utils.getValueOfFile(cell));
                break;
            }
        case "Quốc gia (chỗ ở hiện nay)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setNationCa(Utils.getValueOfFile(cell));
                break;
            }
        case "Quận/Huyện (Chỗ ở hiện nay)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setDistrictCa(Utils.getValueOfFile(cell));
                break;
            }
        case "Xã/Phường (Chỗ ở hiện nay)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setWardCa(Utils.getValueOfFile(cell));
                break;
            }
        case "Ngày thử việc":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setProbationaryDay(Utils.getValueOfFile(cell));
                break;
            }
        case "Ngày chính thức":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setOfficialDay(Utils.getValueOfFile(cell));
                break;
            }
        case "Địa điểm làm việc":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setWorkLocation(Utils.getValueOfFile(cell));
                break;
            }
        case "Loại hợp đồng":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setTypeContract(TypeContactEnum.get(Utils.getValueOfFile(cell)).getValue());
                break;
            }
        case "Trạng thái lao động (*)":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setStatus(StatusEnum.get(Utils.getValueOfFile(cell)).getValue());
                break;
            }
        case "Lý do nghỉ":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setReasonLeave(Utils.getValueOfFile(cell));
                break;
            }
        case "Ngày nghỉ việc":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto
                    .setDayOff(Utils.stringToDate(Utils.getValueOfFile(cell), Contants.CONST_STR_PATTERN_YYYYMMDD));
                break;
            }
        case "Tên vị trí công việc":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setPositionId(PositionEnum.get(Utils.getValueOfFile(cell)).getValue());
                break;
            }
        case "Tên đơn vị công tác":
            if (cell != null && !Utils.getValueOfFile(cell).trim().isEmpty()) {
                employeeDto.setDivisionId(DivisionEnum.get(Utils.getValueOfFile(cell)).getValue());
                break;
            }
        }
    }

    @Override
    public ResponseEntity<?> getDetailEmployee(DetailEmployeeRequestDto employeeRequest) throws Exception {
        // Check employeeCode is empty or null
        if (StringUtils.isEmpty(employeeRequest.getEmployeeCode())) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY),
                HttpStatus.NOT_FOUND);
        }
        DetailEmployeeResponseDto employeeResponse = new DetailEmployeeResponseDto();
        List<MDivision> divisionList = new ArrayList<>();
        List<MPosition> positionList = new ArrayList<>();
        List<MStatus> statusList = new ArrayList<>();
        List<MTypeContact> typeContactList = new ArrayList<>();
        List<EmployeeDto> employee = new ArrayList<>();

        employee = customEmployeeRepository.getDetailEmployee(employeeRequest);
        if (employee.size() <= 0) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY),
                HttpStatus.NOT_FOUND);
        }
        divisionList = mDivisionRepository.findAll();
        positionList = mPositionRepository.findAll();
        statusList = mStatusRepository.findAll();
        typeContactList = mTypeContractRepository.findAll();
        

        employeeResponse.setEmployee(employee);
        employeeResponse.setDivisionList(divisionList);
        employeeResponse.setPositionList(positionList);
        employeeResponse.setStatusList(statusList);
        employeeResponse.setTypeContactList(typeContactList);
        return new ResponseEntity<>(ResponseCommon.createResponse(employeeResponse, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateEmployee(UpdateEmployeeRequestDto employeeRequest) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
