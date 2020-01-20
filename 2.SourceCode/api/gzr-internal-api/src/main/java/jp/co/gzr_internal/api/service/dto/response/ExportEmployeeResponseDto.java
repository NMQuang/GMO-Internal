package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.service.dto.EmployeeDto;

public class ExportEmployeeResponseDto {

    private List<EmployeeDto> employeeList;

    public List<EmployeeDto> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDto> employeeList) {
        this.employeeList = employeeList;
    }
    
    
}
