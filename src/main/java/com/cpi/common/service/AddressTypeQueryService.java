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

package com.cpi.common.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.common.domain.AddressType;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.AddressTypeRepository;
import com.cpi.common.service.dto.AddressTypeCriteria;
import com.cpi.common.service.dto.AddressTypeDTO;
import com.cpi.common.service.mapper.AddressTypeMapper;

/**
 * Service for executing complex queries for AddressType entities in the database.
 * The main input is a {@link AddressTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressTypeDTO} or a {@link Page} of {@link AddressTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressTypeQueryService extends QueryService<AddressType> {

    private final Logger log = LoggerFactory.getLogger(AddressTypeQueryService.class);

    private final AddressTypeRepository addressTypeRepository;

    private final AddressTypeMapper addressTypeMapper;

    public AddressTypeQueryService(AddressTypeRepository addressTypeRepository, AddressTypeMapper addressTypeMapper) {
        this.addressTypeRepository = addressTypeRepository;
        this.addressTypeMapper = addressTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AddressTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressTypeDTO> findByCriteria(AddressTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AddressType> specification = createSpecification(criteria);
        return addressTypeMapper.toDto(addressTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressTypeDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressTypeDTO> findByCriteria(AddressTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AddressType> specification = createSpecification(criteria);
        return addressTypeRepository.findAll(specification, page)
            .map(addressTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AddressType> specification = createSpecification(criteria);
        return addressTypeRepository.count(specification);
    }

    /**
     * Function to convert AddressTypeCriteria to a {@link Specification}
     */
    private Specification<AddressType> createSpecification(AddressTypeCriteria criteria) {
        Specification<AddressType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AddressType_.id));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), AddressType_.sortNum));
            }
            if (criteria.getAddressTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressTypeName(), AddressType_.addressTypeName));
            }
            if (criteria.getAddressTypeNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressTypeNameChinese(), AddressType_.addressTypeNameChinese));
            }
        }
        return specification;
    }
}
