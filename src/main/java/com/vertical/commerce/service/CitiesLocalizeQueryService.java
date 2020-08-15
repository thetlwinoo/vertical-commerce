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

import com.vertical.commerce.domain.CitiesLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.CitiesLocalizeRepository;
import com.vertical.commerce.service.dto.CitiesLocalizeCriteria;
import com.vertical.commerce.service.dto.CitiesLocalizeDTO;
import com.vertical.commerce.service.mapper.CitiesLocalizeMapper;

/**
 * Service for executing complex queries for {@link CitiesLocalize} entities in the database.
 * The main input is a {@link CitiesLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CitiesLocalizeDTO} or a {@link Page} of {@link CitiesLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CitiesLocalizeQueryService extends QueryService<CitiesLocalize> {

    private final Logger log = LoggerFactory.getLogger(CitiesLocalizeQueryService.class);

    private final CitiesLocalizeRepository citiesLocalizeRepository;

    private final CitiesLocalizeMapper citiesLocalizeMapper;

    public CitiesLocalizeQueryService(CitiesLocalizeRepository citiesLocalizeRepository, CitiesLocalizeMapper citiesLocalizeMapper) {
        this.citiesLocalizeRepository = citiesLocalizeRepository;
        this.citiesLocalizeMapper = citiesLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link CitiesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CitiesLocalizeDTO> findByCriteria(CitiesLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CitiesLocalize> specification = createSpecification(criteria);
        return citiesLocalizeMapper.toDto(citiesLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CitiesLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CitiesLocalizeDTO> findByCriteria(CitiesLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CitiesLocalize> specification = createSpecification(criteria);
        return citiesLocalizeRepository.findAll(specification, page)
            .map(citiesLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CitiesLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CitiesLocalize> specification = createSpecification(criteria);
        return citiesLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link CitiesLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CitiesLocalize> createSpecification(CitiesLocalizeCriteria criteria) {
        Specification<CitiesLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CitiesLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CitiesLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(CitiesLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(CitiesLocalize_.city, JoinType.LEFT).get(Cities_.id)));
            }
        }
        return specification;
    }
}
