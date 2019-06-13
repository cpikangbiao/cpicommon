package com.cpi.common.web.rest;

import com.cpi.common.service.VesselService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.dto.VesselCriteria;
import com.cpi.common.service.VesselQueryService;

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
 * REST controller for managing {@link com.cpi.common.domain.Vessel}.
 */
@RestController
@RequestMapping("/api")
public class VesselResource {

    private final Logger log = LoggerFactory.getLogger(VesselResource.class);

    private static final String ENTITY_NAME = "cpicommonVessel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VesselService vesselService;

    private final VesselQueryService vesselQueryService;

    public VesselResource(VesselService vesselService, VesselQueryService vesselQueryService) {
        this.vesselService = vesselService;
        this.vesselQueryService = vesselQueryService;
    }

    /**
     * {@code POST  /vessels} : Create a new vessel.
     *
     * @param vesselDTO the vesselDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vesselDTO, or with status {@code 400 (Bad Request)} if the vessel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vessels")
    public ResponseEntity<VesselDTO> createVessel(@Valid @RequestBody VesselDTO vesselDTO) throws URISyntaxException {
        log.debug("REST request to save Vessel : {}", vesselDTO);
        if (vesselDTO.getId() != null) {
            throw new BadRequestAlertException("A new vessel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselDTO result = vesselService.save(vesselDTO);
        return ResponseEntity.created(new URI("/api/vessels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vessels} : Updates an existing vessel.
     *
     * @param vesselDTO the vesselDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselDTO,
     * or with status {@code 400 (Bad Request)} if the vesselDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vesselDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vessels")
    public ResponseEntity<VesselDTO> updateVessel(@Valid @RequestBody VesselDTO vesselDTO) throws URISyntaxException {
        log.debug("REST request to update Vessel : {}", vesselDTO);
        if (vesselDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselDTO result = vesselService.save(vesselDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vessels} : get all the vessels.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vessels in body.
     */
    @GetMapping("/vessels")
    public ResponseEntity<List<VesselDTO>> getAllVessels(VesselCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get Vessels by criteria: {}", criteria);
        Page<VesselDTO> page = vesselQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /vessels/count} : count all the vessels.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/vessels/count")
    public ResponseEntity<Long> countVessels(VesselCriteria criteria) {
        log.debug("REST request to count Vessels by criteria: {}", criteria);
        return ResponseEntity.ok().body(vesselQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vessels/:id} : get the "id" vessel.
     *
     * @param id the id of the vesselDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vesselDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vessels/{id}")
    public ResponseEntity<VesselDTO> getVessel(@PathVariable Long id) {
        log.debug("REST request to get Vessel : {}", id);
        Optional<VesselDTO> vesselDTO = vesselService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselDTO);
    }

    /**
     * {@code DELETE  /vessels/:id} : delete the "id" vessel.
     *
     * @param id the id of the vesselDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vessels/{id}")
    public ResponseEntity<Void> deleteVessel(@PathVariable Long id) {
        log.debug("REST request to delete Vessel : {}", id);
        vesselService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
