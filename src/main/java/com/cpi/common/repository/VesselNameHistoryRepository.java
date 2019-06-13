package com.cpi.common.repository;

import com.cpi.common.domain.VesselNameHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VesselNameHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselNameHistoryRepository extends JpaRepository<VesselNameHistory, Long>, JpaSpecificationExecutor<VesselNameHistory> {

}
