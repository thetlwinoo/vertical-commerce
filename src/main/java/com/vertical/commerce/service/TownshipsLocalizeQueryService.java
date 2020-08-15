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

import com.vertical.commerce.domain.TownshipsLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TownshipsLocalizeRepository;
import com.vertical.commerce.service.dto.TownshipsLocalizeCriteria;
import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownshipsLocalizeMapper;

/**
 * Service for executing complex queries for {@link TownshipsLocalize} entities in the database.
 * The main input is a {@link TownshipsLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TownshipsLocalizeDTO} or a {@link Page} of {@link TownshipsLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TownshipsLocalizeQueryService extends QueryService<TownshipsLocalize> {

    private final Logger log = LoggerFactory.getLogger(TownshipsLocalizeQueryService.class);

    private final TownshipsLocalizeRepository townshipsLocalizeRepository;

    private final TownshipsLocalizeMapper townshipsLocalizeMapper;

    public TownshipsLocalizeQueryService(TownshipsLocalizeRepository townshipsLocalizeRepository, TownshipsLocalizeMapper townshipsLocalizeMapper) {
        this.townshipsLocalizeRepository = townshipsLocalizeRepository;
        this.townshipsLocalizeMapper = townshipsLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link TownshipsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TownshipsLocalizeDTO> findByCriteria(TownshipsLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TownshipsLocalize> specification = createSpecification(criteria);
        return townshipsLocalizeMapper.toDto(townshipsLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TownshipsLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TownshipsLocalizeDTO> findByCriteria(TownshipsLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TownshipsLocalize> specification = createSpecification(criteria);
        return townshipsLocalizeRepository.findAll(specification, page)
            .map(townshipsLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TownshipsLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TownshipsLocalize> specification = createSpecification(criteria);
        return townshipsLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link TownshipsLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TownshipsLocalize> createSpecification(TownshipsLocalizeCriteria criteria) {
        Specification<TownshipsLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TownshipsLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TownshipsLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(TownshipsLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownshipId(),
                    root -> root.join(TownshipsLocalize_.township, JoinType.LEFT).get(Townships_.id)));
            }
        }
        return specification;
    }
}
