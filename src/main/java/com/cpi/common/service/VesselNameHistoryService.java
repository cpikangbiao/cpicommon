package com.cpi.common.service;

import com.cpi.common.service.dto.VesselNameHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.common.domain.VesselNameHistory}.
 */
public interface VesselNameHistoryService {

    /**
     * Save a vesselNameHistory.
     *
     * @param vesselNameHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    VesselNameHistoryDTO save(VesselNameHistoryDTO vesselNameHistoryDTO);

    /**
     * Get all the vesselNameHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VesselNameHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vesselNameHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VesselNameHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" vesselNameHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
