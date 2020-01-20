package jp.co.gzr_internal.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jp.co.gzr_internal.api.service.dto.request.DetailEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateEmployeeRequestDto;

/**
 * The Interface EmployeeService.
 */
@Service
public interface EmployeeService {

    /**
     * Gets the list employee.
     *
     * @return the list employee
     * @throws Exception the exception
     */
    ResponseEntity<?> getListEmployee() throws Exception;

    ResponseEntity<?> importEmployee(MultipartFile file) throws Exception;

    ResponseEntity<?> searchEmployee(SearchEmployeeRequestDto requestDto) throws Exception;
    
    ResponseEntity<?> exportEmployee(SearchEmployeeRequestDto requestDto) throws Exception;
    
    ResponseEntity<?> getDetailEmployee(DetailEmployeeRequestDto employeeRequest) throws Exception;
    
    ResponseEntity<?> updateEmployee(UpdateEmployeeRequestDto employeeRequest) throws Exception;
    
}
