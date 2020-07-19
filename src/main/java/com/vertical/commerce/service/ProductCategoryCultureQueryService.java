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

import com.vertical.commerce.domain.ProductCategoryCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductCategoryCultureRepository;
import com.vertical.commerce.service.dto.ProductCategoryCultureCriteria;
import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;
import com.vertical.commerce.service.mapper.ProductCategoryCultureMapper;

/**
 * Service for executing complex queries for {@link ProductCategoryCulture} entities in the database.
 * The main input is a {@link ProductCategoryCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductCategoryCultureDTO} or a {@link Page} of {@link ProductCategoryCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryCultureQueryService extends QueryService<ProductCategoryCulture> {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryCultureQueryService.class);

    private final ProductCategoryCultureRepository productCategoryCultureRepository;

    private final ProductCategoryCultureMapper productCategoryCultureMapper;

    public ProductCategoryCultureQueryService(ProductCategoryCultureRepository productCategoryCultureRepository, ProductCategoryCultureMapper productCategoryCultureMapper) {
        this.productCategoryCultureRepository = productCategoryCultureRepository;
        this.productCategoryCultureMapper = productCategoryCultureMapper;
    }

    /**
     * Return a {@link List} of {@link ProductCategoryCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductCategoryCultureDTO> findByCriteria(ProductCategoryCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductCategoryCulture> specification = createSpecification(criteria);
        return productCategoryCultureMapper.toDto(productCategoryCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductCategoryCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryCultureDTO> findByCriteria(ProductCategoryCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductCategoryCulture> specification = createSpecification(criteria);
        return productCategoryCultureRepository.findAll(specification, page)
            .map(productCategoryCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCategoryCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductCategoryCulture> specification = createSpecification(criteria);
        return productCategoryCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCategoryCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductCategoryCulture> createSpecification(ProductCategoryCultureCriteria criteria) {
        Specification<ProductCategoryCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductCategoryCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductCategoryCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductCategoryCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductCategoryCulture_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
