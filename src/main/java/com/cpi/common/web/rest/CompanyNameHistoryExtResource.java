/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-20 上午10:28
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
import com.cpi.common.service.CompanyNameHistoryExtService;
import com.cpi.common.service.CompanyNameHistoryQueryService;
import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.web.rest.errors.BadRequestAlertException;
import com.cpi.common.web.rest.util.HeaderUtil;
import com.cpi.common.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyNameHistory.
 */
@RestController
@RequestMapping("/api")
public class CompanyNameHistoryExtResource {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryExtResource.class);

    private static final String ENTITY_NAME = "cpicommonCompanyNameHistory";

    private final CompanyNameHistoryExtService companyNameHistoryExtService;


    public CompanyNameHistoryExtResource(CompanyNameHistoryExtService companyNameHistoryExtService) {
        this.companyNameHistoryExtService = companyNameHistoryExtService;
    }


    @GetMapping("/company-name-histories/by-company-id/{companyId}")
    @Timed
    public ResponseEntity<List<CompanyNameHistoryDTO>> findAllByCompanyId(@PathVariable Long companyId) {
        log.debug("REST request to get CompanyNameHistories by companyId: {}", companyId);
        List<CompanyNameHistoryDTO> companyNameHistorys = companyNameHistoryExtService.findAllByCompanyId(companyId);
        return ResponseEntity.ok().body(companyNameHistorys);
    }

    @GetMapping("/company-name-histories/find-fit-name-by-company-id")
    @Timed
    public ResponseEntity<CompanyNameHistoryDTO> findFitCompanyNameByCompanyId(
        @RequestParam Long companyId,
        @RequestParam Instant speicalTime) {
        log.debug("REST request to get Fit CompanyName by companyId: {} and Time : {}", companyId, speicalTime);
        CompanyNameHistoryDTO companyNameHistory = companyNameHistoryExtService.findFitCompanyNameByCompanyId(companyId, speicalTime);
        return ResponseEntity.ok().body(companyNameHistory);
    }

}
