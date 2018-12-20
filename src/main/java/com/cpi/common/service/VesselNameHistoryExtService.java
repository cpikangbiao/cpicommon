/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-20 上午10:37
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

import com.cpi.common.domain.VesselNameHistory;
import com.cpi.common.repository.VesselNameHistoryRepository;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.mapper.VesselNameHistoryMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@Service
@Transactional
public class VesselNameHistoryExtService extends QueryService<VesselNameHistory> {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryExtService.class);

    private final VesselNameHistoryRepository vesselNameHistoryRepository;

    private final VesselNameHistoryMapper vesselNameHistoryMapper;

    public VesselNameHistoryExtService(VesselNameHistoryRepository vesselNameHistoryRepository, VesselNameHistoryMapper vesselNameHistoryMapper) {
        this.vesselNameHistoryRepository = vesselNameHistoryRepository;
        this.vesselNameHistoryMapper = vesselNameHistoryMapper;
    }

    @Transactional(readOnly = true)
    public List<VesselNameHistoryDTO> findAllByVesselId(Long vesselId) {
        log.debug("find findAll By vesselId : {}", vesselId);
        return vesselNameHistoryMapper.toDto(vesselNameHistoryRepository.findAllByVesselIdOrderByEndDateDesc(vesselId));
    }

    @Transactional(readOnly = true)
    public VesselNameHistoryDTO findFitVesselNameByVesselId(Long vesselId, Instant specialDate) {
        log.debug("find findAll By vesselId : {} and time : {}", vesselId, specialDate);
        return vesselNameHistoryMapper.toDto(vesselNameHistoryRepository.findFirstByVesselIdAndEndDateAfterOrderByEndDateAsc(vesselId, specialDate));
    }
}
