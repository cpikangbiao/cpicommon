package com.cpi.common.web.rest;

import com.cpi.common.service.VesselNameHistoryService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.dto.VesselNameHistoryCriteria;
import com.cpi.common.service.VesselNameHistoryQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.cpi.common.domain.VesselNameHistory}.
 */
@RestController
@RequestMapping("/api")
public class VesselNameHistoryResource {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryResource.class);

    private static final String ENTITY_NAME = "cpicommonVesselNameHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VesselNameHistoryService vesselNameHistoryService;

    private final VesselNameHistoryQueryService vesselNameHistoryQueryService;

    public VesselNameHistoryResource(VesselNameHistoryService vesselNameHistoryService, VesselNameHistoryQueryService vesselNameHistoryQueryService) {
        this.vesselNameHistoryService = vesselNameHistoryService;
        this.vesselNameHistoryQueryService = vesselNameHistoryQueryService;
    }

    /**
     * {@code POST  /vessel-name-histories} : Create a new vesselNameHistory.
     *
     * @param vesselNameHistoryDTO the vesselNameHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vesselNameHistoryDTO, or with status {@code 400 (Bad Request)} if the vesselNameHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vessel-name-histories")
    public ResponseEntity<VesselNameHistoryDTO> createVesselNameHistory(@RequestBody VesselNameHistoryDTO vesselNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save VesselNameHistory : {}", vesselNameHistoryDTO);
        if (vesselNameHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new vesselNameHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselNameHistoryDTO result = vesselNameHistoryService.save(vesselNameHistoryDTO);
        return ResponseEntity.created(new URI("/api/vessel-name-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vessel-name-histories} : Updates an existing vesselNameHistory.
     *
     * @param vesselNameHistoryDTO the vesselNameHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vesselNameHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the vesselNameHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vesselNameHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vessel-name-histories")
    public ResponseEntity<VesselNameHistoryDTO> updateVesselNameHistory(@RequestBody VesselNameHistoryDTO vesselNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update VesselNameHistory : {}", vesselNameHistoryDTO);
        if (vesselNameHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselNameHistoryDTO result = vesselNameHistoryService.save(vesselNameHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vesselNameHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vessel-name-histories} : get all the vesselNameHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vesselNameHistories in body.
     */
    @GetMapping("/vessel-name-histories")
    public ResponseEntity<List<VesselNameHistoryDTO>> getAllVesselNameHistories(VesselNameHistoryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get VesselNameHistories by criteria: {}", criteria);
        Page<VesselNameHistoryDTO> page = vesselNameHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /vessel-name-histories/count} : count all the vesselNameHistories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/vessel-name-histories/count")
    public ResponseEntity<Long> countVesselNameHistories(VesselNameHistoryCriteria criteria) {
        log.debug("REST request to count VesselNameHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(vesselNameHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vessel-name-histories/:id} : get the "id" vesselNameHistory.
     *
     * @param id the id of the vesselNameHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vesselNameHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vessel-name-histories/{id}")
    public ResponseEntity<VesselNameHistoryDTO> getVesselNameHistory(@PathVariable Long id) {
        log.debug("REST request to get VesselNameHistory : {}", id);
        Optional<VesselNameHistoryDTO> vesselNameHistoryDTO = vesselNameHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselNameHistoryDTO);
    }

    /**
     * {@code DELETE  /vessel-name-histories/:id} : delete the "id" vesselNameHistory.
     *
     * @param id the id of the vesselNameHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vessel-name-histories/{id}")
    public ResponseEntity<Void> deleteVesselNameHistory(@PathVariable Long id) {
        log.debug("REST request to delete VesselNameHistory : {}", id);
        vesselNameHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
