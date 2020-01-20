package jp.co.gzr_internal.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.ProjectDetail;
import jp.co.gzr_internal.api.domain.key.ProjectDetailKey;
import jp.co.gzr_internal.api.service.dto.PositionByEmployeeDto;

@Repository
public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, ProjectDetailKey> {

    List<ProjectDetail> findByProjectDetailKeyProjectCode(Integer projectCode);

    @Query(name = "selectPositionByEmployee", nativeQuery = true)
    List<PositionByEmployeeDto> getListPostionByEmployee(@Param("projectCode") int projectCode, 
                               @Param("employeeCode") String employeeCode);
}
