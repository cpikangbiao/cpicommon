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

package com.cpi.common.service.impl;

import com.cpi.common.service.VesselNameHistoryService;
import com.cpi.common.domain.VesselNameHistory;
import com.cpi.common.repository.VesselNameHistoryRepository;
import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.mapper.VesselNameHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing VesselNameHistory.
 */
@Service
@Transactional
public class VesselNameHistoryServiceImpl implements VesselNameHistoryService {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryServiceImpl.class);

    private final VesselNameHistoryRepository vesselNameHistoryRepository;

    private final VesselNameHistoryMapper vesselNameHistoryMapper;

    public VesselNameHistoryServiceImpl(VesselNameHistoryRepository vesselNameHistoryRepository, VesselNameHistoryMapper vesselNameHistoryMapper) {
        this.vesselNameHistoryRepository = vesselNameHistoryRepository;
        this.vesselNameHistoryMapper = vesselNameHistoryMapper;
    }

    /**
     * Save a vesselNameHistory.
     *
     * @param vesselNameHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselNameHistoryDTO save(VesselNameHistoryDTO vesselNameHistoryDTO) {
        log.debug("Request to save VesselNameHistory : {}", vesselNameHistoryDTO);

        VesselNameHistory vesselNameHistory = vesselNameHistoryMapper.toEntity(vesselNameHistoryDTO);
        vesselNameHistory = vesselNameHistoryRepository.save(vesselNameHistory);
        return vesselNameHistoryMapper.toDto(vesselNameHistory);
    }

    /**
     * Get all the vesselNameHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselNameHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VesselNameHistories");
        return vesselNameHistoryRepository.findAll(pageable)
            .map(vesselNameHistoryMapper::toDto);
    }


    /**
     * Get one vesselNameHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselNameHistoryDTO> findOne(Long id) {
        log.debug("Request to get VesselNameHistory : {}", id);
        return vesselNameHistoryRepository.findById(id)
            .map(vesselNameHistoryMapper::toDto);
    }

    /**
     * Delete the vesselNameHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselNameHistory : {}", id);
        vesselNameHistoryRepository.deleteById(id);
    }
}
