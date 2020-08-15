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

import com.vertical.commerce.domain.ProductBrandLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductBrandLocalizeRepository;
import com.vertical.commerce.service.dto.ProductBrandLocalizeCriteria;
import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductBrandLocalizeMapper;

/**
 * Service for executing complex queries for {@link ProductBrandLocalize} entities in the database.
 * The main input is a {@link ProductBrandLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductBrandLocalizeDTO} or a {@link Page} of {@link ProductBrandLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductBrandLocalizeQueryService extends QueryService<ProductBrandLocalize> {

    private final Logger log = LoggerFactory.getLogger(ProductBrandLocalizeQueryService.class);

    private final ProductBrandLocalizeRepository productBrandLocalizeRepository;

    private final ProductBrandLocalizeMapper productBrandLocalizeMapper;

    public ProductBrandLocalizeQueryService(ProductBrandLocalizeRepository productBrandLocalizeRepository, ProductBrandLocalizeMapper productBrandLocalizeMapper) {
        this.productBrandLocalizeRepository = productBrandLocalizeRepository;
        this.productBrandLocalizeMapper = productBrandLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link ProductBrandLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductBrandLocalizeDTO> findByCriteria(ProductBrandLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductBrandLocalize> specification = createSpecification(criteria);
        return productBrandLocalizeMapper.toDto(productBrandLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductBrandLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductBrandLocalizeDTO> findByCriteria(ProductBrandLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductBrandLocalize> specification = createSpecification(criteria);
        return productBrandLocalizeRepository.findAll(specification, page)
            .map(productBrandLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductBrandLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductBrandLocalize> specification = createSpecification(criteria);
        return productBrandLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductBrandLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductBrandLocalize> createSpecification(ProductBrandLocalizeCriteria criteria) {
        Specification<ProductBrandLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductBrandLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductBrandLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductBrandLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductBrandId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductBrandId(),
                    root -> root.join(ProductBrandLocalize_.productBrand, JoinType.LEFT).get(ProductBrand_.id)));
            }
        }
        return specification;
    }
}
