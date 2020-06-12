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

import com.vertical.commerce.domain.CustomerPaymentCreditCard;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentCreditCardRepository;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardMapper;

/**
 * Service for executing complex queries for {@link CustomerPaymentCreditCard} entities in the database.
 * The main input is a {@link CustomerPaymentCreditCardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentCreditCardDTO} or a {@link Page} of {@link CustomerPaymentCreditCardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentCreditCardQueryService extends QueryService<CustomerPaymentCreditCard> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardQueryService.class);

    private final CustomerPaymentCreditCardRepository customerPaymentCreditCardRepository;

    private final CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper;

    public CustomerPaymentCreditCardQueryService(CustomerPaymentCreditCardRepository customerPaymentCreditCardRepository, CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper) {
        this.customerPaymentCreditCardRepository = customerPaymentCreditCardRepository;
        this.customerPaymentCreditCardMapper = customerPaymentCreditCardMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentCreditCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentCreditCardDTO> findByCriteria(CustomerPaymentCreditCardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPaymentCreditCard> specification = createSpecification(criteria);
        return customerPaymentCreditCardMapper.toDto(customerPaymentCreditCardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentCreditCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentCreditCardDTO> findByCriteria(CustomerPaymentCreditCardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPaymentCreditCard> specification = createSpecification(criteria);
        return customerPaymentCreditCardRepository.findAll(specification, page)
            .map(customerPaymentCreditCardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentCreditCardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPaymentCreditCard> specification = createSpecification(criteria);
        return customerPaymentCreditCardRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentCreditCardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPaymentCreditCard> createSpecification(CustomerPaymentCreditCardCriteria criteria) {
        Specification<CustomerPaymentCreditCard> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPaymentCreditCard_.id));
            }
            if (criteria.getCreditCardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditCardNumber(), CustomerPaymentCreditCard_.creditCardNumber));
            }
            if (criteria.getCreditCardExpiryMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditCardExpiryMonth(), CustomerPaymentCreditCard_.creditCardExpiryMonth));
            }
            if (criteria.getCreditCardExpiryYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditCardExpiryYear(), CustomerPaymentCreditCard_.creditCardExpiryYear));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CustomerPaymentCreditCard_.amount));
            }
            if (criteria.getBatchId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchId(), CustomerPaymentCreditCard_.batchId));
            }
            if (criteria.getResponseCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseCode(), CustomerPaymentCreditCard_.responseCode));
            }
            if (criteria.getApprovalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovalCode(), CustomerPaymentCreditCard_.approvalCode));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CustomerPaymentCreditCard_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerPaymentCreditCard_.lastEditedWhen));
            }
            if (criteria.getCustomerPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentId(),
                    root -> root.join(CustomerPaymentCreditCard_.customerPayment, JoinType.LEFT).get(CustomerPayment_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CustomerPaymentCreditCard_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
