/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-18 上午9:42
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

import com.cpi.common.domain.CurrencyRate;
import com.cpi.common.domain.CurrencyRate_;
import com.cpi.common.domain.Currency_;
import com.cpi.common.repository.CurrencyRateRepository;
import com.cpi.common.service.dto.CurrencyRateCriteria;
import com.cpi.common.service.dto.CurrencyRateDTO;
import com.cpi.common.service.mapper.CurrencyRateMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CurrencyRateExtService extends QueryService<CurrencyRate> {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateExtService.class);

    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyRateExtService(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public CurrencyRateDTO findLastByCurrencyId(Long currencyId) {
        log.debug("find Last By By CurrencyId : {}", currencyId);
        CurrencyRate currencyRate = currencyRateRepository.findFirstByCurrencyIdOrderByRateDateDesc(currencyId);
        return currencyRateMapper.toDto(currencyRate);
    }


}
