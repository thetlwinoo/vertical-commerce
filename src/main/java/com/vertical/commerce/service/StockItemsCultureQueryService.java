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

import com.vertical.commerce.domain.StockItemsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.StockItemsCultureRepository;
import com.vertical.commerce.service.dto.StockItemsCultureCriteria;
import com.vertical.commerce.service.dto.StockItemsCultureDTO;
import com.vertical.commerce.service.mapper.StockItemsCultureMapper;

/**
 * Service for executing complex queries for {@link StockItemsCulture} entities in the database.
 * The main input is a {@link StockItemsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemsCultureDTO} or a {@link Page} of {@link StockItemsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemsCultureQueryService extends QueryService<StockItemsCulture> {

    private final Logger log = LoggerFactory.getLogger(StockItemsCultureQueryService.class);

    private final StockItemsCultureRepository stockItemsCultureRepository;

    private final StockItemsCultureMapper stockItemsCultureMapper;

    public StockItemsCultureQueryService(StockItemsCultureRepository stockItemsCultureRepository, StockItemsCultureMapper stockItemsCultureMapper) {
        this.stockItemsCultureRepository = stockItemsCultureRepository;
        this.stockItemsCultureMapper = stockItemsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemsCultureDTO> findByCriteria(StockItemsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItemsCulture> specification = createSpecification(criteria);
        return stockItemsCultureMapper.toDto(stockItemsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemsCultureDTO> findByCriteria(StockItemsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItemsCulture> specification = createSpecification(criteria);
        return stockItemsCultureRepository.findAll(specification, page)
            .map(stockItemsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItemsCulture> specification = createSpecification(criteria);
        return stockItemsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItemsCulture> createSpecification(StockItemsCultureCriteria criteria) {
        Specification<StockItemsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItemsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StockItemsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(StockItemsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(StockItemsCulture_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}
