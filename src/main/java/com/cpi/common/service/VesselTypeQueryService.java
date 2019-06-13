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

import com.cpi.common.domain.VesselType;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.VesselTypeRepository;
import com.cpi.common.service.dto.VesselTypeCriteria;
import com.cpi.common.service.dto.VesselTypeDTO;
import com.cpi.common.service.mapper.VesselTypeMapper;

/**
 * Service for executing complex queries for {@link VesselType} entities in the database.
 * The main input is a {@link VesselTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VesselTypeDTO} or a {@link Page} of {@link VesselTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VesselTypeQueryService extends QueryService<VesselType> {

    private final Logger log = LoggerFactory.getLogger(VesselTypeQueryService.class);

    private final VesselTypeRepository vesselTypeRepository;

    private final VesselTypeMapper vesselTypeMapper;

    public VesselTypeQueryService(VesselTypeRepository vesselTypeRepository, VesselTypeMapper vesselTypeMapper) {
        this.vesselTypeRepository = vesselTypeRepository;
        this.vesselTypeMapper = vesselTypeMapper;
    }

    /**
     * Return a {@link List} of {@link VesselTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VesselTypeDTO> findByCriteria(VesselTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VesselType> specification = createSpecification(criteria);
        return vesselTypeMapper.toDto(vesselTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VesselTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VesselTypeDTO> findByCriteria(VesselTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VesselType> specification = createSpecification(criteria);
        return vesselTypeRepository.findAll(specification, page)
            .map(vesselTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VesselTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VesselType> specification = createSpecification(criteria);
        return vesselTypeRepository.count(specification);
    }

    /**
     * Function to convert VesselTypeCriteria to a {@link Specification}.
     */
    private Specification<VesselType> createSpecification(VesselTypeCriteria criteria) {
        Specification<VesselType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VesselType_.id));
            }
            if (criteria.getVesselTypeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselTypeName(), VesselType_.vesselTypeName));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), VesselType_.sortNum));
            }
        }
        return specification;
    }
}
