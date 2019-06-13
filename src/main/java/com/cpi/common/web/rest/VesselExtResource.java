/*
 * Copyright (c)  2015-2019, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 19-1-23 上午10:26
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
import com.cpi.common.service.VesselExtService;
import com.cpi.common.service.dto.VesselDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Vessel.
 */
@RestController
@RequestMapping("/api")
public class VesselExtResource {

    private final Logger log = LoggerFactory.getLogger(VesselExtResource.class);

    private static final String ENTITY_NAME = "cpicommonVessel";

    private final VesselExtService vesselExtService;

    public VesselExtResource( VesselExtService vesselExtService) {
        this.vesselExtService = vesselExtService;
    }


    @GetMapping("/vessels/get-by-vessel-name-or-imo")
    @Timed
    public ResponseEntity<List<VesselDTO>> getAllByVesselNameOrImoNumber(@RequestParam("vesselName") String vesselName,
                                                         @RequestParam("imoNumber") String imoNumber) {
        log.debug("REST request to get Vessels by vesselName: {}  imoNumber: {}",vesselName , imoNumber);
        List<VesselDTO> vesselDTOs = vesselExtService.findAllByVesselNameOrImoNumber(vesselName, imoNumber);;
        return ResponseEntity.ok().body(vesselDTOs);
    }


}
