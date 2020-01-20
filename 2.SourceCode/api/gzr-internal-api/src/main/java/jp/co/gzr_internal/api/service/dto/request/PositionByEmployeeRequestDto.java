package jp.co.gzr_internal.api.service.dto.request;

import javax.validation.constraints.NotNull;

import jp.co.gzr_internal.api.web.rest.validator.annotation.number.Number;

public class PositionByEmployeeRequestDto {

    @NotNull
    @Number
    private String projectCode;
    
    @NotNull
    @Number
    private String employeeCode;

    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
