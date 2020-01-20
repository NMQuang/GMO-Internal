package jp.co.gzr_internal.api.service.dto;

import java.util.List;

public class EmployeeByPositionDto {

    private String employeeCode;

    private String employeeName;

    private List<PositionByEmployeeDto> positionByEmployeeDtoList;
    

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<PositionByEmployeeDto> getPositionByEmployeeDtoList() {
        return positionByEmployeeDtoList;
    }

    public void setPositionByEmployeeDtoList(List<PositionByEmployeeDto> positionByEmployeeDtoList) {
        this.positionByEmployeeDtoList = positionByEmployeeDtoList;
    }

}
