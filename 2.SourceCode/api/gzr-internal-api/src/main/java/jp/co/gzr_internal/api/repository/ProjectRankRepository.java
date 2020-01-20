package jp.co.gzr_internal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.ProjectRank;

@Repository
public interface ProjectRankRepository extends JpaRepository<ProjectRank, Integer>{

    Optional<ProjectRank> findByIdAndDeleteFlag(Integer id, int deleteFlag);
    
    List<ProjectRank> findByDeleteFlag(Integer deleteFlag);
}
