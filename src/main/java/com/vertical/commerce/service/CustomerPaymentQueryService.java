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

import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentRepository;
import com.vertical.commerce.service.dto.CustomerPaymentCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentMapper;

/**
 * Service for executing complex queries for {@link CustomerPayment} entities in the database.
 * The main input is a {@link CustomerPaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentDTO} or a {@link Page} of {@link CustomerPaymentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentQueryService extends QueryService<CustomerPayment> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentQueryService.class);

    private final CustomerPaymentRepository customerPaymentRepository;

    private final CustomerPaymentMapper customerPaymentMapper;

    public CustomerPaymentQueryService(CustomerPaymentRepository customerPaymentRepository, CustomerPaymentMapper customerPaymentMapper) {
        this.customerPaymentRepository = customerPaymentRepository;
        this.customerPaymentMapper = customerPaymentMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentDTO> findByCriteria(CustomerPaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPayment> specification = createSpecification(criteria);
        return customerPaymentMapper.toDto(customerPaymentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentDTO> findByCriteria(CustomerPaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPayment> specification = createSpecification(criteria);
        return customerPaymentRepository.findAll(specification, page)
            .map(customerPaymentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPayment> specification = createSpecification(criteria);
        return customerPaymentRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPayment> createSpecification(CustomerPaymentCriteria criteria) {
        Specification<CustomerPayment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPayment_.id));
            }
            if (criteria.getAmountExcludingTax() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountExcludingTax(), CustomerPayment_.amountExcludingTax));
            }
            if (criteria.getTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaxAmount(), CustomerPayment_.taxAmount));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), CustomerPayment_.transactionAmount));
            }
            if (criteria.getOutstandingAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutstandingAmount(), CustomerPayment_.outstandingAmount));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CustomerPayment_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerPayment_.lastEditedWhen));
            }
            if (criteria.getCustomerTransactionId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerTransactionId(),
                    root -> root.join(CustomerPayment_.customerTransaction, JoinType.LEFT).get(CustomerTransactions_.id)));
            }
            if (criteria.getPaymentMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethodId(),
                    root -> root.join(CustomerPayment_.paymentMethod, JoinType.LEFT).get(PaymentMethods_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CustomerPayment_.currency, JoinType.LEFT).get(Currency_.id)));
            }
            if (criteria.getCurrencyRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyRateId(),
                    root -> root.join(CustomerPayment_.currencyRate, JoinType.LEFT).get(CurrencyRate_.id)));
            }
            if (criteria.getCustomerPaymentCreditCardId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentCreditCardId(),
                    root -> root.join(CustomerPayment_.customerPaymentCreditCard, JoinType.LEFT).get(CustomerPaymentCreditCard_.id)));
            }
            if (criteria.getCustomerPaymentVoucherId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentVoucherId(),
                    root -> root.join(CustomerPayment_.customerPaymentVoucher, JoinType.LEFT).get(CustomerPaymentVoucher_.id)));
            }
            if (criteria.getCustomerPaymentBankTransferId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentBankTransferId(),
                    root -> root.join(CustomerPayment_.customerPaymentBankTransfer, JoinType.LEFT).get(CustomerPaymentBankTransfer_.id)));
            }
            if (criteria.getCustomerPaymentPaypalId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentPaypalId(),
                    root -> root.join(CustomerPayment_.customerPaymentPaypal, JoinType.LEFT).get(CustomerPaymentPaypal_.id)));
            }
        }
        return specification;
    }
}
