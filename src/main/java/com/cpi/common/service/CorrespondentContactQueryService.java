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

import com.cpi.common.domain.CorrespondentContact;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.CorrespondentContactRepository;
import com.cpi.common.service.dto.CorrespondentContactCriteria;

import com.cpi.common.service.dto.CorrespondentContactDTO;
import com.cpi.common.service.mapper.CorrespondentContactMapper;

/**
 * Service for executing complex queries for CorrespondentContact entities in the database.
 * The main input is a {@link CorrespondentContactCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrespondentContactDTO} or a {@link Page} of {@link CorrespondentContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrespondentContactQueryService extends QueryService<CorrespondentContact> {

    private final Logger log = LoggerFactory.getLogger(CorrespondentContactQueryService.class);


    private final CorrespondentContactRepository correspondentContactRepository;

    private final CorrespondentContactMapper correspondentContactMapper;

    public CorrespondentContactQueryService(CorrespondentContactRepository correspondentContactRepository, CorrespondentContactMapper correspondentContactMapper) {
        this.correspondentContactRepository = correspondentContactRepository;
        this.correspondentContactMapper = correspondentContactMapper;
    }

    /**
     * Return a {@link List} of {@link CorrespondentContactDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrespondentContactDTO> findByCriteria(CorrespondentContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CorrespondentContact> specification = createSpecification(criteria);
        return correspondentContactMapper.toDto(correspondentContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrespondentContactDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrespondentContactDTO> findByCriteria(CorrespondentContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CorrespondentContact> specification = createSpecification(criteria);
        final Page<CorrespondentContact> result = correspondentContactRepository.findAll(specification, page);
        return result.map(correspondentContactMapper::toDto);
    }

    /**
     * Function to convert CorrespondentContactCriteria to a {@link Specifications}
     */
    private Specifications<CorrespondentContact> createSpecification(CorrespondentContactCriteria criteria) {
        Specifications<CorrespondentContact> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CorrespondentContact_.id));
            }
            if (criteria.getCorrespondentContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorrespondentContactName(), CorrespondentContact_.correspondentContactName));
            }
            if (criteria.getTelephoneOffice() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephoneOffice(), CorrespondentContact_.telephoneOffice));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), CorrespondentContact_.telephone));
            }
            if (criteria.geteMail() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteMail(), CorrespondentContact_.eMail));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), CorrespondentContact_.webSite));
            }
            if (criteria.getCorrespondentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentId(), CorrespondentContact_.correspondent, Correspondent_.id));
            }
        }
        return specification;
    }

}
