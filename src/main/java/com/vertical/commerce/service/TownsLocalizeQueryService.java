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

import com.vertical.commerce.domain.TownsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownsLocalizeRepository;
import com.vertical.commerce.service.dto.TownsLocalizeCriteria;
import com.vertical.commerce.service.dto.TownsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownsLocalizeMapper;

/**
 * Service for executing complex queries for {@link TownsLocalize} entities in the database.
 * The main input is a {@link TownsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownsLocalizeDTO} or a {@link Page} of {@link TownsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownsLocalizeQueryService extends QueryService<TownsLocalize> {

    private final Logger log = LoggerFactory.getLogger(TownsLocalizeQueryService.class);

    private final TownsLocalizeRepository townsLocalizeRepository;

    private final TownsLocalizeMapper townsLocalizeMapper;

    public TownsLocalizeQueryService(TownsLocalizeRepository townsLocalizeRepository, TownsLocalizeMapper townsLocalizeMapper) {
        this.townsLocalizeRepository = townsLocalizeRepository;
        this.townsLocalizeMapper = townsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link TownsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownsLocalizeDTO> findByCriteria(TownsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TownsLocalize> specification = createSpecification(criteria);
        return townsLocalizeMapper.toDto(townsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownsLocalizeDTO> findByCriteria(TownsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TownsLocalize> specification = createSpecification(criteria);
        return townsLocalizeRepository.findAll(specification, page)
            .map(townsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TownsLocalize> specification = createSpecification(criteria);
        return townsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link TownsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TownsLocalize> createSpecification(TownsLocalizeCriteria criteria) {
        Specification<TownsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TownsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TownsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(TownsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getTownId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownId(),
                    root -> root.join(TownsLocalize_.town, JoinType.LEFT).get(Towns_.id)));
            }
        }
        return specification;
    }
}
