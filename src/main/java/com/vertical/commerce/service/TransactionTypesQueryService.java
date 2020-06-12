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

import com.vertical.commerce.domain.TransactionTypes;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TransactionTypesRepository;
import com.vertical.commerce.service.dto.TransactionTypesCriteria;
import com.vertical.commerce.service.dto.TransactionTypesDTO;
import com.vertical.commerce.service.mapper.TransactionTypesMapper;

/**
 * Service for executing complex queries for {@link TransactionTypes} entities in the database.
 * The main input is a {@link TransactionTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionTypesDTO} or a {@link Page} of {@link TransactionTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionTypesQueryService extends QueryService<TransactionTypes> {

    private final Logger log = LoggerFactory.getLogger(TransactionTypesQueryService.class);

    private final TransactionTypesRepository transactionTypesRepository;

    private final TransactionTypesMapper transactionTypesMapper;

    public TransactionTypesQueryService(TransactionTypesRepository transactionTypesRepository, TransactionTypesMapper transactionTypesMapper) {
        this.transactionTypesRepository = transactionTypesRepository;
        this.transactionTypesMapper = transactionTypesMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionTypesDTO> findByCriteria(TransactionTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionTypes> specification = createSpecification(criteria);
        return transactionTypesMapper.toDto(transactionTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionTypesDTO> findByCriteria(TransactionTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionTypes> specification = createSpecification(criteria);
        return transactionTypesRepository.findAll(specification, page)
            .map(transactionTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionTypes> specification = createSpecification(criteria);
        return transactionTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionTypes> createSpecification(TransactionTypesCriteria criteria) {
        Specification<TransactionTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TransactionTypes_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), TransactionTypes_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), TransactionTypes_.validTo));
            }
        }
        return specification;
    }
}
