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

import com.cpi.common.service.CompanyNameHistoryService;
import com.cpi.common.domain.CompanyNameHistory;
import com.cpi.common.repository.CompanyNameHistoryRepository;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.mapper.CompanyNameHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CompanyNameHistory.
 */
@Service
@Transactional
public class CompanyNameHistoryServiceImpl implements CompanyNameHistoryService {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryServiceImpl.class);

    private final CompanyNameHistoryRepository companyNameHistoryRepository;

    private final CompanyNameHistoryMapper companyNameHistoryMapper;

    public CompanyNameHistoryServiceImpl(CompanyNameHistoryRepository companyNameHistoryRepository, CompanyNameHistoryMapper companyNameHistoryMapper) {
        this.companyNameHistoryRepository = companyNameHistoryRepository;
        this.companyNameHistoryMapper = companyNameHistoryMapper;
    }

    /**
     * Save a companyNameHistory.
     *
     * @param companyNameHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyNameHistoryDTO save(CompanyNameHistoryDTO companyNameHistoryDTO) {
        log.debug("Request to save CompanyNameHistory : {}", companyNameHistoryDTO);

        CompanyNameHistory companyNameHistory = companyNameHistoryMapper.toEntity(companyNameHistoryDTO);
        companyNameHistory = companyNameHistoryRepository.save(companyNameHistory);
        return companyNameHistoryMapper.toDto(companyNameHistory);
    }

    /**
     * Get all the companyNameHistories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyNameHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyNameHistories");
        return companyNameHistoryRepository.findAll(pageable)
            .map(companyNameHistoryMapper::toDto);
    }


    /**
     * Get one companyNameHistory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyNameHistoryDTO> findOne(Long id) {
        log.debug("Request to get CompanyNameHistory : {}", id);
        return companyNameHistoryRepository.findById(id)
            .map(companyNameHistoryMapper::toDto);
    }

    /**
     * Delete the companyNameHistory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyNameHistory : {}", id);
        companyNameHistoryRepository.deleteById(id);
    }
}
