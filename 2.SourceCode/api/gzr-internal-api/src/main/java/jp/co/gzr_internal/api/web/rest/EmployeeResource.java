/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.web.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jp.co.gzr_internal.api.service.EmployeeService;
import jp.co.gzr_internal.api.service.dto.request.DetailEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateEmployeeRequestDto;

/**
 * The Class RequestResource.
 */
@RestController
@RequestMapping("api")
public class EmployeeResource
{
    /** The employee service. */
    private final EmployeeService employeeService;
    
    /**
     * Instantiates a new employee resource.
     *
     * @param employeeService the employee service
     */
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Gets the list.
     *
     * @return the list
     * @throws Exception the exception
     */
    @PostMapping(path = "/employees/list")
    public ResponseEntity<?> executeGetListEmployee()
        throws Exception {
        return employeeService.getListEmployee();
    }
    
    @PostMapping("/employees/import")
    public ResponseEntity<?> executeImportEmployee(@RequestParam("file") MultipartFile file) throws Exception {

        return employeeService.importEmployee(file);
    }
    
    @PostMapping(path = "/employees/search")
    public ResponseEntity<?> executeSearchEmployee(@Valid @RequestBody SearchEmployeeRequestDto requestDto) throws Exception {
        return employeeService.searchEmployee(requestDto);
    }
    
    @PostMapping(path = "/employees/export")
    public ResponseEntity<?> executeExportEmployee(@Valid @RequestBody SearchEmployeeRequestDto requestDto) throws Exception {
        return employeeService.exportEmployee(requestDto);
    }
    @PostMapping(path = "/employees/detail")
    public ResponseEntity<?> executeDetailEmployee(@Valid  @RequestBody DetailEmployeeRequestDto employeeRequest) throws Exception {
        return employeeService.getDetailEmployee(employeeRequest);
    }
    
    @PostMapping(path = "/employees/update")
    public ResponseEntity<?> executepdateEmployee(@Valid  @RequestBody UpdateEmployeeRequestDto employeeRequest) throws Exception {
        return employeeService.updateEmployee(employeeRequest);
    }
}