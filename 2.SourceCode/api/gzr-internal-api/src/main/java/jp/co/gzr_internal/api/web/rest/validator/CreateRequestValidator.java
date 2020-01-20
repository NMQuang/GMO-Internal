/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.repository.EmployeeRepository;
import jp.co.gzr_internal.api.service.dto.request.CreateRequestRequestDto;
import jp.co.gzr_internal.api.service.dto.request.RequestDetailRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class ListCourseValidator.
 */
@Component
public class CreateRequestValidator implements Validator {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    /**
     * Supports.
     *
     * @param clazz the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return CreateRequestRequestDto.class.equals(clazz);
    }

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        CreateRequestRequestDto requestOTRequestDto = (CreateRequestRequestDto) target;
        List<RequestDetailRequestDto> listRequestDetail = requestOTRequestDto.getRequestDetail();
        String employeeCode = "";

        // Check empty list detail request
        if (listRequestDetail.isEmpty()) {
            errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_LIST_EMPTY, new Object[]{}, null);
        }
        int sizeRequestDetail = listRequestDetail.size();
        // Loop to check valid detail request
        for (int i = 0; i < sizeRequestDetail; i++) {
            employeeCode = listRequestDetail.get(i).getEmployeeCode();
            String dateOT = listRequestDetail.get(i).getDateOT();
            String timeOT = listRequestDetail.get(i).getPlanTimeOT();
            String positionCode = listRequestDetail.get(i).getPositionCode();

            // Check null, blank employeeCode
            if (StringUtils.isEmpty(employeeCode)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[]{i + 1}, null);
                return;
            } else {
                Optional<Employee> optional = employeeRepository.findByEmployeeCodeAndDeleteFlag(employeeCode, 0);
                if(!optional.isPresent()) {
                    errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_EMPLOYEE_NOT_EXIST,
                        new Object[]{}, null);
                    return;
                }
            }

            // Check null, blank position code
            if(StringUtils.isEmpty(positionCode)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[]{i + 1}, null);
                return;
            }
            
            // Check null, blank dateOT
            if (StringUtils.isEmpty(dateOT)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[]{}, null);
                return;
            } else {

                String pattern = Contants.CONST_STR_PATTERN_YYYYMMDD;
                // Check format date
                if (!dateOT.matches(ValidateCommon.DATE_REGEX)) {
                    errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                        new Object[]{i + 1}, null);
                    return;
                }
                // Check date request must higher current date
                if (!Utils.compareCurrentDate(dateOT, pattern)) {
                    errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                        new Object[]{i + 1}, null);
                    return;
                }
            }

            // Check null, blank timeOT
            if (StringUtils.isEmpty(timeOT)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[]{i + 1}, null);
            }
        }
    }
}
