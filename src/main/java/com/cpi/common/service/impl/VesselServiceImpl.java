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

import com.cpi.common.service.VesselService;
import com.cpi.common.domain.Vessel;
import com.cpi.common.repository.VesselRepository;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.mapper.VesselMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Vessel.
 */
@Service
@Transactional
public class VesselServiceImpl implements VesselService {

    private final Logger log = LoggerFactory.getLogger(VesselServiceImpl.class);

    private final VesselRepository vesselRepository;

    private final VesselMapper vesselMapper;

    public VesselServiceImpl(VesselRepository vesselRepository, VesselMapper vesselMapper) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
    }

    /**
     * Save a vessel.
     *
     * @param vesselDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselDTO save(VesselDTO vesselDTO) {
        log.debug("Request to save Vessel : {}", vesselDTO);

        Vessel vessel = vesselMapper.toEntity(vesselDTO);
        vessel = vesselRepository.save(vessel);
        return vesselMapper.toDto(vessel);
    }

    /**
     * Get all the vessels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vessels");
        return vesselRepository.findAll(pageable)
            .map(vesselMapper::toDto);
    }


    /**
     * Get one vessel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselDTO> findOne(Long id) {
        log.debug("Request to get Vessel : {}", id);
        return vesselRepository.findById(id)
            .map(vesselMapper::toDto);
    }

    /**
     * Delete the vessel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vessel : {}", id);
        vesselRepository.deleteById(id);
    }
}
