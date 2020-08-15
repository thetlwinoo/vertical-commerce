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

import com.vertical.commerce.domain.StockItemsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.StockItemsLocalizeRepository;
import com.vertical.commerce.service.dto.StockItemsLocalizeCriteria;
import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;
import com.vertical.commerce.service.mapper.StockItemsLocalizeMapper;

/**
 * Service for executing complex queries for {@link StockItemsLocalize} entities in the database.
 * The main input is a {@link StockItemsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StockItemsLocalizeDTO} or a {@link Page} of {@link StockItemsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StockItemsLocalizeQueryService extends QueryService<StockItemsLocalize> {

    private final Logger log = LoggerFactory.getLogger(StockItemsLocalizeQueryService.class);

    private final StockItemsLocalizeRepository stockItemsLocalizeRepository;

    private final StockItemsLocalizeMapper stockItemsLocalizeMapper;

    public StockItemsLocalizeQueryService(StockItemsLocalizeRepository stockItemsLocalizeRepository, StockItemsLocalizeMapper stockItemsLocalizeMapper) {
        this.stockItemsLocalizeRepository = stockItemsLocalizeRepository;
        this.stockItemsLocalizeMapper = stockItemsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link StockItemsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StockItemsLocalizeDTO> findByCriteria(StockItemsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StockItemsLocalize> specification = createSpecification(criteria);
        return stockItemsLocalizeMapper.toDto(stockItemsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StockItemsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StockItemsLocalizeDTO> findByCriteria(StockItemsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StockItemsLocalize> specification = createSpecification(criteria);
        return stockItemsLocalizeRepository.findAll(specification, page)
            .map(stockItemsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StockItemsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StockItemsLocalize> specification = createSpecification(criteria);
        return stockItemsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link StockItemsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StockItemsLocalize> createSpecification(StockItemsLocalizeCriteria criteria) {
        Specification<StockItemsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StockItemsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StockItemsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(StockItemsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(StockItemsLocalize_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
        }
        return specification;
    }
}
