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

import com.vertical.commerce.domain.TownshipsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownshipsCultureRepository;
import com.vertical.commerce.service.dto.TownshipsCultureCriteria;
import com.vertical.commerce.service.dto.TownshipsCultureDTO;
import com.vertical.commerce.service.mapper.TownshipsCultureMapper;

/**
 * Service for executing complex queries for {@link TownshipsCulture} entities in the database.
 * The main input is a {@link TownshipsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownshipsCultureDTO} or a {@link Page} of {@link TownshipsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownshipsCultureQueryService extends QueryService<TownshipsCulture> {

    private final Logger log = LoggerFactory.getLogger(TownshipsCultureQueryService.class);

    private final TownshipsCultureRepository townshipsCultureRepository;

    private final TownshipsCultureMapper townshipsCultureMapper;

    public TownshipsCultureQueryService(TownshipsCultureRepository townshipsCultureRepository, TownshipsCultureMapper townshipsCultureMapper) {
        this.townshipsCultureRepository = townshipsCultureRepository;
        this.townshipsCultureMapper = townshipsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link TownshipsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownshipsCultureDTO> findByCriteria(TownshipsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TownshipsCulture> specification = createSpecification(criteria);
        return townshipsCultureMapper.toDto(townshipsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownshipsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownshipsCultureDTO> findByCriteria(TownshipsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TownshipsCulture> specification = createSpecification(criteria);
        return townshipsCultureRepository.findAll(specification, page)
            .map(townshipsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownshipsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TownshipsCulture> specification = createSpecification(criteria);
        return townshipsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link TownshipsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TownshipsCulture> createSpecification(TownshipsCultureCriteria criteria) {
        Specification<TownshipsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TownshipsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TownshipsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(TownshipsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownshipId(),
                    root -> root.join(TownshipsCulture_.township, JoinType.LEFT).get(Townships_.id)));
            }
        }
        return specification;
    }
}
