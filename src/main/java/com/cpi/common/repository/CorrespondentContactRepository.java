package com.cpi.common.repository;

import com.cpi.common.domain.CorrespondentContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CorrespondentContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentContactRepository extends JpaRepository<CorrespondentContact, Long>, JpaSpecificationExecutor<CorrespondentContact> {

}
