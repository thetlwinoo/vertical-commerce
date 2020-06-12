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

import com.vertical.commerce.domain.TrackingEvent;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.TrackingEventRepository;
import com.vertical.commerce.service.dto.TrackingEventCriteria;
import com.vertical.commerce.service.dto.TrackingEventDTO;
import com.vertical.commerce.service.mapper.TrackingEventMapper;

/**
 * Service for executing complex queries for {@link TrackingEvent} entities in the database.
 * The main input is a {@link TrackingEventCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrackingEventDTO} or a {@link Page} of {@link TrackingEventDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrackingEventQueryService extends QueryService<TrackingEvent> {

    private final Logger log = LoggerFactory.getLogger(TrackingEventQueryService.class);

    private final TrackingEventRepository trackingEventRepository;

    private final TrackingEventMapper trackingEventMapper;

    public TrackingEventQueryService(TrackingEventRepository trackingEventRepository, TrackingEventMapper trackingEventMapper) {
        this.trackingEventRepository = trackingEventRepository;
        this.trackingEventMapper = trackingEventMapper;
    }

    /**
     * Return a {@link List} of {@link TrackingEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrackingEventDTO> findByCriteria(TrackingEventCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrackingEvent> specification = createSpecification(criteria);
        return trackingEventMapper.toDto(trackingEventRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TrackingEventDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrackingEventDTO> findByCriteria(TrackingEventCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrackingEvent> specification = createSpecification(criteria);
        return trackingEventRepository.findAll(specification, page)
            .map(trackingEventMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrackingEventCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrackingEvent> specification = createSpecification(criteria);
        return trackingEventRepository.count(specification);
    }

    /**
     * Function to convert {@link TrackingEventCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrackingEvent> createSpecification(TrackingEventCriteria criteria) {
        Specification<TrackingEvent> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TrackingEvent_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TrackingEvent_.name));
            }
        }
        return specification;
    }
}
