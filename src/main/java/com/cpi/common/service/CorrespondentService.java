package com.cpi.common.service;

import com.cpi.common.service.dto.CorrespondentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.common.domain.Correspondent}.
 */
public interface CorrespondentService {

    /**
     * Save a correspondent.
     *
     * @param correspondentDTO the entity to save.
     * @return the persisted entity.
     */
    CorrespondentDTO save(CorrespondentDTO correspondentDTO);

    /**
     * Get all the correspondents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CorrespondentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" correspondent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CorrespondentDTO> findOne(Long id);

    /**
     * Delete the "id" correspondent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
