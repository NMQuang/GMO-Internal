package jp.co.gzr_internal.api.repository;

import java.math.BigInteger;
import java.util.List;

import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.EmployeeOfProjectDto;
import jp.co.gzr_internal.api.service.dto.request.DetailEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchEmployeeRequestDto;

public interface CustomEmployeeRepository {

    BigInteger countEmployeeByCode(String employeeCode);
    
    List<EmployeeDto> getListEmployee(SearchEmployeeRequestDto requestDto);
    
    BigInteger countEmployee(SearchEmployeeRequestDto requestDto);
    
    List<EmployeeDto> getDetailEmployee(DetailEmployeeRequestDto employeeRequest);
    
    List<EmployeeOfProjectDto> getListEmployeeByProjectCode(String projectCode);
    
}
