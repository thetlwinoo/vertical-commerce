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

import com.vertical.commerce.domain.CardTypes;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CardTypesRepository;
import com.vertical.commerce.service.dto.CardTypesCriteria;
import com.vertical.commerce.service.dto.CardTypesDTO;
import com.vertical.commerce.service.mapper.CardTypesMapper;

/**
 * Service for executing complex queries for {@link CardTypes} entities in the database.
 * The main input is a {@link CardTypesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CardTypesDTO} or a {@link Page} of {@link CardTypesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardTypesQueryService extends QueryService<CardTypes> {

    private final Logger log = LoggerFactory.getLogger(CardTypesQueryService.class);

    private final CardTypesRepository cardTypesRepository;

    private final CardTypesMapper cardTypesMapper;

    public CardTypesQueryService(CardTypesRepository cardTypesRepository, CardTypesMapper cardTypesMapper) {
        this.cardTypesRepository = cardTypesRepository;
        this.cardTypesMapper = cardTypesMapper;
    }

    /**
     * Return a {@link List} of {@link CardTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CardTypesDTO> findByCriteria(CardTypesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CardTypes> specification = createSpecification(criteria);
        return cardTypesMapper.toDto(cardTypesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CardTypesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CardTypesDTO> findByCriteria(CardTypesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CardTypes> specification = createSpecification(criteria);
        return cardTypesRepository.findAll(specification, page)
            .map(cardTypesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CardTypesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CardTypes> specification = createSpecification(criteria);
        return cardTypesRepository.count(specification);
    }

    /**
     * Function to convert {@link CardTypesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CardTypes> createSpecification(CardTypesCriteria criteria) {
        Specification<CardTypes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CardTypes_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CardTypes_.name));
            }
            if (criteria.getIssuerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuerId(), CardTypes_.issuerId));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), CardTypes_.modifiedDate));
            }
        }
        return specification;
    }
}
