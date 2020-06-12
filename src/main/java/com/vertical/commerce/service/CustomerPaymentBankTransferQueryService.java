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

import com.vertical.commerce.domain.CustomerPaymentBankTransfer;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentBankTransferRepository;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentBankTransferMapper;

/**
 * Service for executing complex queries for {@link CustomerPaymentBankTransfer} entities in the database.
 * The main input is a {@link CustomerPaymentBankTransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentBankTransferDTO} or a {@link Page} of {@link CustomerPaymentBankTransferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentBankTransferQueryService extends QueryService<CustomerPaymentBankTransfer> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentBankTransferQueryService.class);

    private final CustomerPaymentBankTransferRepository customerPaymentBankTransferRepository;

    private final CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper;

    public CustomerPaymentBankTransferQueryService(CustomerPaymentBankTransferRepository customerPaymentBankTransferRepository, CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper) {
        this.customerPaymentBankTransferRepository = customerPaymentBankTransferRepository;
        this.customerPaymentBankTransferMapper = customerPaymentBankTransferMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentBankTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentBankTransferDTO> findByCriteria(CustomerPaymentBankTransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPaymentBankTransfer> specification = createSpecification(criteria);
        return customerPaymentBankTransferMapper.toDto(customerPaymentBankTransferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentBankTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentBankTransferDTO> findByCriteria(CustomerPaymentBankTransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPaymentBankTransfer> specification = createSpecification(criteria);
        return customerPaymentBankTransferRepository.findAll(specification, page)
            .map(customerPaymentBankTransferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentBankTransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPaymentBankTransfer> specification = createSpecification(criteria);
        return customerPaymentBankTransferRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentBankTransferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPaymentBankTransfer> createSpecification(CustomerPaymentBankTransferCriteria criteria) {
        Specification<CustomerPaymentBankTransfer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPaymentBankTransfer_.id));
            }
            if (criteria.getReceiptImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiptImageUrl(), CustomerPaymentBankTransfer_.receiptImageUrl));
            }
            if (criteria.getNameInBankAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameInBankAccount(), CustomerPaymentBankTransfer_.nameInBankAccount));
            }
            if (criteria.getDateOfTransfer() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfTransfer(), CustomerPaymentBankTransfer_.dateOfTransfer));
            }
            if (criteria.getAmountTransferred() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountTransferred(), CustomerPaymentBankTransfer_.amountTransferred));
            }
            if (criteria.getLastEdityBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEdityBy(), CustomerPaymentBankTransfer_.lastEdityBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerPaymentBankTransfer_.lastEditedWhen));
            }
            if (criteria.getCustomerPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentId(),
                    root -> root.join(CustomerPaymentBankTransfer_.customerPayment, JoinType.LEFT).get(CustomerPayment_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CustomerPaymentBankTransfer_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
