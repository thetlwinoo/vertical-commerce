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

import com.vertical.commerce.domain.PaymentMethods;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.PaymentMethodsRepository;
import com.vertical.commerce.service.dto.PaymentMethodsCriteria;
import com.vertical.commerce.service.dto.PaymentMethodsDTO;
import com.vertical.commerce.service.mapper.PaymentMethodsMapper;

/**
 * Service for executing complex queries for {@link PaymentMethods} entities in the database.
 * The main input is a {@link PaymentMethodsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentMethodsDTO} or a {@link Page} of {@link PaymentMethodsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentMethodsQueryService extends QueryService<PaymentMethods> {

    private final Logger log = LoggerFactory.getLogger(PaymentMethodsQueryService.class);

    private final PaymentMethodsRepository paymentMethodsRepository;

    private final PaymentMethodsMapper paymentMethodsMapper;

    public PaymentMethodsQueryService(PaymentMethodsRepository paymentMethodsRepository, PaymentMethodsMapper paymentMethodsMapper) {
        this.paymentMethodsRepository = paymentMethodsRepository;
        this.paymentMethodsMapper = paymentMethodsMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentMethodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentMethodsDTO> findByCriteria(PaymentMethodsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentMethods> specification = createSpecification(criteria);
        return paymentMethodsMapper.toDto(paymentMethodsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentMethodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentMethodsDTO> findByCriteria(PaymentMethodsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentMethods> specification = createSpecification(criteria);
        return paymentMethodsRepository.findAll(specification, page)
            .map(paymentMethodsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentMethodsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentMethods> specification = createSpecification(criteria);
        return paymentMethodsRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentMethodsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentMethods> createSpecification(PaymentMethodsCriteria criteria) {
        Specification<PaymentMethods> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentMethods_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PaymentMethods_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), PaymentMethods_.code));
            }
            if (criteria.getDisabled() != null) {
                specification = specification.and(buildSpecification(criteria.getDisabled(), PaymentMethods_.disabled));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), PaymentMethods_.sortOrder));
            }
            if (criteria.getIconFont() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconFont(), PaymentMethods_.iconFont));
            }
            if (criteria.getIconPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconPhoto(), PaymentMethods_.iconPhoto));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), PaymentMethods_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), PaymentMethods_.validTo));
            }
        }
        return specification;
    }
}
