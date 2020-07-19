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

import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductBrandRepository;
import com.vertical.commerce.service.dto.ProductBrandCriteria;
import com.vertical.commerce.service.dto.ProductBrandDTO;
import com.vertical.commerce.service.mapper.ProductBrandMapper;

/**
 * Service for executing complex queries for {@link ProductBrand} entities in the database.
 * The main input is a {@link ProductBrandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductBrandDTO} or a {@link Page} of {@link ProductBrandDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductBrandQueryService extends QueryService<ProductBrand> {

    private final Logger log = LoggerFactory.getLogger(ProductBrandQueryService.class);

    private final ProductBrandRepository productBrandRepository;

    private final ProductBrandMapper productBrandMapper;

    public ProductBrandQueryService(ProductBrandRepository productBrandRepository, ProductBrandMapper productBrandMapper) {
        this.productBrandRepository = productBrandRepository;
        this.productBrandMapper = productBrandMapper;
    }

    /**
     * Return a {@link List} of {@link ProductBrandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductBrandDTO> findByCriteria(ProductBrandCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductBrand> specification = createSpecification(criteria);
        return productBrandMapper.toDto(productBrandRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductBrandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductBrandDTO> findByCriteria(ProductBrandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductBrand> specification = createSpecification(criteria);
        return productBrandRepository.findAll(specification, page)
            .map(productBrandMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductBrandCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductBrand> specification = createSpecification(criteria);
        return productBrandRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductBrandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductBrand> createSpecification(ProductBrandCriteria criteria) {
        Specification<ProductBrand> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductBrand_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductBrand_.name));
            }
            if (criteria.getShortLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortLabel(), ProductBrand_.shortLabel));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), ProductBrand_.sortOrder));
            }
            if (criteria.getIconFont() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconFont(), ProductBrand_.iconFont));
            }
            if (criteria.getIconPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconPhoto(), ProductBrand_.iconPhoto));
            }
            if (criteria.getActiveFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveFlag(), ProductBrand_.activeFlag));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), ProductBrand_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), ProductBrand_.validTo));
            }
        }
        return specification;
    }
}
