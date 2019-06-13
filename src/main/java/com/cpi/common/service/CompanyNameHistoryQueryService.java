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

import com.cpi.common.domain.CompanyNameHistory;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.CompanyNameHistoryRepository;
import com.cpi.common.service.dto.CompanyNameHistoryCriteria;
import com.cpi.common.service.dto.CompanyNameHistoryDTO;
import com.cpi.common.service.mapper.CompanyNameHistoryMapper;

/**
 * Service for executing complex queries for {@link CompanyNameHistory} entities in the database.
 * The main input is a {@link CompanyNameHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyNameHistoryDTO} or a {@link Page} of {@link CompanyNameHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyNameHistoryQueryService extends QueryService<CompanyNameHistory> {

    private final Logger log = LoggerFactory.getLogger(CompanyNameHistoryQueryService.class);

    private final CompanyNameHistoryRepository companyNameHistoryRepository;

    private final CompanyNameHistoryMapper companyNameHistoryMapper;

    public CompanyNameHistoryQueryService(CompanyNameHistoryRepository companyNameHistoryRepository, CompanyNameHistoryMapper companyNameHistoryMapper) {
        this.companyNameHistoryRepository = companyNameHistoryRepository;
        this.companyNameHistoryMapper = companyNameHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyNameHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyNameHistoryDTO> findByCriteria(CompanyNameHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyNameHistory> specification = createSpecification(criteria);
        return companyNameHistoryMapper.toDto(companyNameHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyNameHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyNameHistoryDTO> findByCriteria(CompanyNameHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyNameHistory> specification = createSpecification(criteria);
        return companyNameHistoryRepository.findAll(specification, page)
            .map(companyNameHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyNameHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyNameHistory> specification = createSpecification(criteria);
        return companyNameHistoryRepository.count(specification);
    }

    /**
     * Function to convert CompanyNameHistoryCriteria to a {@link Specification}.
     */
    private Specification<CompanyNameHistory> createSpecification(CompanyNameHistoryCriteria criteria) {
        Specification<CompanyNameHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CompanyNameHistory_.id));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), CompanyNameHistory_.updateTime));
            }
            if (criteria.getUpdateUser() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateUser(), CompanyNameHistory_.updateUser));
            }
            if (criteria.getCompanyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyName(), CompanyNameHistory_.companyName));
            }
            if (criteria.getCompanyChineseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyChineseName(), CompanyNameHistory_.companyChineseName));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CompanyNameHistory_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), CompanyNameHistory_.endDate));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(CompanyNameHistory_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
