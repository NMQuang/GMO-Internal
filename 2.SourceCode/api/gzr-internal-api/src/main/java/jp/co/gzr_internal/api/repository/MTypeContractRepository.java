package jp.co.gzr_internal.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.MTypeContact;

@Repository
public interface MTypeContractRepository extends JpaRepository<MTypeContact, String>  {

}
