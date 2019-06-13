package com.cpi.common.web.rest;

import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.CompanyNameHistoryQueryService;

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
 * REST controller for managing {@link com.cpi.common.domain.CompanyNameHistory}.
 */
@RestController
@RequestMapping("/api")
public class CompanyNameHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryResource.class);

    private static final String ENTITY_NAME = "cpicommonCompanyNameHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyNameHistoryService companyNameHistoryService;

    private final CompanyNameHistoryQueryService companyNameHistoryQueryService;

    public CompanyNameHistoryResource(CompanyNameHistoryService companyNameHistoryService, CompanyNameHistoryQueryService companyNameHistoryQueryService) {
        this.companyNameHistoryService = companyNameHistoryService;
        this.companyNameHistoryQueryService = companyNameHistoryQueryService;
    }

    /**
     * {@code POST  /company-name-histories} : Create a new companyNameHistory.
     *
     * @param companyNameHistoryDTO the companyNameHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyNameHistoryDTO, or with status {@code 400 (Bad Request)} if the companyNameHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-name-histories")
    public ResponseEntity<CompanyNameHistoryDTO> createCompanyNameHistory(@RequestBody CompanyNameHistoryDTO companyNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyNameHistory : {}", companyNameHistoryDTO);
        if (companyNameHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyNameHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyNameHistoryDTO result = companyNameHistoryService.save(companyNameHistoryDTO);
        return ResponseEntity.created(new URI("/api/company-name-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-name-histories} : Updates an existing companyNameHistory.
     *
     * @param companyNameHistoryDTO the companyNameHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyNameHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the companyNameHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyNameHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-name-histories")
    public ResponseEntity<CompanyNameHistoryDTO> updateCompanyNameHistory(@RequestBody CompanyNameHistoryDTO companyNameHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyNameHistory : {}", companyNameHistoryDTO);
        if (companyNameHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyNameHistoryDTO result = companyNameHistoryService.save(companyNameHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyNameHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-name-histories} : get all the companyNameHistories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyNameHistories in body.
     */
    @GetMapping("/company-name-histories")
    public ResponseEntity<List<CompanyNameHistoryDTO>> getAllCompanyNameHistories(CompanyNameHistoryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CompanyNameHistories by criteria: {}", criteria);
        Page<CompanyNameHistoryDTO> page = companyNameHistoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /company-name-histories/count} : count all the companyNameHistories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/company-name-histories/count")
    public ResponseEntity<Long> countCompanyNameHistories(CompanyNameHistoryCriteria criteria) {
        log.debug("REST request to count CompanyNameHistories by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyNameHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-name-histories/:id} : get the "id" companyNameHistory.
     *
     * @param id the id of the companyNameHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyNameHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-name-histories/{id}")
    public ResponseEntity<CompanyNameHistoryDTO> getCompanyNameHistory(@PathVariable Long id) {
        log.debug("REST request to get CompanyNameHistory : {}", id);
        Optional<CompanyNameHistoryDTO> companyNameHistoryDTO = companyNameHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyNameHistoryDTO);
    }

    /**
     * {@code DELETE  /company-name-histories/:id} : delete the "id" companyNameHistory.
     *
     * @param id the id of the companyNameHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-name-histories/{id}")
    public ResponseEntity<Void> deleteCompanyNameHistory(@PathVariable Long id) {
        log.debug("REST request to delete CompanyNameHistory : {}", id);
        companyNameHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
