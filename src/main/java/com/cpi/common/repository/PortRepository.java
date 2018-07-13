package com.cpi.common.repository;

import com.cpi.common.domain.Port;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Port entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortRepository extends JpaRepository<Port, Long>, JpaSpecificationExecutor<Port> {

}
