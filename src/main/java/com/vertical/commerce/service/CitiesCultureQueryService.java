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

import com.vertical.commerce.domain.CitiesCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CitiesCultureRepository;
import com.vertical.commerce.service.dto.CitiesCultureCriteria;
import com.vertical.commerce.service.dto.CitiesCultureDTO;
import com.vertical.commerce.service.mapper.CitiesCultureMapper;

/**
 * Service for executing complex queries for {@link CitiesCulture} entities in the database.
 * The main input is a {@link CitiesCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitiesCultureDTO} or a {@link Page} of {@link CitiesCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitiesCultureQueryService extends QueryService<CitiesCulture> {

    private final Logger log = LoggerFactory.getLogger(CitiesCultureQueryService.class);

    private final CitiesCultureRepository citiesCultureRepository;

    private final CitiesCultureMapper citiesCultureMapper;

    public CitiesCultureQueryService(CitiesCultureRepository citiesCultureRepository, CitiesCultureMapper citiesCultureMapper) {
        this.citiesCultureRepository = citiesCultureRepository;
        this.citiesCultureMapper = citiesCultureMapper;
    }

    /**
     * Return a {@link List} of {@link CitiesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitiesCultureDTO> findByCriteria(CitiesCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CitiesCulture> specification = createSpecification(criteria);
        return citiesCultureMapper.toDto(citiesCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitiesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitiesCultureDTO> findByCriteria(CitiesCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CitiesCulture> specification = createSpecification(criteria);
        return citiesCultureRepository.findAll(specification, page)
            .map(citiesCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitiesCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CitiesCulture> specification = createSpecification(criteria);
        return citiesCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link CitiesCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CitiesCulture> createSpecification(CitiesCultureCriteria criteria) {
        Specification<CitiesCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CitiesCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CitiesCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(CitiesCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(CitiesCulture_.city, JoinType.LEFT).get(Cities_.id)));
            }
        }
        return specification;
    }
}
