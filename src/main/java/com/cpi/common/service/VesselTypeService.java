package com.cpi.common.service;

import com.cpi.common.service.dto.VesselTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.common.domain.VesselType}.
 */
public interface VesselTypeService {

    /**
     * Save a vesselType.
     *
     * @param vesselTypeDTO the entity to save.
     * @return the persisted entity.
     */
    VesselTypeDTO save(VesselTypeDTO vesselTypeDTO);

    /**
     * Get all the vesselTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VesselTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vesselType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VesselTypeDTO> findOne(Long id);

    /**
     * Delete the "id" vesselType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
