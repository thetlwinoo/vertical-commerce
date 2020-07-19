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

import com.vertical.commerce.domain.CountriesCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CountriesCultureRepository;
import com.vertical.commerce.service.dto.CountriesCultureCriteria;
import com.vertical.commerce.service.dto.CountriesCultureDTO;
import com.vertical.commerce.service.mapper.CountriesCultureMapper;

/**
 * Service for executing complex queries for {@link CountriesCulture} entities in the database.
 * The main input is a {@link CountriesCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountriesCultureDTO} or a {@link Page} of {@link CountriesCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountriesCultureQueryService extends QueryService<CountriesCulture> {

    private final Logger log = LoggerFactory.getLogger(CountriesCultureQueryService.class);

    private final CountriesCultureRepository countriesCultureRepository;

    private final CountriesCultureMapper countriesCultureMapper;

    public CountriesCultureQueryService(CountriesCultureRepository countriesCultureRepository, CountriesCultureMapper countriesCultureMapper) {
        this.countriesCultureRepository = countriesCultureRepository;
        this.countriesCultureMapper = countriesCultureMapper;
    }

    /**
     * Return a {@link List} of {@link CountriesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountriesCultureDTO> findByCriteria(CountriesCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CountriesCulture> specification = createSpecification(criteria);
        return countriesCultureMapper.toDto(countriesCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountriesCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountriesCultureDTO> findByCriteria(CountriesCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CountriesCulture> specification = createSpecification(criteria);
        return countriesCultureRepository.findAll(specification, page)
            .map(countriesCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountriesCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CountriesCulture> specification = createSpecification(criteria);
        return countriesCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link CountriesCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CountriesCulture> createSpecification(CountriesCultureCriteria criteria) {
        Specification<CountriesCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CountriesCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CountriesCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(CountriesCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(CountriesCulture_.country, JoinType.LEFT).get(Countries_.id)));
            }
        }
        return specification;
    }
}
