/*
 * Copyright (c)  2015-2018, All rights Reserved, Designed By Kang Biao
 * Email: alex.kangbiao@gmail.com
 * Create by Alex Kang on 18-12-20 上午10:06
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

import com.cpi.common.domain.CompanyNameHistory;
import com.cpi.common.domain.CompanyNameHistory_;
import com.cpi.common.domain.Company_;
import com.cpi.common.repository.CompanyNameHistoryRepository;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.mapper.CompanyNameHistoryMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class CompanyNameHistoryExtService extends QueryService<CompanyNameHistory> {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryExtService.class);

    private final CompanyNameHistoryRepository companyNameHistoryRepository;

    private final CompanyNameHistoryMapper companyNameHistoryMapper;

    public CompanyNameHistoryExtService(CompanyNameHistoryRepository companyNameHistoryRepository, CompanyNameHistoryMapper companyNameHistoryMapper) {
        this.companyNameHistoryRepository = companyNameHistoryRepository;
        this.companyNameHistoryMapper = companyNameHistoryMapper;
    }


    @Transactional(readOnly = true)
    public List<CompanyNameHistoryDTO> findAllByCompanyId(Long companyId) {
        log.debug("find findAll By CompanyId : {}", companyId);
        return companyNameHistoryMapper.toDto(companyNameHistoryRepository.findAllByCompanyIdOrderByEndDateAsc(companyId));
    }

    @Transactional(readOnly = true)
    public CompanyNameHistoryDTO findFitCompanyNameByCompanyId(Long companyId, Instant specialDate) {
        log.debug("find findAll By CompanyId : {}", companyId);
        return companyNameHistoryMapper.toDto(companyNameHistoryRepository.findFirstByCompanyIdAndEndDateAfterOrderByEndDateAsc(companyId, specialDate));
    }


}
