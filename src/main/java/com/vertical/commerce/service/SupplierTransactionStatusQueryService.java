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

import com.vertical.commerce.domain.SupplierTransactionStatus;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.SupplierTransactionStatusRepository;
import com.vertical.commerce.service.dto.SupplierTransactionStatusCriteria;
import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;
import com.vertical.commerce.service.mapper.SupplierTransactionStatusMapper;

/**
 * Service for executing complex queries for {@link SupplierTransactionStatus} entities in the database.
 * The main input is a {@link SupplierTransactionStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierTransactionStatusDTO} or a {@link Page} of {@link SupplierTransactionStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierTransactionStatusQueryService extends QueryService<SupplierTransactionStatus> {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionStatusQueryService.class);

    private final SupplierTransactionStatusRepository supplierTransactionStatusRepository;

    private final SupplierTransactionStatusMapper supplierTransactionStatusMapper;

    public SupplierTransactionStatusQueryService(SupplierTransactionStatusRepository supplierTransactionStatusRepository, SupplierTransactionStatusMapper supplierTransactionStatusMapper) {
        this.supplierTransactionStatusRepository = supplierTransactionStatusRepository;
        this.supplierTransactionStatusMapper = supplierTransactionStatusMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierTransactionStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierTransactionStatusDTO> findByCriteria(SupplierTransactionStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierTransactionStatus> specification = createSpecification(criteria);
        return supplierTransactionStatusMapper.toDto(supplierTransactionStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierTransactionStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierTransactionStatusDTO> findByCriteria(SupplierTransactionStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierTransactionStatus> specification = createSpecification(criteria);
        return supplierTransactionStatusRepository.findAll(specification, page)
            .map(supplierTransactionStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierTransactionStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierTransactionStatus> specification = createSpecification(criteria);
        return supplierTransactionStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierTransactionStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierTransactionStatus> createSpecification(SupplierTransactionStatusCriteria criteria) {
        Specification<SupplierTransactionStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierTransactionStatus_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SupplierTransactionStatus_.name));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), SupplierTransactionStatus_.label));
            }
            if (criteria.getShortLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortLabel(), SupplierTransactionStatus_.shortLabel));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), SupplierTransactionStatus_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), SupplierTransactionStatus_.lastEditedWhen));
            }
        }
        return specification;
    }
}
