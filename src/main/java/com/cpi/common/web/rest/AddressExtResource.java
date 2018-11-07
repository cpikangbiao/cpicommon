package com.cpi.common.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.common.service.AddressExtService;
import com.cpi.common.service.AddressQueryService;
import com.cpi.common.service.AddressService;
import com.cpi.common.service.dto.AddressCriteria;
import com.cpi.common.service.dto.AddressDTO;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
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
 * REST controller for managing Address.
 */
@RestController
@RequestMapping("/api")
public class AddressExtResource {

    private final Logger log = LoggerFactory.getLogger(AddressExtResource.class);

    private static final String ENTITY_NAME = "address";

    private final AddressExtService addressExtService;


    public AddressExtResource(AddressExtService addressExtService) {
        this.addressExtService = addressExtService;
    }

    @GetMapping("/addresses/{companyId}/register")
    @Timed
    public ResponseEntity<AddressDTO> getRegisterAddress(@PathVariable Long companyId) {
        log.debug("REST request to get Address for companyId : {}", companyId);
        AddressDTO addressDTO = addressExtService.findRegisterAddress(companyId);
        return new ResponseEntity(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/addresses/{companyId}/english")
    @Timed
    public ResponseEntity<AddressDTO> getEnglishAddress(@PathVariable Long companyId) {
        log.debug("REST request to get Address for companyId : {}", companyId);
        AddressDTO addressDTO = addressExtService.findEnglishAddress(companyId);
        return new ResponseEntity(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/addresses/{companyId}/chinese")
    @Timed
    public ResponseEntity<AddressDTO> getChineseAddress(@PathVariable Long companyId) {
        log.debug("REST request to get Address for companyId : {}", companyId);
        AddressDTO addressDTO = addressExtService.findChineseAddress(companyId);
        return new ResponseEntity(addressDTO, HttpStatus.OK);
    }


}
