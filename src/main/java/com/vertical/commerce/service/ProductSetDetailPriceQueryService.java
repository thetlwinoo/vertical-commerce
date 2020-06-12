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

import com.vertical.commerce.domain.ProductSetDetailPrice;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductSetDetailPriceRepository;
import com.vertical.commerce.service.dto.ProductSetDetailPriceCriteria;
import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;
import com.vertical.commerce.service.mapper.ProductSetDetailPriceMapper;

/**
 * Service for executing complex queries for {@link ProductSetDetailPrice} entities in the database.
 * The main input is a {@link ProductSetDetailPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductSetDetailPriceDTO} or a {@link Page} of {@link ProductSetDetailPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductSetDetailPriceQueryService extends QueryService<ProductSetDetailPrice> {

    private final Logger log = LoggerFactory.getLogger(ProductSetDetailPriceQueryService.class);

    private final ProductSetDetailPriceRepository productSetDetailPriceRepository;

    private final ProductSetDetailPriceMapper productSetDetailPriceMapper;

    public ProductSetDetailPriceQueryService(ProductSetDetailPriceRepository productSetDetailPriceRepository, ProductSetDetailPriceMapper productSetDetailPriceMapper) {
        this.productSetDetailPriceRepository = productSetDetailPriceRepository;
        this.productSetDetailPriceMapper = productSetDetailPriceMapper;
    }

    /**
     * Return a {@link List} of {@link ProductSetDetailPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductSetDetailPriceDTO> findByCriteria(ProductSetDetailPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductSetDetailPrice> specification = createSpecification(criteria);
        return productSetDetailPriceMapper.toDto(productSetDetailPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductSetDetailPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductSetDetailPriceDTO> findByCriteria(ProductSetDetailPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductSetDetailPrice> specification = createSpecification(criteria);
        return productSetDetailPriceRepository.findAll(specification, page)
            .map(productSetDetailPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductSetDetailPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductSetDetailPrice> specification = createSpecification(criteria);
        return productSetDetailPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductSetDetailPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductSetDetailPrice> createSpecification(ProductSetDetailPriceCriteria criteria) {
        Specification<ProductSetDetailPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductSetDetailPrice_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ProductSetDetailPrice_.price));
            }
            if (criteria.getStartCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartCount(), ProductSetDetailPrice_.startCount));
            }
            if (criteria.getEndCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndCount(), ProductSetDetailPrice_.endCount));
            }
            if (criteria.getMultiplyCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMultiplyCount(), ProductSetDetailPrice_.multiplyCount));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ProductSetDetailPrice_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ProductSetDetailPrice_.endDate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), ProductSetDetailPrice_.modifiedDate));
            }
            if (criteria.getProductSetDetailId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductSetDetailId(),
                    root -> root.join(ProductSetDetailPrice_.productSetDetail, JoinType.LEFT).get(ProductSetDetails_.id)));
            }
        }
        return specification;
    }
}
