package com.cpi.common.repository;

import com.cpi.common.domain.Correspondent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Correspondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentRepository extends JpaRepository<Correspondent, Long>, JpaSpecificationExecutor<Correspondent> {

}
