package com.cpi.common.service;

import com.cpi.common.service.dto.CorrespondentContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CorrespondentContact.
 */
public interface CorrespondentContactService {

    /**
     * Save a correspondentContact.
     *
     * @param correspondentContactDTO the entity to save
     * @return the persisted entity
     */
    CorrespondentContactDTO save(CorrespondentContactDTO correspondentContactDTO);

    /**
     * Get all the correspondentContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CorrespondentContactDTO> findAll(Pageable pageable);

    /**
     * Get the "id" correspondentContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CorrespondentContactDTO findOne(Long id);

    /**
     * Delete the "id" correspondentContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
