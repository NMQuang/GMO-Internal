package jp.co.gzr_internal.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.MPosition;

@Repository
public interface MPositionRepository extends JpaRepository<MPosition, String> {

    List<MPosition> findByDeleteFlag(Integer deleteFlag);
}
