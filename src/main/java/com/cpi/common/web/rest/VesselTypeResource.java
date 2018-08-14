package com.cpi.common.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.common.service.VesselTypeService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import com.cpi.common.service.dto.VesselTypeDTO;
import com.cpi.common.service.dto.VesselTypeCriteria;
import com.cpi.common.service.VesselTypeQueryService;
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
 * REST controller for managing VesselType.
 */
@RestController
@RequestMapping("/api")
public class VesselTypeResource {

    private final Logger log = LoggerFactory.getLogger(VesselTypeResource.class);

    private static final String ENTITY_NAME = "vesselType";

    private final VesselTypeService vesselTypeService;

    private final VesselTypeQueryService vesselTypeQueryService;

    public VesselTypeResource(VesselTypeService vesselTypeService, VesselTypeQueryService vesselTypeQueryService) {
        this.vesselTypeService = vesselTypeService;
        this.vesselTypeQueryService = vesselTypeQueryService;
    }

    /**
     * POST  /vessel-types : Create a new vesselType.
     *
     * @param vesselTypeDTO the vesselTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vesselTypeDTO, or with status 400 (Bad Request) if the vesselType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vessel-types")
    @Timed
    public ResponseEntity<VesselTypeDTO> createVesselType(@Valid @RequestBody VesselTypeDTO vesselTypeDTO) throws URISyntaxException {
        log.debug("REST request to save VesselType : {}", vesselTypeDTO);
        if (vesselTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new vesselType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselTypeDTO result = vesselTypeService.save(vesselTypeDTO);
        return ResponseEntity.created(new URI("/api/vessel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vessel-types : Updates an existing vesselType.
     *
     * @param vesselTypeDTO the vesselTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vesselTypeDTO,
     * or with status 400 (Bad Request) if the vesselTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the vesselTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vessel-types")
    @Timed
    public ResponseEntity<VesselTypeDTO> updateVesselType(@Valid @RequestBody VesselTypeDTO vesselTypeDTO) throws URISyntaxException {
        log.debug("REST request to update VesselType : {}", vesselTypeDTO);
        if (vesselTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselTypeDTO result = vesselTypeService.save(vesselTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vesselTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vessel-types : get all the vesselTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vesselTypes in body
     */
    @GetMapping("/vessel-types")
    @Timed
    public ResponseEntity<List<VesselTypeDTO>> getAllVesselTypes(VesselTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VesselTypes by criteria: {}", criteria);
        Page<VesselTypeDTO> page = vesselTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vessel-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vessel-types/:id : get the "id" vesselType.
     *
     * @param id the id of the vesselTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vesselTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vessel-types/{id}")
    @Timed
    public ResponseEntity<VesselTypeDTO> getVesselType(@PathVariable Long id) {
        log.debug("REST request to get VesselType : {}", id);
        Optional<VesselTypeDTO> vesselTypeDTO = vesselTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselTypeDTO);
    }

    /**
     * DELETE  /vessel-types/:id : delete the "id" vesselType.
     *
     * @param id the id of the vesselTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vessel-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVesselType(@PathVariable Long id) {
        log.debug("REST request to delete VesselType : {}", id);
        vesselTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
