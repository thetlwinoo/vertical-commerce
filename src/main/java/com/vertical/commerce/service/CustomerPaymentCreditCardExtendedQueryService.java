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

import com.vertical.commerce.domain.CustomerPaymentCreditCardExtended;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentCreditCardExtendedRepository;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardExtendedMapper;

/**
 * Service for executing complex queries for {@link CustomerPaymentCreditCardExtended} entities in the database.
 * The main input is a {@link CustomerPaymentCreditCardExtendedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentCreditCardExtendedDTO} or a {@link Page} of {@link CustomerPaymentCreditCardExtendedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentCreditCardExtendedQueryService extends QueryService<CustomerPaymentCreditCardExtended> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardExtendedQueryService.class);

    private final CustomerPaymentCreditCardExtendedRepository customerPaymentCreditCardExtendedRepository;

    private final CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper;

    public CustomerPaymentCreditCardExtendedQueryService(CustomerPaymentCreditCardExtendedRepository customerPaymentCreditCardExtendedRepository, CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper) {
        this.customerPaymentCreditCardExtendedRepository = customerPaymentCreditCardExtendedRepository;
        this.customerPaymentCreditCardExtendedMapper = customerPaymentCreditCardExtendedMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentCreditCardExtendedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentCreditCardExtendedDTO> findByCriteria(CustomerPaymentCreditCardExtendedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPaymentCreditCardExtended> specification = createSpecification(criteria);
        return customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtendedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentCreditCardExtendedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentCreditCardExtendedDTO> findByCriteria(CustomerPaymentCreditCardExtendedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPaymentCreditCardExtended> specification = createSpecification(criteria);
        return customerPaymentCreditCardExtendedRepository.findAll(specification, page)
            .map(customerPaymentCreditCardExtendedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentCreditCardExtendedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPaymentCreditCardExtended> specification = createSpecification(criteria);
        return customerPaymentCreditCardExtendedRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentCreditCardExtendedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPaymentCreditCardExtended> createSpecification(CustomerPaymentCreditCardExtendedCriteria criteria) {
        Specification<CustomerPaymentCreditCardExtended> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPaymentCreditCardExtended_.id));
            }
            if (criteria.getErrorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorCode(), CustomerPaymentCreditCardExtended_.errorCode));
            }
            if (criteria.getErrorMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorMessage(), CustomerPaymentCreditCardExtended_.errorMessage));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), CustomerPaymentCreditCardExtended_.lastEditedBy));
            }
            if (criteria.getLastEditeWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditeWhen(), CustomerPaymentCreditCardExtended_.lastEditeWhen));
            }
        }
        return specification;
    }
}
