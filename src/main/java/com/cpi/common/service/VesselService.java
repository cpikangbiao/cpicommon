package com.cpi.common.service;

import com.cpi.common.service.dto.VesselDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Vessel.
 */
public interface VesselService {

    /**
     * Save a vessel.
     *
     * @param vesselDTO the entity to save
     * @return the persisted entity
     */
    VesselDTO save(VesselDTO vesselDTO);

    /**
     * Get all the vessels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VesselDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vessel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VesselDTO> findOne(Long id);

    /**
     * Delete the "id" vessel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
