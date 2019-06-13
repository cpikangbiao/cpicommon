package com.cpi.common.service;

import com.cpi.common.service.dto.CompanyNameHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.common.domain.CompanyNameHistory}.
 */
public interface CompanyNameHistoryService {

    /**
     * Save a companyNameHistory.
     *
     * @param companyNameHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyNameHistoryDTO save(CompanyNameHistoryDTO companyNameHistoryDTO);

    /**
     * Get all the companyNameHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyNameHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" companyNameHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyNameHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" companyNameHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
