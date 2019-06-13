package com.cpi.common.service;

import com.cpi.common.service.dto.ClassificationSocietyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.cpi.common.domain.ClassificationSociety}.
 */
public interface ClassificationSocietyService {

    /**
     * Save a classificationSociety.
     *
     * @param classificationSocietyDTO the entity to save.
     * @return the persisted entity.
     */
    ClassificationSocietyDTO save(ClassificationSocietyDTO classificationSocietyDTO);

    /**
     * Get all the classificationSocieties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassificationSocietyDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classificationSociety.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassificationSocietyDTO> findOne(Long id);

    /**
     * Delete the "id" classificationSociety.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
