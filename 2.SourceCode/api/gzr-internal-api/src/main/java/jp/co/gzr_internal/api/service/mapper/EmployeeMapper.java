package jp.co.gzr_internal.api.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.request.ImportEmployeeRequestDto;

@Mapper
public interface EmployeeMapper {

    Employee convertDtoToEntity(EmployeeDto employeeDto);
    
    List<Employee> convertListDtoToListEntity(List<ImportEmployeeRequestDto> employeeDtoList);
}
