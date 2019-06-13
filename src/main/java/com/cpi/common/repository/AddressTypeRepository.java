package com.cpi.common.repository;

import com.cpi.common.domain.AddressType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AddressType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Long>, JpaSpecificationExecutor<AddressType> {

}
