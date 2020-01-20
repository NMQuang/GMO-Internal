package jp.co.gzr_internal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByProjectCodeAndDeleteFlag(int projectCode, int deleteFlag);
    
    List<Project> findByDeleteFlag(Integer deleteFlag);

    @Query(name = "checkProjectOfEmployee")
    Integer checkProjectOfEmployee(@Param("projectCode") int projectCode, 
                               @Param("employeeCode") String employeeCode,
                               @Param("positionCode") int positionCode);
}
