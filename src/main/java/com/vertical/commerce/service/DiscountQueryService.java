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

import com.vertical.commerce.domain.Discount;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DiscountRepository;
import com.vertical.commerce.service.dto.DiscountCriteria;
import com.vertical.commerce.service.dto.DiscountDTO;
import com.vertical.commerce.service.mapper.DiscountMapper;

/**
 * Service for executing complex queries for {@link Discount} entities in the database.
 * The main input is a {@link DiscountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiscountDTO} or a {@link Page} of {@link DiscountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscountQueryService extends QueryService<Discount> {

    private final Logger log = LoggerFactory.getLogger(DiscountQueryService.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    public DiscountQueryService(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    /**
     * Return a {@link List} of {@link DiscountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountDTO> findByCriteria(DiscountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Discount> specification = createSpecification(criteria);
        return discountMapper.toDto(discountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiscountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscountDTO> findByCriteria(DiscountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Discount> specification = createSpecification(criteria);
        return discountRepository.findAll(specification, page)
            .map(discountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Discount> specification = createSpecification(criteria);
        return discountRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Discount> createSpecification(DiscountCriteria criteria) {
        Specification<Discount> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Discount_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Discount_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Discount_.description));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Discount_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Discount_.validTo));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Discount_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getDiscountTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDiscountTypeId(),
                    root -> root.join(Discount_.discountType, JoinType.LEFT).get(DiscountTypes_.id)));
            }
        }
        return specification;
    }
}
