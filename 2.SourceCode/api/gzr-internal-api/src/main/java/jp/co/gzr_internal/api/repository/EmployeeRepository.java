package jp.co.gzr_internal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;

/**
 * The Interface EmployeeRepository.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{

    /**
     * Find by status and delete flag.
     *
     * @param status the status
     * @param deleteFlag the delete flag
     * @return the list
     */
    List<Employee> findByStatusAndDeleteFlag(Integer status, Integer deleteFlag);
    
    Optional<Employee> findByEmployeeCodeAndDeleteFlag(String employeeCode, int deleteFlag);

    /**
     * Select by role id.
     *
     * @param roleId the role id
     * @return the list
     */
    @Query(name = "selectByRoleId", nativeQuery = true)
    List<EmployeeDto> selectByRoleId(@Param("roleId") Integer roleId);

    @Query(name = "selectByEmployeeCode", nativeQuery = true)
    EmployeeDto selectByEmployeeCode(@Param("employeeCode") String employeeCode);
    
    List<Employee> findByDeleteFlag(Integer deleteFlag);
    
    Optional<Employee> findByEmailAndDeleteFlag(String email, int deleteFlag);
}
