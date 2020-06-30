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

import com.vertical.commerce.domain.DiscountTypes;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DiscountTypesRepository;
import com.vertical.commerce.service.dto.DiscountTypesCriteria;
import com.vertical.commerce.service.dto.DiscountTypesDTO;
import com.vertical.commerce.service.mapper.DiscountTypesMapper;

/**
 * Service for executing complex queries for {@link DiscountTypes} entities in the database.
 * The main input is a {@link DiscountTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiscountTypesDTO} or a {@link Page} of {@link DiscountTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscountTypesQueryService extends QueryService<DiscountTypes> {

    private final Logger log = LoggerFactory.getLogger(DiscountTypesQueryService.class);

    private final DiscountTypesRepository discountTypesRepository;

    private final DiscountTypesMapper discountTypesMapper;

    public DiscountTypesQueryService(DiscountTypesRepository discountTypesRepository, DiscountTypesMapper discountTypesMapper) {
        this.discountTypesRepository = discountTypesRepository;
        this.discountTypesMapper = discountTypesMapper;
    }

    /**
     * Return a {@link List} of {@link DiscountTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountTypesDTO> findByCriteria(DiscountTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DiscountTypes> specification = createSpecification(criteria);
        return discountTypesMapper.toDto(discountTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiscountTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscountTypesDTO> findByCriteria(DiscountTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DiscountTypes> specification = createSpecification(criteria);
        return discountTypesRepository.findAll(specification, page)
            .map(discountTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscountTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DiscountTypes> specification = createSpecification(criteria);
        return discountTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscountTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DiscountTypes> createSpecification(DiscountTypesCriteria criteria) {
        Specification<DiscountTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DiscountTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DiscountTypes_.name));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), DiscountTypes_.modifiedDate));
            }
        }
        return specification;
    }
}
