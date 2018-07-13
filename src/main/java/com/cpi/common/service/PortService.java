package com.cpi.common.service;

import com.cpi.common.service.dto.PortDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Port.
 */
public interface PortService {

    /**
     * Save a port.
     *
     * @param portDTO the entity to save
     * @return the persisted entity
     */
    PortDTO save(PortDTO portDTO);

    /**
     * Get all the ports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PortDTO> findAll(Pageable pageable);

    /**
     * Get the "id" port.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PortDTO findOne(Long id);

    /**
     * Delete the "id" port.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
