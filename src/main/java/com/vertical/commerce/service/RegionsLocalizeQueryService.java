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

import com.vertical.commerce.domain.RegionsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.RegionsLocalizeRepository;
import com.vertical.commerce.service.dto.RegionsLocalizeCriteria;
import com.vertical.commerce.service.dto.RegionsLocalizeDTO;
import com.vertical.commerce.service.mapper.RegionsLocalizeMapper;

/**
 * Service for executing complex queries for {@link RegionsLocalize} entities in the database.
 * The main input is a {@link RegionsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegionsLocalizeDTO} or a {@link Page} of {@link RegionsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionsLocalizeQueryService extends QueryService<RegionsLocalize> {

    private final Logger log = LoggerFactory.getLogger(RegionsLocalizeQueryService.class);

    private final RegionsLocalizeRepository regionsLocalizeRepository;

    private final RegionsLocalizeMapper regionsLocalizeMapper;

    public RegionsLocalizeQueryService(RegionsLocalizeRepository regionsLocalizeRepository, RegionsLocalizeMapper regionsLocalizeMapper) {
        this.regionsLocalizeRepository = regionsLocalizeRepository;
        this.regionsLocalizeMapper = regionsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link RegionsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionsLocalizeDTO> findByCriteria(RegionsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegionsLocalize> specification = createSpecification(criteria);
        return regionsLocalizeMapper.toDto(regionsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionsLocalizeDTO> findByCriteria(RegionsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegionsLocalize> specification = createSpecification(criteria);
        return regionsLocalizeRepository.findAll(specification, page)
            .map(regionsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegionsLocalize> specification = createSpecification(criteria);
        return regionsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link RegionsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegionsLocalize> createSpecification(RegionsLocalizeCriteria criteria) {
        Specification<RegionsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegionsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), RegionsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(RegionsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(),
                    root -> root.join(RegionsLocalize_.region, JoinType.LEFT).get(Regions_.id)));
            }
        }
        return specification;
    }
}
