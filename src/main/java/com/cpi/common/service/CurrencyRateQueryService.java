package com.cpi.common.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.common.domain.CurrencyRate;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.CurrencyRateRepository;
import com.cpi.common.service.dto.CurrencyRateCriteria;

import com.cpi.common.service.dto.CurrencyRateDTO;
import com.cpi.common.service.mapper.CurrencyRateMapper;

/**
 * Service for executing complex queries for CurrencyRate entities in the database.
 * The main input is a {@link CurrencyRateCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyRateDTO} or a {@link Page} of {@link CurrencyRateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyRateQueryService extends QueryService<CurrencyRate> {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateQueryService.class);


    private final CurrencyRateRepository currencyRateRepository;

    private final CurrencyRateMapper currencyRateMapper;

    public CurrencyRateQueryService(CurrencyRateRepository currencyRateRepository, CurrencyRateMapper currencyRateMapper) {
        this.currencyRateRepository = currencyRateRepository;
        this.currencyRateMapper = currencyRateMapper;
    }

    /**
     * Return a {@link List} of {@link CurrencyRateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyRateDTO> findByCriteria(CurrencyRateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CurrencyRate> specification = createSpecification(criteria);
        return currencyRateMapper.toDto(currencyRateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyRateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyRateDTO> findByCriteria(CurrencyRateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CurrencyRate> specification = createSpecification(criteria);
        final Page<CurrencyRate> result = currencyRateRepository.findAll(specification, page);
        return result.map(currencyRateMapper::toDto);
    }

    /**
     * Function to convert CurrencyRateCriteria to a {@link Specifications}
     */
    private Specifications<CurrencyRate> createSpecification(CurrencyRateCriteria criteria) {
        Specifications<CurrencyRate> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CurrencyRate_.id));
            }
            if (criteria.getRateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRateDate(), CurrencyRate_.rateDate));
            }
            if (criteria.getCurrencyRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencyRate(), CurrencyRate_.currencyRate));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCurrencyId(), CurrencyRate_.currency, Currency_.id));
            }
        }
        return specification;
    }

}
