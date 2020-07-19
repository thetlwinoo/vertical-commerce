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

import com.vertical.commerce.domain.ProductsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductsCultureRepository;
import com.vertical.commerce.service.dto.ProductsCultureCriteria;
import com.vertical.commerce.service.dto.ProductsCultureDTO;
import com.vertical.commerce.service.mapper.ProductsCultureMapper;

/**
 * Service for executing complex queries for {@link ProductsCulture} entities in the database.
 * The main input is a {@link ProductsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductsCultureDTO} or a {@link Page} of {@link ProductsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductsCultureQueryService extends QueryService<ProductsCulture> {

    private final Logger log = LoggerFactory.getLogger(ProductsCultureQueryService.class);

    private final ProductsCultureRepository productsCultureRepository;

    private final ProductsCultureMapper productsCultureMapper;

    public ProductsCultureQueryService(ProductsCultureRepository productsCultureRepository, ProductsCultureMapper productsCultureMapper) {
        this.productsCultureRepository = productsCultureRepository;
        this.productsCultureMapper = productsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link ProductsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductsCultureDTO> findByCriteria(ProductsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductsCulture> specification = createSpecification(criteria);
        return productsCultureMapper.toDto(productsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsCultureDTO> findByCriteria(ProductsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductsCulture> specification = createSpecification(criteria);
        return productsCultureRepository.findAll(specification, page)
            .map(productsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductsCulture> specification = createSpecification(criteria);
        return productsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductsCulture> createSpecification(ProductsCultureCriteria criteria) {
        Specification<ProductsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductsCulture_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
