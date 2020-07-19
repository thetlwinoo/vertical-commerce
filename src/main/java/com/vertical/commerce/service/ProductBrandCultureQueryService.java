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

import com.vertical.commerce.domain.ProductBrandCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductBrandCultureRepository;
import com.vertical.commerce.service.dto.ProductBrandCultureCriteria;
import com.vertical.commerce.service.dto.ProductBrandCultureDTO;
import com.vertical.commerce.service.mapper.ProductBrandCultureMapper;

/**
 * Service for executing complex queries for {@link ProductBrandCulture} entities in the database.
 * The main input is a {@link ProductBrandCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductBrandCultureDTO} or a {@link Page} of {@link ProductBrandCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductBrandCultureQueryService extends QueryService<ProductBrandCulture> {

    private final Logger log = LoggerFactory.getLogger(ProductBrandCultureQueryService.class);

    private final ProductBrandCultureRepository productBrandCultureRepository;

    private final ProductBrandCultureMapper productBrandCultureMapper;

    public ProductBrandCultureQueryService(ProductBrandCultureRepository productBrandCultureRepository, ProductBrandCultureMapper productBrandCultureMapper) {
        this.productBrandCultureRepository = productBrandCultureRepository;
        this.productBrandCultureMapper = productBrandCultureMapper;
    }

    /**
     * Return a {@link List} of {@link ProductBrandCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductBrandCultureDTO> findByCriteria(ProductBrandCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductBrandCulture> specification = createSpecification(criteria);
        return productBrandCultureMapper.toDto(productBrandCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductBrandCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductBrandCultureDTO> findByCriteria(ProductBrandCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductBrandCulture> specification = createSpecification(criteria);
        return productBrandCultureRepository.findAll(specification, page)
            .map(productBrandCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductBrandCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductBrandCulture> specification = createSpecification(criteria);
        return productBrandCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductBrandCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductBrandCulture> createSpecification(ProductBrandCultureCriteria criteria) {
        Specification<ProductBrandCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductBrandCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductBrandCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductBrandCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductBrandId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductBrandId(),
                    root -> root.join(ProductBrandCulture_.productBrand, JoinType.LEFT).get(ProductBrand_.id)));
            }
        }
        return specification;
    }
}
