package com.cpi.common.web.rest;

import com.cpi.common.service.ClassificationSocietyService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.service.dto.ClassificationSocietyDTO;
import com.cpi.common.service.dto.ClassificationSocietyCriteria;
import com.cpi.common.service.ClassificationSocietyQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.common.domain.ClassificationSociety}.
 */
@RestController
@RequestMapping("/api")
public class ClassificationSocietyResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationSocietyResource.class);

    private static final String ENTITY_NAME = "cpicommonClassificationSociety";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassificationSocietyService classificationSocietyService;

    private final ClassificationSocietyQueryService classificationSocietyQueryService;

    public ClassificationSocietyResource(ClassificationSocietyService classificationSocietyService, ClassificationSocietyQueryService classificationSocietyQueryService) {
        this.classificationSocietyService = classificationSocietyService;
        this.classificationSocietyQueryService = classificationSocietyQueryService;
    }

    /**
     * {@code POST  /classification-societies} : Create a new classificationSociety.
     *
     * @param classificationSocietyDTO the classificationSocietyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classificationSocietyDTO, or with status {@code 400 (Bad Request)} if the classificationSociety has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/classification-societies")
    public ResponseEntity<ClassificationSocietyDTO> createClassificationSociety(@Valid @RequestBody ClassificationSocietyDTO classificationSocietyDTO) throws URISyntaxException {
        log.debug("REST request to save ClassificationSociety : {}", classificationSocietyDTO);
        if (classificationSocietyDTO.getId() != null) {
            throw new BadRequestAlertException("A new classificationSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassificationSocietyDTO result = classificationSocietyService.save(classificationSocietyDTO);
        return ResponseEntity.created(new URI("/api/classification-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /classification-societies} : Updates an existing classificationSociety.
     *
     * @param classificationSocietyDTO the classificationSocietyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classificationSocietyDTO,
     * or with status {@code 400 (Bad Request)} if the classificationSocietyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classificationSocietyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/classification-societies")
    public ResponseEntity<ClassificationSocietyDTO> updateClassificationSociety(@Valid @RequestBody ClassificationSocietyDTO classificationSocietyDTO) throws URISyntaxException {
        log.debug("REST request to update ClassificationSociety : {}", classificationSocietyDTO);
        if (classificationSocietyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassificationSocietyDTO result = classificationSocietyService.save(classificationSocietyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classificationSocietyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /classification-societies} : get all the classificationSocieties.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classificationSocieties in body.
     */
    @GetMapping("/classification-societies")
    public ResponseEntity<List<ClassificationSocietyDTO>> getAllClassificationSocieties(ClassificationSocietyCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ClassificationSocieties by criteria: {}", criteria);
        Page<ClassificationSocietyDTO> page = classificationSocietyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /classification-societies/count} : count all the classificationSocieties.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/classification-societies/count")
    public ResponseEntity<Long> countClassificationSocieties(ClassificationSocietyCriteria criteria) {
        log.debug("REST request to count ClassificationSocieties by criteria: {}", criteria);
        return ResponseEntity.ok().body(classificationSocietyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /classification-societies/:id} : get the "id" classificationSociety.
     *
     * @param id the id of the classificationSocietyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classificationSocietyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/classification-societies/{id}")
    public ResponseEntity<ClassificationSocietyDTO> getClassificationSociety(@PathVariable Long id) {
        log.debug("REST request to get ClassificationSociety : {}", id);
        Optional<ClassificationSocietyDTO> classificationSocietyDTO = classificationSocietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classificationSocietyDTO);
    }

    /**
     * {@code DELETE  /classification-societies/:id} : delete the "id" classificationSociety.
     *
     * @param id the id of the classificationSocietyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/classification-societies/{id}")
    public ResponseEntity<Void> deleteClassificationSociety(@PathVariable Long id) {
        log.debug("REST request to delete ClassificationSociety : {}", id);
        classificationSocietyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
