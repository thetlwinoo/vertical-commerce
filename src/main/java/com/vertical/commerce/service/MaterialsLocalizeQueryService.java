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

import com.vertical.commerce.domain.MaterialsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.MaterialsLocalizeRepository;
import com.vertical.commerce.service.dto.MaterialsLocalizeCriteria;
import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;
import com.vertical.commerce.service.mapper.MaterialsLocalizeMapper;

/**
 * Service for executing complex queries for {@link MaterialsLocalize} entities in the database.
 * The main input is a {@link MaterialsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MaterialsLocalizeDTO} or a {@link Page} of {@link MaterialsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialsLocalizeQueryService extends QueryService<MaterialsLocalize> {

    private final Logger log = LoggerFactory.getLogger(MaterialsLocalizeQueryService.class);

    private final MaterialsLocalizeRepository materialsLocalizeRepository;

    private final MaterialsLocalizeMapper materialsLocalizeMapper;

    public MaterialsLocalizeQueryService(MaterialsLocalizeRepository materialsLocalizeRepository, MaterialsLocalizeMapper materialsLocalizeMapper) {
        this.materialsLocalizeRepository = materialsLocalizeRepository;
        this.materialsLocalizeMapper = materialsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link MaterialsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MaterialsLocalizeDTO> findByCriteria(MaterialsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MaterialsLocalize> specification = createSpecification(criteria);
        return materialsLocalizeMapper.toDto(materialsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MaterialsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MaterialsLocalizeDTO> findByCriteria(MaterialsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MaterialsLocalize> specification = createSpecification(criteria);
        return materialsLocalizeRepository.findAll(specification, page)
            .map(materialsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MaterialsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MaterialsLocalize> specification = createSpecification(criteria);
        return materialsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link MaterialsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MaterialsLocalize> createSpecification(MaterialsLocalizeCriteria criteria) {
        Specification<MaterialsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MaterialsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MaterialsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(MaterialsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildSpecification(criteria.getMaterialId(),
                    root -> root.join(MaterialsLocalize_.material, JoinType.LEFT).get(Materials_.id)));
            }
        }
        return specification;
    }
}
