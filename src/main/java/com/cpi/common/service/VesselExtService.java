/*
 * Copyright (c)  2015-2019, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 19-1-23 上午10:24
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

package com.cpi.common.service;

import com.cpi.common.domain.Vessel;
import com.cpi.common.repository.VesselRepository;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.mapper.VesselMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VesselExtService extends QueryService<Vessel> {

    private final Logger log = LoggerFactory.getLogger(VesselExtService.class);

    private final VesselRepository vesselRepository;

    private final VesselMapper vesselMapper;

    public VesselExtService(VesselRepository vesselRepository, VesselMapper vesselMapper) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
    }


    @Transactional(readOnly = true)
    public List<VesselDTO> findAllByVesselNameOrImoNumber(String vesselName, String imoNumber) {
        log.debug("find by findBy VesselName : {} Or ImoNumber : {}", vesselName, imoNumber );
        final List<Vessel> vessels = vesselRepository.findAllByVesselNameContainingIgnoreCaseOrImoNumber(vesselName, imoNumber );
        return vesselMapper.toDto(vessels);
    }


}
