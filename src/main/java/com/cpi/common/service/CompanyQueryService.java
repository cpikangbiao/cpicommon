package com.cpi.common.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.common.domain.Company;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.CompanyRepository;
import com.cpi.common.service.dto.CompanyCriteria;

import com.cpi.common.service.dto.CompanyDTO;
import com.cpi.common.service.mapper.CompanyMapper;

/**
 * Service for executing complex queries for Company entities in the database.
 * The main input is a {@link CompanyCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);


    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Company> specification = createSpecification(criteria);
        final Page<Company> result = companyRepository.findAll(specification, page);
        return result.map(companyMapper::toDto);
    }

    /**
     * Function to convert CompanyCriteria to a {@link Specifications}
     */
    private Specifications<Company> createSpecification(CompanyCriteria criteria) {
        Specifications<Company> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getCompanyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyCode(), Company_.companyCode));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), Company_.companyName));
            }
            if (criteria.getCountryNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryNameChinese(), Company_.countryNameChinese));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), Company_.country, Country_.id));
            }
        }
        return specification;
    }

}
