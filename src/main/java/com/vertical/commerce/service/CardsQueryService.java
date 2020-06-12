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

import com.vertical.commerce.domain.Cards;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CardsRepository;
import com.vertical.commerce.service.dto.CardsCriteria;
import com.vertical.commerce.service.dto.CardsDTO;
import com.vertical.commerce.service.mapper.CardsMapper;

/**
 * Service for executing complex queries for {@link Cards} entities in the database.
 * The main input is a {@link CardsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CardsDTO} or a {@link Page} of {@link CardsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CardsQueryService extends QueryService<Cards> {

    private final Logger log = LoggerFactory.getLogger(CardsQueryService.class);

    private final CardsRepository cardsRepository;

    private final CardsMapper cardsMapper;

    public CardsQueryService(CardsRepository cardsRepository, CardsMapper cardsMapper) {
        this.cardsRepository = cardsRepository;
        this.cardsMapper = cardsMapper;
    }

    /**
     * Return a {@link List} of {@link CardsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CardsDTO> findByCriteria(CardsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cards> specification = createSpecification(criteria);
        return cardsMapper.toDto(cardsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CardsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CardsDTO> findByCriteria(CardsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cards> specification = createSpecification(criteria);
        return cardsRepository.findAll(specification, page)
            .map(cardsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CardsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cards> specification = createSpecification(criteria);
        return cardsRepository.count(specification);
    }

    /**
     * Function to convert {@link CardsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cards> createSpecification(CardsCriteria criteria) {
        Specification<Cards> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cards_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumber(), Cards_.number));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Cards_.modifiedDate));
            }
        }
        return specification;
    }
}
