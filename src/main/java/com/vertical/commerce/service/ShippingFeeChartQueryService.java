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

import com.vertical.commerce.domain.ShippingFeeChart;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ShippingFeeChartRepository;
import com.vertical.commerce.service.dto.ShippingFeeChartCriteria;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;
import com.vertical.commerce.service.mapper.ShippingFeeChartMapper;

/**
 * Service for executing complex queries for {@link ShippingFeeChart} entities in the database.
 * The main input is a {@link ShippingFeeChartCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShippingFeeChartDTO} or a {@link Page} of {@link ShippingFeeChartDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShippingFeeChartQueryService extends QueryService<ShippingFeeChart> {

    private final Logger log = LoggerFactory.getLogger(ShippingFeeChartQueryService.class);

    private final ShippingFeeChartRepository shippingFeeChartRepository;

    private final ShippingFeeChartMapper shippingFeeChartMapper;

    public ShippingFeeChartQueryService(ShippingFeeChartRepository shippingFeeChartRepository, ShippingFeeChartMapper shippingFeeChartMapper) {
        this.shippingFeeChartRepository = shippingFeeChartRepository;
        this.shippingFeeChartMapper = shippingFeeChartMapper;
    }

    /**
     * Return a {@link List} of {@link ShippingFeeChartDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShippingFeeChartDTO> findByCriteria(ShippingFeeChartCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShippingFeeChart> specification = createSpecification(criteria);
        return shippingFeeChartMapper.toDto(shippingFeeChartRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShippingFeeChartDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShippingFeeChartDTO> findByCriteria(ShippingFeeChartCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShippingFeeChart> specification = createSpecification(criteria);
        return shippingFeeChartRepository.findAll(specification, page)
            .map(shippingFeeChartMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShippingFeeChartCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShippingFeeChart> specification = createSpecification(criteria);
        return shippingFeeChartRepository.count(specification);
    }

    /**
     * Function to convert {@link ShippingFeeChartCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShippingFeeChart> createSpecification(ShippingFeeChartCriteria criteria) {
        Specification<ShippingFeeChart> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShippingFeeChart_.id));
            }
            if (criteria.getSizeOfPercel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSizeOfPercel(), ShippingFeeChart_.sizeOfPercel));
            }
            if (criteria.getMinVolumeWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinVolumeWeight(), ShippingFeeChart_.minVolumeWeight));
            }
            if (criteria.getMaxVolumeWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxVolumeWeight(), ShippingFeeChart_.maxVolumeWeight));
            }
            if (criteria.getMinActualWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinActualWeight(), ShippingFeeChart_.minActualWeight));
            }
            if (criteria.getMaxActualWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxActualWeight(), ShippingFeeChart_.maxActualWeight));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), ShippingFeeChart_.price));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), ShippingFeeChart_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), ShippingFeeChart_.lastEditedWhen));
            }
            if (criteria.getSourceTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceTownshipId(),
                    root -> root.join(ShippingFeeChart_.sourceTownship, JoinType.LEFT).get(Townships_.id)));
            }
            if (criteria.getDestinationTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinationTownshipId(),
                    root -> root.join(ShippingFeeChart_.destinationTownship, JoinType.LEFT).get(Townships_.id)));
            }
            if (criteria.getDeliveryMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMethodId(),
                    root -> root.join(ShippingFeeChart_.deliveryMethod, JoinType.LEFT).get(DeliveryMethods_.id)));
            }
        }
        return specification;
    }
}
