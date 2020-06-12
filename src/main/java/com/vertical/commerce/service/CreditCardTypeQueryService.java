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

import com.vertical.commerce.domain.CreditCardType;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CreditCardTypeRepository;
import com.vertical.commerce.service.dto.CreditCardTypeCriteria;
import com.vertical.commerce.service.dto.CreditCardTypeDTO;
import com.vertical.commerce.service.mapper.CreditCardTypeMapper;

/**
 * Service for executing complex queries for {@link CreditCardType} entities in the database.
 * The main input is a {@link CreditCardTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CreditCardTypeDTO} or a {@link Page} of {@link CreditCardTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CreditCardTypeQueryService extends QueryService<CreditCardType> {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeQueryService.class);

    private final CreditCardTypeRepository creditCardTypeRepository;

    private final CreditCardTypeMapper creditCardTypeMapper;

    public CreditCardTypeQueryService(CreditCardTypeRepository creditCardTypeRepository, CreditCardTypeMapper creditCardTypeMapper) {
        this.creditCardTypeRepository = creditCardTypeRepository;
        this.creditCardTypeMapper = creditCardTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CreditCardTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CreditCardTypeDTO> findByCriteria(CreditCardTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CreditCardType> specification = createSpecification(criteria);
        return creditCardTypeMapper.toDto(creditCardTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CreditCardTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditCardTypeDTO> findByCriteria(CreditCardTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CreditCardType> specification = createSpecification(criteria);
        return creditCardTypeRepository.findAll(specification, page)
            .map(creditCardTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CreditCardTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CreditCardType> specification = createSpecification(criteria);
        return creditCardTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CreditCardTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CreditCardType> createSpecification(CreditCardTypeCriteria criteria) {
        Specification<CreditCardType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CreditCardType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CreditCardType_.name));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), CreditCardType_.modifiedDate));
            }
        }
        return specification;
    }
}
