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

import com.vertical.commerce.domain.StateProvincesCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.StateProvincesCultureRepository;
import com.vertical.commerce.service.dto.StateProvincesCultureCriteria;
import com.vertical.commerce.service.dto.StateProvincesCultureDTO;
import com.vertical.commerce.service.mapper.StateProvincesCultureMapper;

/**
 * Service for executing complex queries for {@link StateProvincesCulture} entities in the database.
 * The main input is a {@link StateProvincesCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateProvincesCultureDTO} or a {@link Page} of {@link StateProvincesCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateProvincesCultureQueryService extends QueryService<StateProvincesCulture> {

    private final Logger log = LoggerFactory.getLogger(StateProvincesCultureQueryService.class);

    private final StateProvincesCultureRepository stateProvincesCultureRepository;

    private final StateProvincesCultureMapper stateProvincesCultureMapper;

    public StateProvincesCultureQueryService(StateProvincesCultureRepository stateProvincesCultureRepository, StateProvincesCultureMapper stateProvincesCultureMapper) {
        this.stateProvincesCultureRepository = stateProvincesCultureRepository;
        this.stateProvincesCultureMapper = stateProvincesCultureMapper;
    }

    /**
     * Return a {@link List} of {@link StateProvincesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateProvincesCultureDTO> findByCriteria(StateProvincesCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateProvincesCulture> specification = createSpecification(criteria);
        return stateProvincesCultureMapper.toDto(stateProvincesCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateProvincesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvincesCultureDTO> findByCriteria(StateProvincesCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateProvincesCulture> specification = createSpecification(criteria);
        return stateProvincesCultureRepository.findAll(specification, page)
            .map(stateProvincesCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateProvincesCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateProvincesCulture> specification = createSpecification(criteria);
        return stateProvincesCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link StateProvincesCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StateProvincesCulture> createSpecification(StateProvincesCultureCriteria criteria) {
        Specification<StateProvincesCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StateProvincesCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StateProvincesCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(StateProvincesCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getStateProvinceId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateProvinceId(),
                    root -> root.join(StateProvincesCulture_.stateProvince, JoinType.LEFT).get(StateProvinces_.id)));
            }
        }
        return specification;
    }
}
