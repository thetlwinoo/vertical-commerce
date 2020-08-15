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

import com.vertical.commerce.domain.ProductsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductsLocalizeRepository;
import com.vertical.commerce.service.dto.ProductsLocalizeCriteria;
import com.vertical.commerce.service.dto.ProductsLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductsLocalizeMapper;

/**
 * Service for executing complex queries for {@link ProductsLocalize} entities in the database.
 * The main input is a {@link ProductsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductsLocalizeDTO} or a {@link Page} of {@link ProductsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductsLocalizeQueryService extends QueryService<ProductsLocalize> {

    private final Logger log = LoggerFactory.getLogger(ProductsLocalizeQueryService.class);

    private final ProductsLocalizeRepository productsLocalizeRepository;

    private final ProductsLocalizeMapper productsLocalizeMapper;

    public ProductsLocalizeQueryService(ProductsLocalizeRepository productsLocalizeRepository, ProductsLocalizeMapper productsLocalizeMapper) {
        this.productsLocalizeRepository = productsLocalizeRepository;
        this.productsLocalizeMapper = productsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link ProductsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductsLocalizeDTO> findByCriteria(ProductsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductsLocalize> specification = createSpecification(criteria);
        return productsLocalizeMapper.toDto(productsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductsLocalizeDTO> findByCriteria(ProductsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductsLocalize> specification = createSpecification(criteria);
        return productsLocalizeRepository.findAll(specification, page)
            .map(productsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductsLocalize> specification = createSpecification(criteria);
        return productsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductsLocalize> createSpecification(ProductsLocalizeCriteria criteria) {
        Specification<ProductsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductsLocalize_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
