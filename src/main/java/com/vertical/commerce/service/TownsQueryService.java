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

import com.vertical.commerce.domain.Towns;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownsRepository;
import com.vertical.commerce.service.dto.TownsCriteria;
import com.vertical.commerce.service.dto.TownsDTO;
import com.vertical.commerce.service.mapper.TownsMapper;

/**
 * Service for executing complex queries for {@link Towns} entities in the database.
 * The main input is a {@link TownsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownsDTO} or a {@link Page} of {@link TownsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownsQueryService extends QueryService<Towns> {

    private final Logger log = LoggerFactory.getLogger(TownsQueryService.class);

    private final TownsRepository townsRepository;

    private final TownsMapper townsMapper;

    public TownsQueryService(TownsRepository townsRepository, TownsMapper townsMapper) {
        this.townsRepository = townsRepository;
        this.townsMapper = townsMapper;
    }

    /**
     * Return a {@link List} of {@link TownsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownsDTO> findByCriteria(TownsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Towns> specification = createSpecification(criteria);
        return townsMapper.toDto(townsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownsDTO> findByCriteria(TownsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Towns> specification = createSpecification(criteria);
        return townsRepository.findAll(specification, page)
            .map(townsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Towns> specification = createSpecification(criteria);
        return townsRepository.count(specification);
    }

    /**
     * Function to convert {@link TownsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Towns> createSpecification(TownsCriteria criteria) {
        Specification<Towns> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Towns_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Towns_.name));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Towns_.postalCode));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Towns_.description));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Towns_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Towns_.validTo));
            }
            if (criteria.getTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownshipId(),
                    root -> root.join(Towns_.township, JoinType.LEFT).get(Townships_.id)));
            }
        }
        return specification;
    }
}
