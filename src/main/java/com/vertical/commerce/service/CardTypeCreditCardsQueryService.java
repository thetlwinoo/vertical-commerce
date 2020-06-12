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

import com.vertical.commerce.domain.CardTypeCreditCards;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CardTypeCreditCardsRepository;
import com.vertical.commerce.service.dto.CardTypeCreditCardsCriteria;
import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;
import com.vertical.commerce.service.mapper.CardTypeCreditCardsMapper;

/**
 * Service for executing complex queries for {@link CardTypeCreditCards} entities in the database.
 * The main input is a {@link CardTypeCreditCardsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CardTypeCreditCardsDTO} or a {@link Page} of {@link CardTypeCreditCardsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardTypeCreditCardsQueryService extends QueryService<CardTypeCreditCards> {

    private final Logger log = LoggerFactory.getLogger(CardTypeCreditCardsQueryService.class);

    private final CardTypeCreditCardsRepository cardTypeCreditCardsRepository;

    private final CardTypeCreditCardsMapper cardTypeCreditCardsMapper;

    public CardTypeCreditCardsQueryService(CardTypeCreditCardsRepository cardTypeCreditCardsRepository, CardTypeCreditCardsMapper cardTypeCreditCardsMapper) {
        this.cardTypeCreditCardsRepository = cardTypeCreditCardsRepository;
        this.cardTypeCreditCardsMapper = cardTypeCreditCardsMapper;
    }

    /**
     * Return a {@link List} of {@link CardTypeCreditCardsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CardTypeCreditCardsDTO> findByCriteria(CardTypeCreditCardsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CardTypeCreditCards> specification = createSpecification(criteria);
        return cardTypeCreditCardsMapper.toDto(cardTypeCreditCardsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CardTypeCreditCardsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CardTypeCreditCardsDTO> findByCriteria(CardTypeCreditCardsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CardTypeCreditCards> specification = createSpecification(criteria);
        return cardTypeCreditCardsRepository.findAll(specification, page)
            .map(cardTypeCreditCardsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CardTypeCreditCardsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CardTypeCreditCards> specification = createSpecification(criteria);
        return cardTypeCreditCardsRepository.count(specification);
    }

    /**
     * Function to convert {@link CardTypeCreditCardsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CardTypeCreditCards> createSpecification(CardTypeCreditCardsCriteria criteria) {
        Specification<CardTypeCreditCards> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CardTypeCreditCards_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CardTypeCreditCards_.name));
            }
            if (criteria.getStartNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartNumber(), CardTypeCreditCards_.startNumber));
            }
            if (criteria.getEndNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndNumber(), CardTypeCreditCards_.endNumber));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), CardTypeCreditCards_.modifiedDate));
            }
        }
        return specification;
    }
}
