package com.cpi.common.repository;

import com.cpi.common.domain.CurrencyRate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CurrencyRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long>, JpaSpecificationExecutor<CurrencyRate> {

}
