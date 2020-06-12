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

import com.vertical.commerce.domain.CustomerPaymentPaypal;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentPaypalRepository;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentPaypalMapper;

/**
 * Service for executing complex queries for {@link CustomerPaymentPaypal} entities in the database.
 * The main input is a {@link CustomerPaymentPaypalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentPaypalDTO} or a {@link Page} of {@link CustomerPaymentPaypalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentPaypalQueryService extends QueryService<CustomerPaymentPaypal> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentPaypalQueryService.class);

    private final CustomerPaymentPaypalRepository customerPaymentPaypalRepository;

    private final CustomerPaymentPaypalMapper customerPaymentPaypalMapper;

    public CustomerPaymentPaypalQueryService(CustomerPaymentPaypalRepository customerPaymentPaypalRepository, CustomerPaymentPaypalMapper customerPaymentPaypalMapper) {
        this.customerPaymentPaypalRepository = customerPaymentPaypalRepository;
        this.customerPaymentPaypalMapper = customerPaymentPaypalMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentPaypalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentPaypalDTO> findByCriteria(CustomerPaymentPaypalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPaymentPaypal> specification = createSpecification(criteria);
        return customerPaymentPaypalMapper.toDto(customerPaymentPaypalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentPaypalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentPaypalDTO> findByCriteria(CustomerPaymentPaypalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPaymentPaypal> specification = createSpecification(criteria);
        return customerPaymentPaypalRepository.findAll(specification, page)
            .map(customerPaymentPaypalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentPaypalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPaymentPaypal> specification = createSpecification(criteria);
        return customerPaymentPaypalRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentPaypalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPaymentPaypal> createSpecification(CustomerPaymentPaypalCriteria criteria) {
        Specification<CustomerPaymentPaypal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPaymentPaypal_.id));
            }
            if (criteria.getPaypalAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaypalAccount(), CustomerPaymentPaypal_.paypalAccount));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CustomerPaymentPaypal_.amount));
            }
            if (criteria.getResponseCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseCode(), CustomerPaymentPaypal_.responseCode));
            }
            if (criteria.getApprovalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovalCode(), CustomerPaymentPaypal_.approvalCode));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CustomerPaymentPaypal_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerPaymentPaypal_.lastEditedWhen));
            }
            if (criteria.getCustomerPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentId(),
                    root -> root.join(CustomerPaymentPaypal_.customerPayment, JoinType.LEFT).get(CustomerPayment_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CustomerPaymentPaypal_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
