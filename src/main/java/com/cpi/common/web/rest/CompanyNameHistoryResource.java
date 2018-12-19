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
import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.CompanyNameHistoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyNameHistory.
 */
@RestController
@RequestMapping("/api")
public class CompanyNameHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryResource.class);

    private static final String ENTITY_NAME = "cpicommonCompanyNameHistory";

    private final CompanyNameHistoryService companyNameHistoryService;

    private final CompanyNameHistoryQueryService companyNameHistoryQueryService;

    public CompanyNameHistoryResource(CompanyNameHistoryService companyNameHistoryService, CompanyNameHistoryQueryService companyNameHistoryQueryService) {
        this.companyNameHistoryService = companyNameHistoryService;
        this.companyNameHistoryQueryService = companyNameHistoryQueryService;
    }

    /**
     * POST  /company-name-histories : Create a new companyNameHistory.
     *
     * @param companyNameHistoryDTO the companyNameHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyNameHistoryDTO, or with status 400 (Bad Request) if the companyNameHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-name-histories")
    @Timed
    public ResponseEntity<CompanyNameHistoryDTO> createCompanyNameHistory(@RequestBody CompanyNameHistoryDTO companyNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyNameHistory : {}", companyNameHistoryDTO);
        if (companyNameHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyNameHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyNameHistoryDTO result = companyNameHistoryService.save(companyNameHistoryDTO);
        return ResponseEntity.created(new URI("/api/company-name-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-name-histories : Updates an existing companyNameHistory.
     *
     * @param companyNameHistoryDTO the companyNameHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyNameHistoryDTO,
     * or with status 400 (Bad Request) if the companyNameHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyNameHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-name-histories")
    @Timed
    public ResponseEntity<CompanyNameHistoryDTO> updateCompanyNameHistory(@RequestBody CompanyNameHistoryDTO companyNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyNameHistory : {}", companyNameHistoryDTO);
        if (companyNameHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyNameHistoryDTO result = companyNameHistoryService.save(companyNameHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyNameHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-name-histories : get all the companyNameHistories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of companyNameHistories in body
     */
    @GetMapping("/company-name-histories")
    @Timed
    public ResponseEntity<List<CompanyNameHistoryDTO>> getAllCompanyNameHistories(CompanyNameHistoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompanyNameHistories by criteria: {}", criteria);
        Page<CompanyNameHistoryDTO> page = companyNameHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-name-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /company-name-histories/count : count all the companyNameHistories.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/company-name-histories/count")
    @Timed
    public ResponseEntity<Long> countCompanyNameHistories(CompanyNameHistoryCriteria criteria) {
        log.debug("REST request to count CompanyNameHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyNameHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /company-name-histories/:id : get the "id" companyNameHistory.
     *
     * @param id the id of the companyNameHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyNameHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-name-histories/{id}")
    @Timed
    public ResponseEntity<CompanyNameHistoryDTO> getCompanyNameHistory(@PathVariable Long id) {
        log.debug("REST request to get CompanyNameHistory : {}", id);
        Optional<CompanyNameHistoryDTO> companyNameHistoryDTO = companyNameHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyNameHistoryDTO);
    }

    /**
     * DELETE  /company-name-histories/:id : delete the "id" companyNameHistory.
     *
     * @param id the id of the companyNameHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-name-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyNameHistory(@PathVariable Long id) {
        log.debug("REST request to delete CompanyNameHistory : {}", id);
        companyNameHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
