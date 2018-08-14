package com.cpi.common.repository;

import com.cpi.common.domain.VesselType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VesselType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VesselTypeRepository extends JpaRepository<VesselType, Long>, JpaSpecificationExecutor<VesselType> {

}
