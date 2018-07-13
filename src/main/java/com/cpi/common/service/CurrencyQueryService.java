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

import com.cpi.common.domain.Currency;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.CurrencyRepository;
import com.cpi.common.service.dto.CurrencyCriteria;

import com.cpi.common.service.dto.CurrencyDTO;
import com.cpi.common.service.mapper.CurrencyMapper;

/**
 * Service for executing complex queries for Currency entities in the database.
 * The main input is a {@link CurrencyCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CurrencyDTO} or a {@link Page} of {@link CurrencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CurrencyQueryService extends QueryService<Currency> {

    private final Logger log = LoggerFactory.getLogger(CurrencyQueryService.class);


    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyQueryService(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    /**
     * Return a {@link List} of {@link CurrencyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CurrencyDTO> findByCriteria(CurrencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Currency> specification = createSpecification(criteria);
        return currencyMapper.toDto(currencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CurrencyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CurrencyDTO> findByCriteria(CurrencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Currency> specification = createSpecification(criteria);
        final Page<Currency> result = currencyRepository.findAll(specification, page);
        return result.map(currencyMapper::toDto);
    }

    /**
     * Function to convert CurrencyCriteria to a {@link Specifications}
     */
    private Specifications<Currency> createSpecification(CurrencyCriteria criteria) {
        Specifications<Currency> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Currency_.id));
            }
            if (criteria.getFullNameEnglish() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullNameEnglish(), Currency_.fullNameEnglish));
            }
            if (criteria.getFullNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullNameChinese(), Currency_.fullNameChinese));
            }
            if (criteria.getCountryNameEnglish() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryNameEnglish(), Currency_.countryNameEnglish));
            }
            if (criteria.getCountryNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryNameChinese(), Currency_.countryNameChinese));
            }
            if (criteria.getNameAbbre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAbbre(), Currency_.nameAbbre));
            }
            if (criteria.getCurrencySort() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrencySort(), Currency_.currencySort));
            }
        }
        return specification;
    }

}
