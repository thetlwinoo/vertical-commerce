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

import com.vertical.commerce.domain.CompareLines;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CompareLinesRepository;
import com.vertical.commerce.service.dto.CompareLinesCriteria;
import com.vertical.commerce.service.dto.CompareLinesDTO;
import com.vertical.commerce.service.mapper.CompareLinesMapper;

/**
 * Service for executing complex queries for {@link CompareLines} entities in the database.
 * The main input is a {@link CompareLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompareLinesDTO} or a {@link Page} of {@link CompareLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompareLinesQueryService extends QueryService<CompareLines> {

    private final Logger log = LoggerFactory.getLogger(CompareLinesQueryService.class);

    private final CompareLinesRepository compareLinesRepository;

    private final CompareLinesMapper compareLinesMapper;

    public CompareLinesQueryService(CompareLinesRepository compareLinesRepository, CompareLinesMapper compareLinesMapper) {
        this.compareLinesRepository = compareLinesRepository;
        this.compareLinesMapper = compareLinesMapper;
    }

    /**
     * Return a {@link List} of {@link CompareLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompareLinesDTO> findByCriteria(CompareLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompareLines> specification = createSpecification(criteria);
        return compareLinesMapper.toDto(compareLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompareLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompareLinesDTO> findByCriteria(CompareLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompareLines> specification = createSpecification(criteria);
        return compareLinesRepository.findAll(specification, page)
            .map(compareLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompareLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompareLines> specification = createSpecification(criteria);
        return compareLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link CompareLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompareLines> createSpecification(CompareLinesCriteria criteria) {
        Specification<CompareLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompareLines_.id));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(CompareLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getCompareId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareId(),
                    root -> root.join(CompareLines_.compare, JoinType.LEFT).get(Compares_.id)));
            }
        }
        return specification;
    }
}
