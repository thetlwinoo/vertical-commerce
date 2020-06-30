package com.vertical.commerce.service;

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

import com.vertical.commerce.domain.Tax;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TaxRepository;
import com.vertical.commerce.service.dto.TaxCriteria;
import com.vertical.commerce.service.dto.TaxDTO;
import com.vertical.commerce.service.mapper.TaxMapper;

/**
 * Service for executing complex queries for {@link Tax} entities in the database.
 * The main input is a {@link TaxCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxDTO} or a {@link Page} of {@link TaxDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxQueryService extends QueryService<Tax> {

    private final Logger log = LoggerFactory.getLogger(TaxQueryService.class);

    private final TaxRepository taxRepository;

    private final TaxMapper taxMapper;

    public TaxQueryService(TaxRepository taxRepository, TaxMapper taxMapper) {
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
    }

    /**
     * Return a {@link List} of {@link TaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxDTO> findByCriteria(TaxCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxMapper.toDto(taxRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxDTO> findByCriteria(TaxCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxRepository.findAll(specification, page)
            .map(taxMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tax> specification = createSpecification(criteria);
        return taxRepository.count(specification);
    }

    /**
     * Function to convert {@link TaxCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tax> createSpecification(TaxCriteria criteria) {
        Specification<Tax> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tax_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Tax_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Tax_.name));
            }
            if (criteria.getRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRate(), Tax_.rate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Tax_.modifiedDate));
            }
            if (criteria.getTaxClassId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaxClassId(),
                    root -> root.join(Tax_.taxClass, JoinType.LEFT).get(TaxClass_.id)));
            }
        }
        return specification;
    }
}
