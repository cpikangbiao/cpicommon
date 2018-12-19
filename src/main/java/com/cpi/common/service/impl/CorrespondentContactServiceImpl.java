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

import com.cpi.common.service.CorrespondentContactService;
import com.cpi.common.domain.CorrespondentContact;
import com.cpi.common.repository.CorrespondentContactRepository;
import com.cpi.common.service.dto.CorrespondentContactDTO;
import com.cpi.common.service.mapper.CorrespondentContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CorrespondentContact.
 */
@Service
@Transactional
public class CorrespondentContactServiceImpl implements CorrespondentContactService {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactServiceImpl.class);

    private final CorrespondentContactRepository correspondentContactRepository;

    private final CorrespondentContactMapper correspondentContactMapper;

    public CorrespondentContactServiceImpl(CorrespondentContactRepository correspondentContactRepository, CorrespondentContactMapper correspondentContactMapper) {
        this.correspondentContactRepository = correspondentContactRepository;
        this.correspondentContactMapper = correspondentContactMapper;
    }

    /**
     * Save a correspondentContact.
     *
     * @param correspondentContactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CorrespondentContactDTO save(CorrespondentContactDTO correspondentContactDTO) {
        log.debug("Request to save CorrespondentContact : {}", correspondentContactDTO);

        CorrespondentContact correspondentContact = correspondentContactMapper.toEntity(correspondentContactDTO);
        correspondentContact = correspondentContactRepository.save(correspondentContact);
        return correspondentContactMapper.toDto(correspondentContact);
    }

    /**
     * Get all the correspondentContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrespondentContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CorrespondentContacts");
        return correspondentContactRepository.findAll(pageable)
            .map(correspondentContactMapper::toDto);
    }


    /**
     * Get one correspondentContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrespondentContactDTO> findOne(Long id) {
        log.debug("Request to get CorrespondentContact : {}", id);
        return correspondentContactRepository.findById(id)
            .map(correspondentContactMapper::toDto);
    }

    /**
     * Delete the correspondentContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CorrespondentContact : {}", id);
        correspondentContactRepository.deleteById(id);
    }
}
