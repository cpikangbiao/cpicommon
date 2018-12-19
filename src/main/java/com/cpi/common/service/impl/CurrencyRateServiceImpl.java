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

import com.cpi.common.service.CurrencyRateService;
import com.cpi.common.domain.CurrencyRate;
import com.cpi.common.repository.CurrencyRateRepository;
import com.cpi.common.service.dto.CurrencyRateDTO;
import com.cpi.common.service.mapper.CurrencyRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CurrencyRate.
 */
@Service
@Transactional
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateServiceImpl.class);

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyRateServiceImpl(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
    }

    /**
     * Save a currencyRate.
     *
     * @param currencyRateDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CurrencyRateDTO save(CurrencyRateDTO currencyRateDTO) {
        log.debug("Request to save CurrencyRate : {}", currencyRateDTO);

        CurrencyRate currencyRate = currencyRateMapper.toEntity(currencyRateDTO);
        currencyRate = currencyRateRepository.save(currencyRate);
        return currencyRateMapper.toDto(currencyRate);
    }

    /**
     * Get all the currencyRates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurrencyRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurrencyRates");
        return currencyRateRepository.findAll(pageable)
            .map(currencyRateMapper::toDto);
    }


    /**
     * Get one currencyRate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrencyRateDTO> findOne(Long id) {
        log.debug("Request to get CurrencyRate : {}", id);
        return currencyRateRepository.findById(id)
            .map(currencyRateMapper::toDto);
    }

    /**
     * Delete the currencyRate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrencyRate : {}", id);
        currencyRateRepository.deleteById(id);
    }
}
