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

import com.vertical.commerce.domain.TownsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownsCultureRepository;
import com.vertical.commerce.service.dto.TownsCultureCriteria;
import com.vertical.commerce.service.dto.TownsCultureDTO;
import com.vertical.commerce.service.mapper.TownsCultureMapper;

/**
 * Service for executing complex queries for {@link TownsCulture} entities in the database.
 * The main input is a {@link TownsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownsCultureDTO} or a {@link Page} of {@link TownsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownsCultureQueryService extends QueryService<TownsCulture> {

    private final Logger log = LoggerFactory.getLogger(TownsCultureQueryService.class);

    private final TownsCultureRepository townsCultureRepository;

    private final TownsCultureMapper townsCultureMapper;

    public TownsCultureQueryService(TownsCultureRepository townsCultureRepository, TownsCultureMapper townsCultureMapper) {
        this.townsCultureRepository = townsCultureRepository;
        this.townsCultureMapper = townsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link TownsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownsCultureDTO> findByCriteria(TownsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TownsCulture> specification = createSpecification(criteria);
        return townsCultureMapper.toDto(townsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownsCultureDTO> findByCriteria(TownsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TownsCulture> specification = createSpecification(criteria);
        return townsCultureRepository.findAll(specification, page)
            .map(townsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TownsCulture> specification = createSpecification(criteria);
        return townsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link TownsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TownsCulture> createSpecification(TownsCultureCriteria criteria) {
        Specification<TownsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TownsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TownsCulture_.name));
            }
        }
        return specification;
    }
}
