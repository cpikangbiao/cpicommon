package com.cpi.common.repository;

import com.cpi.common.domain.Vessel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vessel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselRepository extends JpaRepository<Vessel, Long>, JpaSpecificationExecutor<Vessel> {

}
