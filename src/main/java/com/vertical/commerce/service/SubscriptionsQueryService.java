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

import com.vertical.commerce.domain.Subscriptions;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.SubscriptionsRepository;
import com.vertical.commerce.service.dto.SubscriptionsCriteria;
import com.vertical.commerce.service.dto.SubscriptionsDTO;
import com.vertical.commerce.service.mapper.SubscriptionsMapper;

/**
 * Service for executing complex queries for {@link Subscriptions} entities in the database.
 * The main input is a {@link SubscriptionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubscriptionsDTO} or a {@link Page} of {@link SubscriptionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionsQueryService extends QueryService<Subscriptions> {

    private final Logger log = LoggerFactory.getLogger(SubscriptionsQueryService.class);

    private final SubscriptionsRepository subscriptionsRepository;

    private final SubscriptionsMapper subscriptionsMapper;

    public SubscriptionsQueryService(SubscriptionsRepository subscriptionsRepository, SubscriptionsMapper subscriptionsMapper) {
        this.subscriptionsRepository = subscriptionsRepository;
        this.subscriptionsMapper = subscriptionsMapper;
    }

    /**
     * Return a {@link List} of {@link SubscriptionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubscriptionsDTO> findByCriteria(SubscriptionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Subscriptions> specification = createSpecification(criteria);
        return subscriptionsMapper.toDto(subscriptionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubscriptionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubscriptionsDTO> findByCriteria(SubscriptionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Subscriptions> specification = createSpecification(criteria);
        return subscriptionsRepository.findAll(specification, page)
            .map(subscriptionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubscriptionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Subscriptions> specification = createSpecification(criteria);
        return subscriptionsRepository.count(specification);
    }

    /**
     * Function to convert {@link SubscriptionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Subscriptions> createSpecification(SubscriptionsCriteria criteria) {
        Specification<Subscriptions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Subscriptions_.id));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), Subscriptions_.emailAddress));
            }
            if (criteria.getSubscribedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubscribedOn(), Subscriptions_.subscribedOn));
            }
            if (criteria.getActiveFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveFlag(), Subscriptions_.activeFlag));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Subscriptions_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Subscriptions_.validTo));
            }
        }
        return specification;
    }
}
