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

import com.vertical.commerce.domain.ProductCategoryLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductCategoryLocalizeRepository;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeCriteria;
import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;
import com.vertical.commerce.service.mapper.ProductCategoryLocalizeMapper;

/**
 * Service for executing complex queries for {@link ProductCategoryLocalize} entities in the database.
 * The main input is a {@link ProductCategoryLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductCategoryLocalizeDTO} or a {@link Page} of {@link ProductCategoryLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductCategoryLocalizeQueryService extends QueryService<ProductCategoryLocalize> {

    private final Logger log = LoggerFactory.getLogger(ProductCategoryLocalizeQueryService.class);

    private final ProductCategoryLocalizeRepository productCategoryLocalizeRepository;

    private final ProductCategoryLocalizeMapper productCategoryLocalizeMapper;

    public ProductCategoryLocalizeQueryService(ProductCategoryLocalizeRepository productCategoryLocalizeRepository, ProductCategoryLocalizeMapper productCategoryLocalizeMapper) {
        this.productCategoryLocalizeRepository = productCategoryLocalizeRepository;
        this.productCategoryLocalizeMapper = productCategoryLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link ProductCategoryLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductCategoryLocalizeDTO> findByCriteria(ProductCategoryLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductCategoryLocalize> specification = createSpecification(criteria);
        return productCategoryLocalizeMapper.toDto(productCategoryLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductCategoryLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductCategoryLocalizeDTO> findByCriteria(ProductCategoryLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductCategoryLocalize> specification = createSpecification(criteria);
        return productCategoryLocalizeRepository.findAll(specification, page)
            .map(productCategoryLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCategoryLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductCategoryLocalize> specification = createSpecification(criteria);
        return productCategoryLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCategoryLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductCategoryLocalize> createSpecification(ProductCategoryLocalizeCriteria criteria) {
        Specification<ProductCategoryLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductCategoryLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductCategoryLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductCategoryLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(ProductCategoryLocalize_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
