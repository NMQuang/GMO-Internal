/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.web.rest.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.gzr_internal.api.service.dto.request.ApprovalDetailRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * The Class ListCourseValidator.
 */
@Component
public class ApprovalRequestValidator implements Validator
{
    /**
     * Supports.
     *
     * @param clazz the clazz
     * @return true, if successful
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return ApprovalRequestDto.class.equals(clazz);
    }

    /**
     * Validate.
     *
     * @param target the target
     * @param errors the errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        Integer role = Utils.getRoleFromLogin();
        if (Contants.CONST_ROLE_ADMIN.equals(role)) {
            return;
        }
        
        ApprovalRequestDto approvalRequestDto = (ApprovalRequestDto) target;
        List<ApprovalDetailRequestDto> detailList = approvalRequestDto.getRequestDetail();

        // Check empty list detail request
        if (detailList.isEmpty()) {
            errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_LIST_EMPTY,
                new Object[] {}, null);
            return;
        }
        int approvalDetailSize = detailList.size();
        // Loop to check valid detail approval
        for (int i = 0; i < approvalDetailSize; i++) {
            String timeOT = detailList.get(i).getApprovalTimeOT();
            String requestDetailId =  detailList.get(i).getRequestDetailId();
            
            // Check null, blank request detail id
            if (StringUtils.isEmpty(requestDetailId)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[] {
                        i+1
                    }, null);
                return;
            }

            // Check null, blank timeOT
            if (StringUtils.isEmpty(timeOT)) {
                errors.rejectValue("requestDetail", MessageContants.CONST_ERROR_CODE_ELEMENT_REQUEST_DETAIL,
                    new Object[] {
                        i+1
                    }, null);
            }
        }
    }
}
