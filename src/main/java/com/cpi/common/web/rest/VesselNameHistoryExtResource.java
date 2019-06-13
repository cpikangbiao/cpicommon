/*
 * Copyright (c)  2015-2019, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 2019/6/13 下午4:27
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

import com.cpi.common.service.VesselNameHistoryExtService;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 * REST controller for managing VesselNameHistory.
 */
@RestController
@RequestMapping("/api")
public class VesselNameHistoryExtResource {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryExtResource.class);

    private static final String ENTITY_NAME = "cpicommonVesselNameHistory";

    private final VesselNameHistoryExtService vesselNameHistoryExtService;

    public VesselNameHistoryExtResource(VesselNameHistoryExtService vesselNameHistoryExtService) {
        this.vesselNameHistoryExtService = vesselNameHistoryExtService;
    }

    @GetMapping("/vessel-name-histories/by-vessel-id/{vesselId}")
    public ResponseEntity<List<VesselNameHistoryDTO>> findAllByVesselId(@PathVariable Long vesselId) {
        log.debug("REST request to get VesselNameHistories by vesselId: {}", vesselId);
        List<VesselNameHistoryDTO> vesselNameHistorys = vesselNameHistoryExtService.findAllByVesselId(vesselId);
        return ResponseEntity.ok().body(vesselNameHistorys);
    }

    @GetMapping("/vessel-name-histories/find-fit-name-by-vessel-id")
    public ResponseEntity<VesselNameHistoryDTO> findFitVesselNameByVesselId(
        @RequestParam Long vesselId,
        @RequestParam Instant specialTime) {
        log.debug("REST request to get Fit VesselName by vesselId: {} and Time : {}", vesselId, specialTime);
        VesselNameHistoryDTO vesselNameHistory = vesselNameHistoryExtService.findFitVesselNameByVesselId(vesselId, specialTime);
        return ResponseEntity.ok().body(vesselNameHistory);
    }
}
