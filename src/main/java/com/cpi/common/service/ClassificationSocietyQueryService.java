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

import com.cpi.common.domain.ClassificationSociety;
import com.cpi.common.domain.*; // for static metamodels
import com.cpi.common.repository.ClassificationSocietyRepository;
import com.cpi.common.service.dto.ClassificationSocietyCriteria;
import com.cpi.common.service.dto.ClassificationSocietyDTO;
import com.cpi.common.service.mapper.ClassificationSocietyMapper;

/**
 * Service for executing complex queries for ClassificationSociety entities in the database.
 * The main input is a {@link ClassificationSocietyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassificationSocietyDTO} or a {@link Page} of {@link ClassificationSocietyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassificationSocietyQueryService extends QueryService<ClassificationSociety> {

    private final Logger log = LoggerFactory.getLogger(ClassificationSocietyQueryService.class);

    private final ClassificationSocietyRepository classificationSocietyRepository;

    private final ClassificationSocietyMapper classificationSocietyMapper;

    public ClassificationSocietyQueryService(ClassificationSocietyRepository classificationSocietyRepository, ClassificationSocietyMapper classificationSocietyMapper) {
        this.classificationSocietyRepository = classificationSocietyRepository;
        this.classificationSocietyMapper = classificationSocietyMapper;
    }

    /**
     * Return a {@link List} of {@link ClassificationSocietyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassificationSocietyDTO> findByCriteria(ClassificationSocietyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassificationSociety> specification = createSpecification(criteria);
        return classificationSocietyMapper.toDto(classificationSocietyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassificationSocietyDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassificationSocietyDTO> findByCriteria(ClassificationSocietyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassificationSociety> specification = createSpecification(criteria);
        return classificationSocietyRepository.findAll(specification, page)
            .map(classificationSocietyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassificationSocietyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassificationSociety> specification = createSpecification(criteria);
        return classificationSocietyRepository.count(specification);
    }

    /**
     * Function to convert ClassificationSocietyCriteria to a {@link Specification}
     */
    private Specification<ClassificationSociety> createSpecification(ClassificationSocietyCriteria criteria) {
        Specification<ClassificationSociety> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ClassificationSociety_.id));
            }
            if (criteria.getClassificationSocietyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassificationSocietyName(), ClassificationSociety_.classificationSocietyName));
            }
            if (criteria.getClassificationSocietyNameAbbr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassificationSocietyNameAbbr(), ClassificationSociety_.classificationSocietyNameAbbr));
            }
            if (criteria.getSortNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortNum(), ClassificationSociety_.sortNum));
            }
        }
        return specification;
    }
}
