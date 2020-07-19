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

import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DeliveryMethodsRepository;
import com.vertical.commerce.service.dto.DeliveryMethodsCriteria;
import com.vertical.commerce.service.dto.DeliveryMethodsDTO;
import com.vertical.commerce.service.mapper.DeliveryMethodsMapper;

/**
 * Service for executing complex queries for {@link DeliveryMethods} entities in the database.
 * The main input is a {@link DeliveryMethodsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeliveryMethodsDTO} or a {@link Page} of {@link DeliveryMethodsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeliveryMethodsQueryService extends QueryService<DeliveryMethods> {

    private final Logger log = LoggerFactory.getLogger(DeliveryMethodsQueryService.class);

    private final DeliveryMethodsRepository deliveryMethodsRepository;

    private final DeliveryMethodsMapper deliveryMethodsMapper;

    public DeliveryMethodsQueryService(DeliveryMethodsRepository deliveryMethodsRepository, DeliveryMethodsMapper deliveryMethodsMapper) {
        this.deliveryMethodsRepository = deliveryMethodsRepository;
        this.deliveryMethodsMapper = deliveryMethodsMapper;
    }

    /**
     * Return a {@link List} of {@link DeliveryMethodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeliveryMethodsDTO> findByCriteria(DeliveryMethodsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeliveryMethods> specification = createSpecification(criteria);
        return deliveryMethodsMapper.toDto(deliveryMethodsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeliveryMethodsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeliveryMethodsDTO> findByCriteria(DeliveryMethodsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeliveryMethods> specification = createSpecification(criteria);
        return deliveryMethodsRepository.findAll(specification, page)
            .map(deliveryMethodsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeliveryMethodsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeliveryMethods> specification = createSpecification(criteria);
        return deliveryMethodsRepository.count(specification);
    }

    /**
     * Function to convert {@link DeliveryMethodsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeliveryMethods> createSpecification(DeliveryMethodsCriteria criteria) {
        Specification<DeliveryMethods> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeliveryMethods_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DeliveryMethods_.name));
            }
            if (criteria.getThirdPartyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThirdPartyName(), DeliveryMethods_.thirdPartyName));
            }
            if (criteria.getExpectedMinArrivalDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedMinArrivalDays(), DeliveryMethods_.expectedMinArrivalDays));
            }
            if (criteria.getExpectedMaxArrivalDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedMaxArrivalDays(), DeliveryMethods_.expectedMaxArrivalDays));
            }
            if (criteria.getDefaultInd() != null) {
                specification = specification.and(buildSpecification(criteria.getDefaultInd(), DeliveryMethods_.defaultInd));
            }
            if (criteria.getDeliveryNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryNote(), DeliveryMethods_.deliveryNote));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), DeliveryMethods_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), DeliveryMethods_.validTo));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(DeliveryMethods_.suppliers, JoinType.LEFT).get(Suppliers_.id)));
            }
        }
        return specification;
    }
}
