package jp.co.gzr_internal.api.service.dto.request;

import javax.validation.constraints.NotNull;

import jp.co.gzr_internal.api.web.rest.validator.annotation.number.Number;

public class DetailEmployeeRequestDto {

    @NotNull
    @Number
    private String employeeCode;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    
    
}
