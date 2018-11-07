package com.cpi.common.repository;

import com.cpi.common.domain.Address;
import com.cpi.common.domain.Company;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    Address findTopByAddressTypeIdAndCompanyId(Long addressTypeId, Long companyId);
}
