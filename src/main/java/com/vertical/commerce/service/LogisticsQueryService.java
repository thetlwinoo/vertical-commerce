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

import com.vertical.commerce.domain.Logistics;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.LogisticsRepository;
import com.vertical.commerce.service.dto.LogisticsCriteria;
import com.vertical.commerce.service.dto.LogisticsDTO;
import com.vertical.commerce.service.mapper.LogisticsMapper;

/**
 * Service for executing complex queries for {@link Logistics} entities in the database.
 * The main input is a {@link LogisticsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogisticsDTO} or a {@link Page} of {@link LogisticsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogisticsQueryService extends QueryService<Logistics> {

    private final Logger log = LoggerFactory.getLogger(LogisticsQueryService.class);

    private final LogisticsRepository logisticsRepository;

    private final LogisticsMapper logisticsMapper;

    public LogisticsQueryService(LogisticsRepository logisticsRepository, LogisticsMapper logisticsMapper) {
        this.logisticsRepository = logisticsRepository;
        this.logisticsMapper = logisticsMapper;
    }

    /**
     * Return a {@link List} of {@link LogisticsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogisticsDTO> findByCriteria(LogisticsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Logistics> specification = createSpecification(criteria);
        return logisticsMapper.toDto(logisticsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LogisticsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogisticsDTO> findByCriteria(LogisticsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Logistics> specification = createSpecification(criteria);
        return logisticsRepository.findAll(specification, page)
            .map(logisticsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogisticsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Logistics> specification = createSpecification(criteria);
        return logisticsRepository.count(specification);
    }

    /**
     * Function to convert {@link LogisticsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Logistics> createSpecification(LogisticsCriteria criteria) {
        Specification<Logistics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Logistics_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Logistics_.name));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), Logistics_.activeInd));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Logistics_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Logistics_.lastEditedWhen));
            }
        }
        return specification;
    }
}
