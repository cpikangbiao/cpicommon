package com.cpi.common.web.rest;

import com.cpi.common.service.AddressTypeService;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.service.dto.AddressTypeDTO;
import com.cpi.common.service.dto.AddressTypeCriteria;
import com.cpi.common.service.AddressTypeQueryService;

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
 * REST controller for managing {@link com.cpi.common.domain.AddressType}.
 */
@RestController
@RequestMapping("/api")
public class AddressTypeResource {

    private final Logger log = LoggerFactory.getLogger(AddressTypeResource.class);

    private static final String ENTITY_NAME = "cpicommonAddressType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressTypeService addressTypeService;

    private final AddressTypeQueryService addressTypeQueryService;

    public AddressTypeResource(AddressTypeService addressTypeService, AddressTypeQueryService addressTypeQueryService) {
        this.addressTypeService = addressTypeService;
        this.addressTypeQueryService = addressTypeQueryService;
    }

    /**
     * {@code POST  /address-types} : Create a new addressType.
     *
     * @param addressTypeDTO the addressTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressTypeDTO, or with status {@code 400 (Bad Request)} if the addressType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-types")
    public ResponseEntity<AddressTypeDTO> createAddressType(@Valid @RequestBody AddressTypeDTO addressTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AddressType : {}", addressTypeDTO);
        if (addressTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressTypeDTO result = addressTypeService.save(addressTypeDTO);
        return ResponseEntity.created(new URI("/api/address-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-types} : Updates an existing addressType.
     *
     * @param addressTypeDTO the addressTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressTypeDTO,
     * or with status {@code 400 (Bad Request)} if the addressTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-types")
    public ResponseEntity<AddressTypeDTO> updateAddressType(@Valid @RequestBody AddressTypeDTO addressTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AddressType : {}", addressTypeDTO);
        if (addressTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressTypeDTO result = addressTypeService.save(addressTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /address-types} : get all the addressTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressTypes in body.
     */
    @GetMapping("/address-types")
    public ResponseEntity<List<AddressTypeDTO>> getAllAddressTypes(AddressTypeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AddressTypes by criteria: {}", criteria);
        Page<AddressTypeDTO> page = addressTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /address-types/count} : count all the addressTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/address-types/count")
    public ResponseEntity<Long> countAddressTypes(AddressTypeCriteria criteria) {
        log.debug("REST request to count AddressTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(addressTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /address-types/:id} : get the "id" addressType.
     *
     * @param id the id of the addressTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-types/{id}")
    public ResponseEntity<AddressTypeDTO> getAddressType(@PathVariable Long id) {
        log.debug("REST request to get AddressType : {}", id);
        Optional<AddressTypeDTO> addressTypeDTO = addressTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressTypeDTO);
    }

    /**
     * {@code DELETE  /address-types/:id} : delete the "id" addressType.
     *
     * @param id the id of the addressTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-types/{id}")
    public ResponseEntity<Void> deleteAddressType(@PathVariable Long id) {
        log.debug("REST request to delete AddressType : {}", id);
        addressTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
