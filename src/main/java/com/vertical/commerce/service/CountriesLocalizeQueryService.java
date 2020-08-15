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

import com.vertical.commerce.domain.CountriesLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CountriesLocalizeRepository;
import com.vertical.commerce.service.dto.CountriesLocalizeCriteria;
import com.vertical.commerce.service.dto.CountriesLocalizeDTO;
import com.vertical.commerce.service.mapper.CountriesLocalizeMapper;

/**
 * Service for executing complex queries for {@link CountriesLocalize} entities in the database.
 * The main input is a {@link CountriesLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CountriesLocalizeDTO} or a {@link Page} of {@link CountriesLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CountriesLocalizeQueryService extends QueryService<CountriesLocalize> {

    private final Logger log = LoggerFactory.getLogger(CountriesLocalizeQueryService.class);

    private final CountriesLocalizeRepository countriesLocalizeRepository;

    private final CountriesLocalizeMapper countriesLocalizeMapper;

    public CountriesLocalizeQueryService(CountriesLocalizeRepository countriesLocalizeRepository, CountriesLocalizeMapper countriesLocalizeMapper) {
        this.countriesLocalizeRepository = countriesLocalizeRepository;
        this.countriesLocalizeMapper = countriesLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link CountriesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CountriesLocalizeDTO> findByCriteria(CountriesLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CountriesLocalize> specification = createSpecification(criteria);
        return countriesLocalizeMapper.toDto(countriesLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CountriesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CountriesLocalizeDTO> findByCriteria(CountriesLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CountriesLocalize> specification = createSpecification(criteria);
        return countriesLocalizeRepository.findAll(specification, page)
            .map(countriesLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CountriesLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CountriesLocalize> specification = createSpecification(criteria);
        return countriesLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link CountriesLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CountriesLocalize> createSpecification(CountriesLocalizeCriteria criteria) {
        Specification<CountriesLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CountriesLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CountriesLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(CountriesLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCountryId(),
                    root -> root.join(CountriesLocalize_.country, JoinType.LEFT).get(Countries_.id)));
            }
        }
        return specification;
    }
}
