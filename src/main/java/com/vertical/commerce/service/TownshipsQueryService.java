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

import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownshipsRepository;
import com.vertical.commerce.service.dto.TownshipsCriteria;
import com.vertical.commerce.service.dto.TownshipsDTO;
import com.vertical.commerce.service.mapper.TownshipsMapper;

/**
 * Service for executing complex queries for {@link Townships} entities in the database.
 * The main input is a {@link TownshipsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownshipsDTO} or a {@link Page} of {@link TownshipsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownshipsQueryService extends QueryService<Townships> {

    private final Logger log = LoggerFactory.getLogger(TownshipsQueryService.class);

    private final TownshipsRepository townshipsRepository;

    private final TownshipsMapper townshipsMapper;

    public TownshipsQueryService(TownshipsRepository townshipsRepository, TownshipsMapper townshipsMapper) {
        this.townshipsRepository = townshipsRepository;
        this.townshipsMapper = townshipsMapper;
    }

    /**
     * Return a {@link List} of {@link TownshipsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownshipsDTO> findByCriteria(TownshipsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Townships> specification = createSpecification(criteria);
        return townshipsMapper.toDto(townshipsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownshipsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownshipsDTO> findByCriteria(TownshipsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Townships> specification = createSpecification(criteria);
        return townshipsRepository.findAll(specification, page)
            .map(townshipsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownshipsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Townships> specification = createSpecification(criteria);
        return townshipsRepository.count(specification);
    }

    /**
     * Function to convert {@link TownshipsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Townships> createSpecification(TownshipsCriteria criteria) {
        Specification<Townships> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Townships_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Townships_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Townships_.description));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Townships_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Townships_.validTo));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(Townships_.city, JoinType.LEFT).get(Cities_.id)));
            }
        }
        return specification;
    }
}
