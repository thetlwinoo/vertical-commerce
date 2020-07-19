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

import com.vertical.commerce.domain.DistrictsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DistrictsCultureRepository;
import com.vertical.commerce.service.dto.DistrictsCultureCriteria;
import com.vertical.commerce.service.dto.DistrictsCultureDTO;
import com.vertical.commerce.service.mapper.DistrictsCultureMapper;

/**
 * Service for executing complex queries for {@link DistrictsCulture} entities in the database.
 * The main input is a {@link DistrictsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DistrictsCultureDTO} or a {@link Page} of {@link DistrictsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistrictsCultureQueryService extends QueryService<DistrictsCulture> {

    private final Logger log = LoggerFactory.getLogger(DistrictsCultureQueryService.class);

    private final DistrictsCultureRepository districtsCultureRepository;

    private final DistrictsCultureMapper districtsCultureMapper;

    public DistrictsCultureQueryService(DistrictsCultureRepository districtsCultureRepository, DistrictsCultureMapper districtsCultureMapper) {
        this.districtsCultureRepository = districtsCultureRepository;
        this.districtsCultureMapper = districtsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link DistrictsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DistrictsCultureDTO> findByCriteria(DistrictsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DistrictsCulture> specification = createSpecification(criteria);
        return districtsCultureMapper.toDto(districtsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DistrictsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DistrictsCultureDTO> findByCriteria(DistrictsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DistrictsCulture> specification = createSpecification(criteria);
        return districtsCultureRepository.findAll(specification, page)
            .map(districtsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistrictsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DistrictsCulture> specification = createSpecification(criteria);
        return districtsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link DistrictsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DistrictsCulture> createSpecification(DistrictsCultureCriteria criteria) {
        Specification<DistrictsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DistrictsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DistrictsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(DistrictsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getDistinctId() != null) {
                specification = specification.and(buildSpecification(criteria.getDistinctId(),
                    root -> root.join(DistrictsCulture_.distinct, JoinType.LEFT).get(Districts_.id)));
            }
        }
        return specification;
    }
}
