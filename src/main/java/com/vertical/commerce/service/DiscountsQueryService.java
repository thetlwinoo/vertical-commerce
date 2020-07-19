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

import com.vertical.commerce.domain.Discounts;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DiscountsRepository;
import com.vertical.commerce.service.dto.DiscountsCriteria;
import com.vertical.commerce.service.dto.DiscountsDTO;
import com.vertical.commerce.service.mapper.DiscountsMapper;

/**
 * Service for executing complex queries for {@link Discounts} entities in the database.
 * The main input is a {@link DiscountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiscountsDTO} or a {@link Page} of {@link DiscountsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscountsQueryService extends QueryService<Discounts> {

    private final Logger log = LoggerFactory.getLogger(DiscountsQueryService.class);

    private final DiscountsRepository discountsRepository;

    private final DiscountsMapper discountsMapper;

    public DiscountsQueryService(DiscountsRepository discountsRepository, DiscountsMapper discountsMapper) {
        this.discountsRepository = discountsRepository;
        this.discountsMapper = discountsMapper;
    }

    /**
     * Return a {@link List} of {@link DiscountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountsDTO> findByCriteria(DiscountsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Discounts> specification = createSpecification(criteria);
        return discountsMapper.toDto(discountsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiscountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscountsDTO> findByCriteria(DiscountsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Discounts> specification = createSpecification(criteria);
        return discountsRepository.findAll(specification, page)
            .map(discountsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscountsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Discounts> specification = createSpecification(criteria);
        return discountsRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Discounts> createSpecification(DiscountsCriteria criteria) {
        Specification<Discounts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Discounts_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Discounts_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Discounts_.description));
            }
            if (criteria.getDiscountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscountCode(), Discounts_.discountCode));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Discounts_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Discounts_.validTo));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Discounts_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getDiscountTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDiscountTypeId(),
                    root -> root.join(Discounts_.discountType, JoinType.LEFT).get(DiscountTypes_.id)));
            }
        }
        return specification;
    }
}
