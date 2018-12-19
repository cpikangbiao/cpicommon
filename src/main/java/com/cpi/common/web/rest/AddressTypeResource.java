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
import com.cpi.common.service.AddressTypeService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import com.cpi.common.service.dto.AddressTypeDTO;
import com.cpi.common.service.dto.AddressTypeCriteria;
import com.cpi.common.service.AddressTypeQueryService;
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
 * REST controller for managing AddressType.
 */
@RestController
@RequestMapping("/api")
public class AddressTypeResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypeResource.class);

    private static final String ENTITY_NAME = "cpicommonAddressType";

    private final AddressTypeService addressTypeService;

    private final AddressTypeQueryService addressTypeQueryService;

    public AddressTypeResource(AddressTypeService addressTypeService, AddressTypeQueryService addressTypeQueryService) {
        this.addressTypeService = addressTypeService;
        this.addressTypeQueryService = addressTypeQueryService;
    }

    /**
     * POST  /address-types : Create a new addressType.
     *
     * @param addressTypeDTO the addressTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new addressTypeDTO, or with status 400 (Bad Request) if the addressType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/address-types")
    @Timed
    public ResponseEntity<AddressTypeDTO> createAddressType(@Valid @RequestBody AddressTypeDTO addressTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AddressType : {}", addressTypeDTO);
        if (addressTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressTypeDTO result = addressTypeService.save(addressTypeDTO);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /address-types : Updates an existing addressType.
     *
     * @param addressTypeDTO the addressTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated addressTypeDTO,
     * or with status 400 (Bad Request) if the addressTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the addressTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/address-types")
    @Timed
    public ResponseEntity<AddressTypeDTO> updateAddressType(@Valid @RequestBody AddressTypeDTO addressTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AddressType : {}", addressTypeDTO);
        if (addressTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressTypeDTO result = addressTypeService.save(addressTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, addressTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /address-types : get all the addressTypes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of addressTypes in body
     */
    @GetMapping("/address-types")
    @Timed
    public ResponseEntity<List<AddressTypeDTO>> getAllAddressTypes(AddressTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AddressTypes by criteria: {}", criteria);
        Page<AddressTypeDTO> page = addressTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/address-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /address-types/count : count all the addressTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/address-types/count")
    @Timed
    public ResponseEntity<Long> countAddressTypes(AddressTypeCriteria criteria) {
        log.debug("REST request to count AddressTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(addressTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /address-types/:id : get the "id" addressType.
     *
     * @param id the id of the addressTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the addressTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/address-types/{id}")
    @Timed
    public ResponseEntity<AddressTypeDTO> getAddressType(@PathVariable Long id) {
        log.debug("REST request to get AddressType : {}", id);
        Optional<AddressTypeDTO> addressTypeDTO = addressTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressTypeDTO);
    }

    /**
     * DELETE  /address-types/:id : delete the "id" addressType.
     *
     * @param id the id of the addressTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/address-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAddressType(@PathVariable Long id) {
        log.debug("REST request to delete AddressType : {}", id);
        addressTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
