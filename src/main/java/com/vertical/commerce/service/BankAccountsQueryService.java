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

import com.vertical.commerce.domain.BankAccounts;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.BankAccountsRepository;
import com.vertical.commerce.service.dto.BankAccountsCriteria;
import com.vertical.commerce.service.dto.BankAccountsDTO;
import com.vertical.commerce.service.mapper.BankAccountsMapper;

/**
 * Service for executing complex queries for {@link BankAccounts} entities in the database.
 * The main input is a {@link BankAccountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankAccountsDTO} or a {@link Page} of {@link BankAccountsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankAccountsQueryService extends QueryService<BankAccounts> {

    private final Logger log = LoggerFactory.getLogger(BankAccountsQueryService.class);

    private final BankAccountsRepository bankAccountsRepository;

    private final BankAccountsMapper bankAccountsMapper;

    public BankAccountsQueryService(BankAccountsRepository bankAccountsRepository, BankAccountsMapper bankAccountsMapper) {
        this.bankAccountsRepository = bankAccountsRepository;
        this.bankAccountsMapper = bankAccountsMapper;
    }

    /**
     * Return a {@link List} of {@link BankAccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankAccountsDTO> findByCriteria(BankAccountsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BankAccounts> specification = createSpecification(criteria);
        return bankAccountsMapper.toDto(bankAccountsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankAccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankAccountsDTO> findByCriteria(BankAccountsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BankAccounts> specification = createSpecification(criteria);
        return bankAccountsRepository.findAll(specification, page)
            .map(bankAccountsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankAccountsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BankAccounts> specification = createSpecification(criteria);
        return bankAccountsRepository.count(specification);
    }

    /**
     * Function to convert {@link BankAccountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BankAccounts> createSpecification(BankAccountsCriteria criteria) {
        Specification<BankAccounts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BankAccounts_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BankAccounts_.name));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), BankAccounts_.branch));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), BankAccounts_.code));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), BankAccounts_.number));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), BankAccounts_.type));
            }
            if (criteria.getBank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBank(), BankAccounts_.bank));
            }
            if (criteria.getInternationalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternationalCode(), BankAccounts_.internationalCode));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), BankAccounts_.lastEditedBy));
            }
            if (criteria.getLogoPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogoPhoto(), BankAccounts_.logoPhoto));
            }
            if (criteria.getValidForm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidForm(), BankAccounts_.validForm));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), BankAccounts_.validTo));
            }
        }
        return specification;
    }
}
