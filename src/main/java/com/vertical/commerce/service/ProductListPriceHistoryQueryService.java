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

import com.vertical.commerce.domain.ProductListPriceHistory;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductListPriceHistoryRepository;
import com.vertical.commerce.service.dto.ProductListPriceHistoryCriteria;
import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;
import com.vertical.commerce.service.mapper.ProductListPriceHistoryMapper;

/**
 * Service for executing complex queries for {@link ProductListPriceHistory} entities in the database.
 * The main input is a {@link ProductListPriceHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductListPriceHistoryDTO} or a {@link Page} of {@link ProductListPriceHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductListPriceHistoryQueryService extends QueryService<ProductListPriceHistory> {

    private final Logger log = LoggerFactory.getLogger(ProductListPriceHistoryQueryService.class);

    private final ProductListPriceHistoryRepository productListPriceHistoryRepository;

    private final ProductListPriceHistoryMapper productListPriceHistoryMapper;

    public ProductListPriceHistoryQueryService(ProductListPriceHistoryRepository productListPriceHistoryRepository, ProductListPriceHistoryMapper productListPriceHistoryMapper) {
        this.productListPriceHistoryRepository = productListPriceHistoryRepository;
        this.productListPriceHistoryMapper = productListPriceHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link ProductListPriceHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductListPriceHistoryDTO> findByCriteria(ProductListPriceHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductListPriceHistory> specification = createSpecification(criteria);
        return productListPriceHistoryMapper.toDto(productListPriceHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductListPriceHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductListPriceHistoryDTO> findByCriteria(ProductListPriceHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductListPriceHistory> specification = createSpecification(criteria);
        return productListPriceHistoryRepository.findAll(specification, page)
            .map(productListPriceHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductListPriceHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductListPriceHistory> specification = createSpecification(criteria);
        return productListPriceHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductListPriceHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductListPriceHistory> createSpecification(ProductListPriceHistoryCriteria criteria) {
        Specification<ProductListPriceHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductListPriceHistory_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ProductListPriceHistory_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ProductListPriceHistory_.endDate));
            }
            if (criteria.getListPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getListPrice(), ProductListPriceHistory_.listPrice));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), ProductListPriceHistory_.modifiedDate));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductListPriceHistory_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
