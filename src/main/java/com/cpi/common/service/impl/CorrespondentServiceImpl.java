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

import com.cpi.common.service.CorrespondentService;
import com.cpi.common.domain.Correspondent;
import com.cpi.common.repository.CorrespondentRepository;
import com.cpi.common.service.dto.CorrespondentDTO;
import com.cpi.common.service.mapper.CorrespondentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Correspondent.
 */
@Service
@Transactional
public class CorrespondentServiceImpl implements CorrespondentService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentServiceImpl.class);

    private final CorrespondentRepository correspondentRepository;

    private final CorrespondentMapper correspondentMapper;

    public CorrespondentServiceImpl(CorrespondentRepository correspondentRepository, CorrespondentMapper correspondentMapper) {
        this.correspondentRepository = correspondentRepository;
        this.correspondentMapper = correspondentMapper;
    }

    /**
     * Save a correspondent.
     *
     * @param correspondentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentDTO save(CorrespondentDTO correspondentDTO) {
        log.debug("Request to save Correspondent : {}", correspondentDTO);

        Correspondent correspondent = correspondentMapper.toEntity(correspondentDTO);
        correspondent = correspondentRepository.save(correspondent);
        return correspondentMapper.toDto(correspondent);
    }

    /**
     * Get all the correspondents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Correspondents");
        return correspondentRepository.findAll(pageable)
            .map(correspondentMapper::toDto);
    }


    /**
     * Get one correspondent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentDTO> findOne(Long id) {
        log.debug("Request to get Correspondent : {}", id);
        return correspondentRepository.findById(id)
            .map(correspondentMapper::toDto);
    }

    /**
     * Delete the correspondent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Correspondent : {}", id);
        correspondentRepository.deleteById(id);
    }
}
