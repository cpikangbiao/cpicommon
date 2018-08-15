package com.cpi.common.repository;

import com.cpi.common.domain.CompanyNameHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompanyNameHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyNameHistoryRepository extends JpaRepository<CompanyNameHistory, Long>, JpaSpecificationExecutor<CompanyNameHistory> {

}
