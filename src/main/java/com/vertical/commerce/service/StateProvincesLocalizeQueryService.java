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

import com.vertical.commerce.domain.StateProvincesLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.StateProvincesLocalizeRepository;
import com.vertical.commerce.service.dto.StateProvincesLocalizeCriteria;
import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;
import com.vertical.commerce.service.mapper.StateProvincesLocalizeMapper;

/**
 * Service for executing complex queries for {@link StateProvincesLocalize} entities in the database.
 * The main input is a {@link StateProvincesLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateProvincesLocalizeDTO} or a {@link Page} of {@link StateProvincesLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateProvincesLocalizeQueryService extends QueryService<StateProvincesLocalize> {

    private final Logger log = LoggerFactory.getLogger(StateProvincesLocalizeQueryService.class);

    private final StateProvincesLocalizeRepository stateProvincesLocalizeRepository;

    private final StateProvincesLocalizeMapper stateProvincesLocalizeMapper;

    public StateProvincesLocalizeQueryService(StateProvincesLocalizeRepository stateProvincesLocalizeRepository, StateProvincesLocalizeMapper stateProvincesLocalizeMapper) {
        this.stateProvincesLocalizeRepository = stateProvincesLocalizeRepository;
        this.stateProvincesLocalizeMapper = stateProvincesLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link StateProvincesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateProvincesLocalizeDTO> findByCriteria(StateProvincesLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateProvincesLocalize> specification = createSpecification(criteria);
        return stateProvincesLocalizeMapper.toDto(stateProvincesLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StateProvincesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvincesLocalizeDTO> findByCriteria(StateProvincesLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateProvincesLocalize> specification = createSpecification(criteria);
        return stateProvincesLocalizeRepository.findAll(specification, page)
            .map(stateProvincesLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateProvincesLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateProvincesLocalize> specification = createSpecification(criteria);
        return stateProvincesLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link StateProvincesLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StateProvincesLocalize> createSpecification(StateProvincesLocalizeCriteria criteria) {
        Specification<StateProvincesLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StateProvincesLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), StateProvincesLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(StateProvincesLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getStateProvinceId() != null) {
                specification = specification.and(buildSpecification(criteria.getStateProvinceId(),
                    root -> root.join(StateProvincesLocalize_.stateProvince, JoinType.LEFT).get(StateProvinces_.id)));
            }
        }
        return specification;
    }
}
