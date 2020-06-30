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

import com.vertical.commerce.domain.TaxClass;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TaxClassRepository;
import com.vertical.commerce.service.dto.TaxClassCriteria;
import com.vertical.commerce.service.dto.TaxClassDTO;
import com.vertical.commerce.service.mapper.TaxClassMapper;

/**
 * Service for executing complex queries for {@link TaxClass} entities in the database.
 * The main input is a {@link TaxClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TaxClassDTO} or a {@link Page} of {@link TaxClassDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaxClassQueryService extends QueryService<TaxClass> {

    private final Logger log = LoggerFactory.getLogger(TaxClassQueryService.class);

    private final TaxClassRepository taxClassRepository;

    private final TaxClassMapper taxClassMapper;

    public TaxClassQueryService(TaxClassRepository taxClassRepository, TaxClassMapper taxClassMapper) {
        this.taxClassRepository = taxClassRepository;
        this.taxClassMapper = taxClassMapper;
    }

    /**
     * Return a {@link List} of {@link TaxClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaxClassDTO> findByCriteria(TaxClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaxClass> specification = createSpecification(criteria);
        return taxClassMapper.toDto(taxClassRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaxClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaxClassDTO> findByCriteria(TaxClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaxClass> specification = createSpecification(criteria);
        return taxClassRepository.findAll(specification, page)
            .map(taxClassMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaxClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaxClass> specification = createSpecification(criteria);
        return taxClassRepository.count(specification);
    }

    /**
     * Function to convert {@link TaxClassCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaxClass> createSpecification(TaxClassCriteria criteria) {
        Specification<TaxClass> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaxClass_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TaxClass_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TaxClass_.name));
            }
        }
        return specification;
    }
}
