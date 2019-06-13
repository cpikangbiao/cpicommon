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

import com.cpi.common.domain.Vessel;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.VesselRepository;
import com.cpi.common.service.dto.VesselCriteria;
import com.cpi.common.service.dto.VesselDTO;
import com.cpi.common.service.mapper.VesselMapper;

/**
 * Service for executing complex queries for {@link Vessel} entities in the database.
 * The main input is a {@link VesselCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VesselDTO} or a {@link Page} of {@link VesselDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VesselQueryService extends QueryService<Vessel> {

    private final Logger log = LoggerFactory.getLogger(VesselQueryService.class);

    private final VesselRepository vesselRepository;

    private final VesselMapper vesselMapper;

    public VesselQueryService(VesselRepository vesselRepository, VesselMapper vesselMapper) {
        this.vesselRepository = vesselRepository;
        this.vesselMapper = vesselMapper;
    }

    /**
     * Return a {@link List} of {@link VesselDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VesselDTO> findByCriteria(VesselCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vessel> specification = createSpecification(criteria);
        return vesselMapper.toDto(vesselRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VesselDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VesselDTO> findByCriteria(VesselCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vessel> specification = createSpecification(criteria);
        return vesselRepository.findAll(specification, page)
            .map(vesselMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VesselCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vessel> specification = createSpecification(criteria);
        return vesselRepository.count(specification);
    }

    /**
     * Function to convert VesselCriteria to a {@link Specification}.
     */
    private Specification<Vessel> createSpecification(VesselCriteria criteria) {
        Specification<Vessel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Vessel_.id));
            }
            if (criteria.getVesselCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselCode(), Vessel_.vesselCode));
            }
            if (criteria.getVesselName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselName(), Vessel_.vesselName));
            }
            if (criteria.getVesselChineseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselChineseName(), Vessel_.vesselChineseName));
            }
            if (criteria.getGtr() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGtr(), Vessel_.gtr));
            }
            if (criteria.getWrt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWrt(), Vessel_.wrt));
            }
            if (criteria.getBuildLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuildLocation(), Vessel_.buildLocation));
            }
            if (criteria.getBuildYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBuildYear(), Vessel_.buildYear));
            }
            if (criteria.getFoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFoc(), Vessel_.foc));
            }
            if (criteria.getDwt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDwt(), Vessel_.dwt));
            }
            if (criteria.getHullHmAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullHmAmount(), Vessel_.hullHmAmount));
            }
            if (criteria.getHullHmValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullHmValue(), Vessel_.hullHmValue));
            }
            if (criteria.getHullIvAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullIvAmount(), Vessel_.hullIvAmount));
            }
            if (criteria.getHullIvValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullIvValue(), Vessel_.hullIvValue));
            }
            if (criteria.getHullWarAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullWarAmount(), Vessel_.hullWarAmount));
            }
            if (criteria.getHullWarValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHullWarValue(), Vessel_.hullWarValue));
            }
            if (criteria.getVesselSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselSize(), Vessel_.vesselSize));
            }
            if (criteria.getLine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLine(), Vessel_.line));
            }
            if (criteria.getDeeper() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeeper(), Vessel_.deeper));
            }
            if (criteria.getCallSign() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCallSign(), Vessel_.callSign));
            }
            if (criteria.getImoNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImoNumber(), Vessel_.imoNumber));
            }
            if (criteria.getVesselCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getVesselCountryId(),
                    root -> root.join(Vessel_.vesselCountry, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getVesselCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getVesselCurrencyId(),
                    root -> root.join(Vessel_.vesselCurrency, JoinType.LEFT).get(Currency_.id)));
            }
            if (criteria.getVesselTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getVesselTypeId(),
                    root -> root.join(Vessel_.vesselType, JoinType.LEFT).get(VesselType_.id)));
            }
            if (criteria.getVesselOwnerCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getVesselOwnerCompanyId(),
                    root -> root.join(Vessel_.vesselOwnerCompany, JoinType.LEFT).get(Company_.id)));
            }
            if (criteria.getRegistedPortId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegistedPortId(),
                    root -> root.join(Vessel_.registedPort, JoinType.LEFT).get(Port_.id)));
            }
        }
        return specification;
    }
}
