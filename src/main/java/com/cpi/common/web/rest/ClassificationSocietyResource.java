package com.cpi.common.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.common.service.ClassificationSocietyService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import com.cpi.common.service.dto.ClassificationSocietyDTO;
import com.cpi.common.service.dto.ClassificationSocietyCriteria;
import com.cpi.common.service.ClassificationSocietyQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClassificationSociety.
 */
@RestController
@RequestMapping("/api")
public class ClassificationSocietyResource {

    private final Logger log = LoggerFactory.getLogger(ClassificationSocietyResource.class);

    private static final String ENTITY_NAME = "classificationSociety";

    private final ClassificationSocietyService classificationSocietyService;

    private final ClassificationSocietyQueryService classificationSocietyQueryService;

    public ClassificationSocietyResource(ClassificationSocietyService classificationSocietyService, ClassificationSocietyQueryService classificationSocietyQueryService) {
        this.classificationSocietyService = classificationSocietyService;
        this.classificationSocietyQueryService = classificationSocietyQueryService;
    }

    /**
     * POST  /classification-societies : Create a new classificationSociety.
     *
     * @param classificationSocietyDTO the classificationSocietyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classificationSocietyDTO, or with status 400 (Bad Request) if the classificationSociety has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classification-societies")
    @Timed
    public ResponseEntity<ClassificationSocietyDTO> createClassificationSociety(@Valid @RequestBody ClassificationSocietyDTO classificationSocietyDTO) throws URISyntaxException {
        log.debug("REST request to save ClassificationSociety : {}", classificationSocietyDTO);
        if (classificationSocietyDTO.getId() != null) {
            throw new BadRequestAlertException("A new classificationSociety cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassificationSocietyDTO result = classificationSocietyService.save(classificationSocietyDTO);
        return ResponseEntity.created(new URI("/api/classification-societies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classification-societies : Updates an existing classificationSociety.
     *
     * @param classificationSocietyDTO the classificationSocietyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classificationSocietyDTO,
     * or with status 400 (Bad Request) if the classificationSocietyDTO is not valid,
     * or with status 500 (Internal Server Error) if the classificationSocietyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classification-societies")
    @Timed
    public ResponseEntity<ClassificationSocietyDTO> updateClassificationSociety(@Valid @RequestBody ClassificationSocietyDTO classificationSocietyDTO) throws URISyntaxException {
        log.debug("REST request to update ClassificationSociety : {}", classificationSocietyDTO);
        if (classificationSocietyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassificationSocietyDTO result = classificationSocietyService.save(classificationSocietyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classificationSocietyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classification-societies : get all the classificationSocieties.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classificationSocieties in body
     */
    @GetMapping("/classification-societies")
    @Timed
    public ResponseEntity<List<ClassificationSocietyDTO>> getAllClassificationSocieties(ClassificationSocietyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ClassificationSocieties by criteria: {}", criteria);
        Page<ClassificationSocietyDTO> page = classificationSocietyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classification-societies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /classification-societies/:id : get the "id" classificationSociety.
     *
     * @param id the id of the classificationSocietyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classificationSocietyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/classification-societies/{id}")
    @Timed
    public ResponseEntity<ClassificationSocietyDTO> getClassificationSociety(@PathVariable Long id) {
        log.debug("REST request to get ClassificationSociety : {}", id);
        Optional<ClassificationSocietyDTO> classificationSocietyDTO = classificationSocietyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classificationSocietyDTO);
    }

    /**
     * DELETE  /classification-societies/:id : delete the "id" classificationSociety.
     *
     * @param id the id of the classificationSocietyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classification-societies/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassificationSociety(@PathVariable Long id) {
        log.debug("REST request to delete ClassificationSociety : {}", id);
        classificationSocietyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
