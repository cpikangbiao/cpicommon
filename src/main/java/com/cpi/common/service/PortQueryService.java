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

import com.cpi.common.domain.Port;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.PortRepository;
import com.cpi.common.service.dto.PortCriteria;

import com.cpi.common.service.dto.PortDTO;
import com.cpi.common.service.mapper.PortMapper;

/**
 * Service for executing complex queries for Port entities in the database.
 * The main input is a {@link PortCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PortDTO} or a {@link Page} of {@link PortDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PortQueryService extends QueryService<Port> {

    private final Logger log = LoggerFactory.getLogger(PortQueryService.class);


    private final PortRepository portRepository;

    private final PortMapper portMapper;

    public PortQueryService(PortRepository portRepository, PortMapper portMapper) {
        this.portRepository = portRepository;
        this.portMapper = portMapper;
    }

    /**
     * Return a {@link List} of {@link PortDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PortDTO> findByCriteria(PortCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Port> specification = createSpecification(criteria);
        return portMapper.toDto(portRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PortDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PortDTO> findByCriteria(PortCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Port> specification = createSpecification(criteria);
        final Page<Port> result = portRepository.findAll(specification, page);
        return result.map(portMapper::toDto);
    }

    /**
     * Function to convert PortCriteria to a {@link Specifications}
     */
    private Specifications<Port> createSpecification(PortCriteria criteria) {
        Specifications<Port> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Port_.id));
            }
            if (criteria.getPortCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortCode(), Port_.portCode));
            }
            if (criteria.getPortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortName(), Port_.portName));
            }
            if (criteria.getPortNameChinese() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortNameChinese(), Port_.portNameChinese));
            }
            if (criteria.getCorrespondentsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCorrespondentsId(), Port_.correspondents, Correspondent_.id));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCountryId(), Port_.country, Country_.id));
            }
        }
        return specification;
    }

}
