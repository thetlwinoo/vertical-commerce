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

import com.vertical.commerce.domain.RegionsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.RegionsCultureRepository;
import com.vertical.commerce.service.dto.RegionsCultureCriteria;
import com.vertical.commerce.service.dto.RegionsCultureDTO;
import com.vertical.commerce.service.mapper.RegionsCultureMapper;

/**
 * Service for executing complex queries for {@link RegionsCulture} entities in the database.
 * The main input is a {@link RegionsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegionsCultureDTO} or a {@link Page} of {@link RegionsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionsCultureQueryService extends QueryService<RegionsCulture> {

    private final Logger log = LoggerFactory.getLogger(RegionsCultureQueryService.class);

    private final RegionsCultureRepository regionsCultureRepository;

    private final RegionsCultureMapper regionsCultureMapper;

    public RegionsCultureQueryService(RegionsCultureRepository regionsCultureRepository, RegionsCultureMapper regionsCultureMapper) {
        this.regionsCultureRepository = regionsCultureRepository;
        this.regionsCultureMapper = regionsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link RegionsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionsCultureDTO> findByCriteria(RegionsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegionsCulture> specification = createSpecification(criteria);
        return regionsCultureMapper.toDto(regionsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionsCultureDTO> findByCriteria(RegionsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegionsCulture> specification = createSpecification(criteria);
        return regionsCultureRepository.findAll(specification, page)
            .map(regionsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegionsCulture> specification = createSpecification(criteria);
        return regionsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link RegionsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegionsCulture> createSpecification(RegionsCultureCriteria criteria) {
        Specification<RegionsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegionsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RegionsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(RegionsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(),
                    root -> root.join(RegionsCulture_.region, JoinType.LEFT).get(Regions_.id)));
            }
        }
        return specification;
    }
}
