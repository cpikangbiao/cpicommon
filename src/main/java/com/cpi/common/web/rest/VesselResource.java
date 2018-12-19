/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-18 上午9:40
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 */

package com.cpi.common.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.common.service.VesselService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.dto.VesselCriteria;
import com.cpi.common.service.VesselQueryService;
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
 * REST controller for managing Vessel.
 */
@RestController
@RequestMapping("/api")
public class VesselResource {

    private final Logger log = LoggerFactory.getLogger(VesselResource.class);

    private static final String ENTITY_NAME = "cpicommonVessel";

    private final VesselService vesselService;

    private final VesselQueryService vesselQueryService;

    public VesselResource(VesselService vesselService, VesselQueryService vesselQueryService) {
        this.vesselService = vesselService;
        this.vesselQueryService = vesselQueryService;
    }

    /**
     * POST  /vessels : Create a new vessel.
     *
     * @param vesselDTO the vesselDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vesselDTO, or with status 400 (Bad Request) if the vessel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vessels")
    @Timed
    public ResponseEntity<VesselDTO> createVessel(@Valid @RequestBody VesselDTO vesselDTO) throws URISyntaxException {
        log.debug("REST request to save Vessel : {}", vesselDTO);
        if (vesselDTO.getId() != null) {
            throw new BadRequestAlertException("A new vessel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VesselDTO result = vesselService.save(vesselDTO);
        return ResponseEntity.created(new URI("/api/vessels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vessels : Updates an existing vessel.
     *
     * @param vesselDTO the vesselDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vesselDTO,
     * or with status 400 (Bad Request) if the vesselDTO is not valid,
     * or with status 500 (Internal Server Error) if the vesselDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vessels")
    @Timed
    public ResponseEntity<VesselDTO> updateVessel(@Valid @RequestBody VesselDTO vesselDTO) throws URISyntaxException {
        log.debug("REST request to update Vessel : {}", vesselDTO);
        if (vesselDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VesselDTO result = vesselService.save(vesselDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vesselDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vessels : get all the vessels.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vessels in body
     */
    @GetMapping("/vessels")
    @Timed
    public ResponseEntity<List<VesselDTO>> getAllVessels(VesselCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vessels by criteria: {}", criteria);
        Page<VesselDTO> page = vesselQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vessels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /vessels/count : count all the vessels.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/vessels/count")
    @Timed
    public ResponseEntity<Long> countVessels(VesselCriteria criteria) {
        log.debug("REST request to count Vessels by criteria: {}", criteria);
        return ResponseEntity.ok().body(vesselQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /vessels/:id : get the "id" vessel.
     *
     * @param id the id of the vesselDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vesselDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vessels/{id}")
    @Timed
    public ResponseEntity<VesselDTO> getVessel(@PathVariable Long id) {
        log.debug("REST request to get Vessel : {}", id);
        Optional<VesselDTO> vesselDTO = vesselService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vesselDTO);
    }

    /**
     * DELETE  /vessels/:id : delete the "id" vessel.
     *
     * @param id the id of the vesselDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vessels/{id}")
    @Timed
    public ResponseEntity<Void> deleteVessel(@PathVariable Long id) {
        log.debug("REST request to delete Vessel : {}", id);
        vesselService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
