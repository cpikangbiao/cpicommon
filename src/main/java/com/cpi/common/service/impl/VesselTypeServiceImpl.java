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

import com.cpi.common.service.VesselTypeService;
import com.cpi.common.domain.VesselType;
import com.cpi.common.repository.VesselTypeRepository;
import com.cpi.common.service.dto.VesselTypeDTO;
import com.cpi.common.service.mapper.VesselTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing VesselType.
 */
@Service
@Transactional
public class VesselTypeServiceImpl implements VesselTypeService {

    private final Logger log = LoggerFactory.getLogger(VesselTypeServiceImpl.class);

    private final VesselTypeRepository vesselTypeRepository;

    private final VesselTypeMapper vesselTypeMapper;

    public VesselTypeServiceImpl(VesselTypeRepository vesselTypeRepository, VesselTypeMapper vesselTypeMapper) {
        this.vesselTypeRepository = vesselTypeRepository;
        this.vesselTypeMapper = vesselTypeMapper;
    }

    /**
     * Save a vesselType.
     *
     * @param vesselTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VesselTypeDTO save(VesselTypeDTO vesselTypeDTO) {
        log.debug("Request to save VesselType : {}", vesselTypeDTO);

        VesselType vesselType = vesselTypeMapper.toEntity(vesselTypeDTO);
        vesselType = vesselTypeRepository.save(vesselType);
        return vesselTypeMapper.toDto(vesselType);
    }

    /**
     * Get all the vesselTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VesselTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VesselTypes");
        return vesselTypeRepository.findAll(pageable)
            .map(vesselTypeMapper::toDto);
    }


    /**
     * Get one vesselType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VesselTypeDTO> findOne(Long id) {
        log.debug("Request to get VesselType : {}", id);
        return vesselTypeRepository.findById(id)
            .map(vesselTypeMapper::toDto);
    }

    /**
     * Delete the vesselType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VesselType : {}", id);
        vesselTypeRepository.deleteById(id);
    }
}
