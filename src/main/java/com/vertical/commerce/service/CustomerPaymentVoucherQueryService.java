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

import com.vertical.commerce.domain.CustomerPaymentVoucher;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CustomerPaymentVoucherRepository;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherCriteria;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentVoucherMapper;

/**
 * Service for executing complex queries for {@link CustomerPaymentVoucher} entities in the database.
 * The main input is a {@link CustomerPaymentVoucherCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerPaymentVoucherDTO} or a {@link Page} of {@link CustomerPaymentVoucherDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerPaymentVoucherQueryService extends QueryService<CustomerPaymentVoucher> {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentVoucherQueryService.class);

    private final CustomerPaymentVoucherRepository customerPaymentVoucherRepository;

    private final CustomerPaymentVoucherMapper customerPaymentVoucherMapper;

    public CustomerPaymentVoucherQueryService(CustomerPaymentVoucherRepository customerPaymentVoucherRepository, CustomerPaymentVoucherMapper customerPaymentVoucherMapper) {
        this.customerPaymentVoucherRepository = customerPaymentVoucherRepository;
        this.customerPaymentVoucherMapper = customerPaymentVoucherMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerPaymentVoucherDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerPaymentVoucherDTO> findByCriteria(CustomerPaymentVoucherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustomerPaymentVoucher> specification = createSpecification(criteria);
        return customerPaymentVoucherMapper.toDto(customerPaymentVoucherRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerPaymentVoucherDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerPaymentVoucherDTO> findByCriteria(CustomerPaymentVoucherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustomerPaymentVoucher> specification = createSpecification(criteria);
        return customerPaymentVoucherRepository.findAll(specification, page)
            .map(customerPaymentVoucherMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerPaymentVoucherCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustomerPaymentVoucher> specification = createSpecification(criteria);
        return customerPaymentVoucherRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerPaymentVoucherCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustomerPaymentVoucher> createSpecification(CustomerPaymentVoucherCriteria criteria) {
        Specification<CustomerPaymentVoucher> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustomerPaymentVoucher_.id));
            }
            if (criteria.getSerialNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNo(), CustomerPaymentVoucher_.serialNo));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CustomerPaymentVoucher_.amount));
            }
            if (criteria.getLastEdityBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEdityBy(), CustomerPaymentVoucher_.lastEdityBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), CustomerPaymentVoucher_.lastEditedWhen));
            }
            if (criteria.getCustomerPaymentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerPaymentId(),
                    root -> root.join(CustomerPaymentVoucher_.customerPayment, JoinType.LEFT).get(CustomerPayment_.id)));
            }
            if (criteria.getCurrencyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyId(),
                    root -> root.join(CustomerPaymentVoucher_.currency, JoinType.LEFT).get(Currency_.id)));
            }
        }
        return specification;
    }
}
