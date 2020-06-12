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

import com.vertical.commerce.domain.OrderTracking;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.OrderTrackingRepository;
import com.vertical.commerce.service.dto.OrderTrackingCriteria;
import com.vertical.commerce.service.dto.OrderTrackingDTO;
import com.vertical.commerce.service.mapper.OrderTrackingMapper;

/**
 * Service for executing complex queries for {@link OrderTracking} entities in the database.
 * The main input is a {@link OrderTrackingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderTrackingDTO} or a {@link Page} of {@link OrderTrackingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderTrackingQueryService extends QueryService<OrderTracking> {

    private final Logger log = LoggerFactory.getLogger(OrderTrackingQueryService.class);

    private final OrderTrackingRepository orderTrackingRepository;

    private final OrderTrackingMapper orderTrackingMapper;

    public OrderTrackingQueryService(OrderTrackingRepository orderTrackingRepository, OrderTrackingMapper orderTrackingMapper) {
        this.orderTrackingRepository = orderTrackingRepository;
        this.orderTrackingMapper = orderTrackingMapper;
    }

    /**
     * Return a {@link List} of {@link OrderTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderTrackingDTO> findByCriteria(OrderTrackingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderTracking> specification = createSpecification(criteria);
        return orderTrackingMapper.toDto(orderTrackingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrderTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderTrackingDTO> findByCriteria(OrderTrackingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderTracking> specification = createSpecification(criteria);
        return orderTrackingRepository.findAll(specification, page)
            .map(orderTrackingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderTrackingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderTracking> specification = createSpecification(criteria);
        return orderTrackingRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderTrackingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderTracking> createSpecification(OrderTrackingCriteria criteria) {
        Specification<OrderTracking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderTracking_.id));
            }
            if (criteria.getCarrierTrackingNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCarrierTrackingNumber(), OrderTracking_.carrierTrackingNumber));
            }
            if (criteria.getEventDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEventDate(), OrderTracking_.eventDate));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(OrderTracking_.order, JoinType.LEFT).get(Orders_.id)));
            }
            if (criteria.getTrackingEventId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrackingEventId(),
                    root -> root.join(OrderTracking_.trackingEvent, JoinType.LEFT).get(TrackingEvent_.id)));
            }
        }
        return specification;
    }
}
