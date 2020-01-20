/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.gzr_internal.api.repository.RequestDetailRepository;
import jp.co.gzr_internal.api.service.dto.RequestDetailDto;
import jp.co.gzr_internal.api.service.dto.request.RequestDetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateRequestRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class UpdateRequestValidator.
 */
@Component
public class UpdateRequestValidator implements Validator {

    @Autowired
    private RequestDetailRepository requestDetailRepository;

    /**
     * Supports.
     *
     * @param clazz the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateRequestRequestDto.class.equals(clazz);
    }

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        UpdateRequestRequestDto requestOTRequestDto = (UpdateRequestRequestDto) target;
        List<RequestDetailRequestDto> listRequestDetail = requestOTRequestDto.getRequestDetail();

        // Check empty list detail request
        if (listRequestDetail.isEmpty()) {
            errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_LIST_EMPTY, new Object[]{}, null);
            return;
        }

        List<RequestDetailDto> listRequestDetailOld = requestDetailRepository
            .selectDetailByRequestId(Integer.valueOf(requestOTRequestDto.getRequestId()));

        int sizeRequestDetail = listRequestDetail.size();
        // Loop to check valid detail request
        for (int i = 0; i < sizeRequestDetail; i++) {
            String employeeCode = listRequestDetail.get(i).getEmployeeCode();
            String dateOT = listRequestDetail.get(i).getDateOT();
            String timeOT = listRequestDetail.get(i).getPlanTimeOT();
            String requestDetailId = listRequestDetail.get(i).getRequestDetailId();
            String positionCode = listRequestDetail.get(i).getPositionCode();

            // Check if RequestDetailId is empty(add new a request), it will add a new record
            // if RequestDetailId is not empty, it will update
            if (!StringUtils.equals(Contants.CONST_STR_BLANK, requestDetailId)) {

            }
            // Check null, blank employeeCode
            if (StringUtils.isEmpty(employeeCode)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[]{i + 1}, null);
                return;
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
                    new Object[]{i + 1}, null);
                return;
            } else {

                String pattern = Contants.CONST_STR_PATTERN_YYYYMMDD;
                // Check format date
                if (!dateOT.matches(ValidateCommon.DATE_REGEX)) {
                    errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                        new Object[]{i + 1}, null);
                    return;
                }
                // Check if RequestDetailId is empty(add new a request), it will add a new record
                // if RequestDetailId is not empty, it will update
                if (!StringUtils.equals(Contants.CONST_STR_BLANK, requestDetailId)) {
                    // Check date request must higher current date
                    if (!listRequestDetailOld.get(i).getDateOT().equals(dateOT)
                        && !Utils.compareCurrentDate(dateOT, pattern)) {
                        errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                            new Object[]{i + 1}, null);
                        return;
                    }
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
