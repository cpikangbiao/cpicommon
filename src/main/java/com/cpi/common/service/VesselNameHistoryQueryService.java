package com.cpi.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.cpi.common.domain.VesselNameHistory;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.VesselNameHistoryRepository;
import com.cpi.common.service.dto.VesselNameHistoryCriteria;

import com.cpi.common.service.dto.VesselNameHistoryDTO;
import com.cpi.common.service.mapper.VesselNameHistoryMapper;

/**
 * Service for executing complex queries for VesselNameHistory entities in the database.
 * The main input is a {@link VesselNameHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VesselNameHistoryDTO} or a {@link Page} of {@link VesselNameHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VesselNameHistoryQueryService extends QueryService<VesselNameHistory> {

    private final Logger log = LoggerFactory.getLogger(VesselNameHistoryQueryService.class);

    private final VesselNameHistoryRepository vesselNameHistoryRepository;

    private final VesselNameHistoryMapper vesselNameHistoryMapper;

    public VesselNameHistoryQueryService(VesselNameHistoryRepository vesselNameHistoryRepository, VesselNameHistoryMapper vesselNameHistoryMapper) {
        this.vesselNameHistoryRepository = vesselNameHistoryRepository;
        this.vesselNameHistoryMapper = vesselNameHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link VesselNameHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VesselNameHistoryDTO> findByCriteria(VesselNameHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VesselNameHistory> specification = createSpecification(criteria);
        return vesselNameHistoryMapper.toDto(vesselNameHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VesselNameHistoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VesselNameHistoryDTO> findByCriteria(VesselNameHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VesselNameHistory> specification = createSpecification(criteria);
        return vesselNameHistoryRepository.findAll(specification, page)
            .map(vesselNameHistoryMapper::toDto);
    }

    /**
     * Function to convert VesselNameHistoryCriteria to a {@link Specification}
     */
    private Specification<VesselNameHistory> createSpecification(VesselNameHistoryCriteria criteria) {
        Specification<VesselNameHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VesselNameHistory_.id));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), VesselNameHistory_.updateTime));
            }
            if (criteria.getUpdateUser() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateUser(), VesselNameHistory_.updateUser));
            }
            if (criteria.getVesselName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselName(), VesselNameHistory_.vesselName));
            }
            if (criteria.getVesselChineseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVesselChineseName(), VesselNameHistory_.vesselChineseName));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), VesselNameHistory_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), VesselNameHistory_.endDate));
            }
            if (criteria.getVesselId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVesselId(), VesselNameHistory_.vessel, Vessel_.id));
            }
        }
        return specification;
    }

}
