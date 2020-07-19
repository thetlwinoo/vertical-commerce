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

import com.vertical.commerce.domain.MaterialsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.MaterialsCultureRepository;
import com.vertical.commerce.service.dto.MaterialsCultureCriteria;
import com.vertical.commerce.service.dto.MaterialsCultureDTO;
import com.vertical.commerce.service.mapper.MaterialsCultureMapper;

/**
 * Service for executing complex queries for {@link MaterialsCulture} entities in the database.
 * The main input is a {@link MaterialsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaterialsCultureDTO} or a {@link Page} of {@link MaterialsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialsCultureQueryService extends QueryService<MaterialsCulture> {

    private final Logger log = LoggerFactory.getLogger(MaterialsCultureQueryService.class);

    private final MaterialsCultureRepository materialsCultureRepository;

    private final MaterialsCultureMapper materialsCultureMapper;

    public MaterialsCultureQueryService(MaterialsCultureRepository materialsCultureRepository, MaterialsCultureMapper materialsCultureMapper) {
        this.materialsCultureRepository = materialsCultureRepository;
        this.materialsCultureMapper = materialsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link MaterialsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaterialsCultureDTO> findByCriteria(MaterialsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MaterialsCulture> specification = createSpecification(criteria);
        return materialsCultureMapper.toDto(materialsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MaterialsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialsCultureDTO> findByCriteria(MaterialsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaterialsCulture> specification = createSpecification(criteria);
        return materialsCultureRepository.findAll(specification, page)
            .map(materialsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MaterialsCulture> specification = createSpecification(criteria);
        return materialsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaterialsCulture> createSpecification(MaterialsCultureCriteria criteria) {
        Specification<MaterialsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MaterialsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MaterialsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(MaterialsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildSpecification(criteria.getMaterialId(),
                    root -> root.join(MaterialsCulture_.material, JoinType.LEFT).get(Materials_.id)));
            }
        }
        return specification;
    }
}
