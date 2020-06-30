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

import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.OrderPackagesRepository;
import com.vertical.commerce.service.dto.OrderPackagesCriteria;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.mapper.OrderPackagesMapper;

/**
 * Service for executing complex queries for {@link OrderPackages} entities in the database.
 * The main input is a {@link OrderPackagesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderPackagesDTO} or a {@link Page} of {@link OrderPackagesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderPackagesQueryService extends QueryService<OrderPackages> {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesQueryService.class);

    private final OrderPackagesRepository orderPackagesRepository;

    private final OrderPackagesMapper orderPackagesMapper;

    public OrderPackagesQueryService(OrderPackagesRepository orderPackagesRepository, OrderPackagesMapper orderPackagesMapper) {
        this.orderPackagesRepository = orderPackagesRepository;
        this.orderPackagesMapper = orderPackagesMapper;
    }

    /**
     * Return a {@link List} of {@link OrderPackagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderPackagesDTO> findByCriteria(OrderPackagesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderPackages> specification = createSpecification(criteria);
        return orderPackagesMapper.toDto(orderPackagesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrderPackagesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderPackagesDTO> findByCriteria(OrderPackagesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderPackages> specification = createSpecification(criteria);
        return orderPackagesRepository.findAll(specification, page)
            .map(orderPackagesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderPackagesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderPackages> specification = createSpecification(criteria);
        return orderPackagesRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderPackagesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderPackages> createSpecification(OrderPackagesCriteria criteria) {
        Specification<OrderPackages> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderPackages_.id));
            }
            if (criteria.getExpectedDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedDeliveryDate(), OrderPackages_.expectedDeliveryDate));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), OrderPackages_.comments));
            }
            if (criteria.getDeliveryInstructions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryInstructions(), OrderPackages_.deliveryInstructions));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), OrderPackages_.internalComments));
            }
            if (criteria.getPackageShippingFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackageShippingFee(), OrderPackages_.packageShippingFee));
            }
            if (criteria.getPackageShippingFeeDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackageShippingFeeDiscount(), OrderPackages_.packageShippingFeeDiscount));
            }
            if (criteria.getPackagePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackagePrice(), OrderPackages_.packagePrice));
            }
            if (criteria.getPackageSubTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackageSubTotal(), OrderPackages_.packageSubTotal));
            }
            if (criteria.getPackageTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackageTaxAmount(), OrderPackages_.packageTaxAmount));
            }
            if (criteria.getPackageVoucherDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackageVoucherDiscount(), OrderPackages_.packageVoucherDiscount));
            }
            if (criteria.getPackagePromotionDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPackagePromotionDiscount(), OrderPackages_.packagePromotionDiscount));
            }
            if (criteria.getPickingCompletedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPickingCompletedWhen(), OrderPackages_.pickingCompletedWhen));
            }
            if (criteria.getCustomerReviewedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerReviewedOn(), OrderPackages_.customerReviewedOn));
            }
            if (criteria.getSellerRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellerRating(), OrderPackages_.sellerRating));
            }
            if (criteria.getDeliveryRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryRating(), OrderPackages_.deliveryRating));
            }
            if (criteria.getReviewAsAnonymous() != null) {
                specification = specification.and(buildSpecification(criteria.getReviewAsAnonymous(), OrderPackages_.reviewAsAnonymous));
            }
            if (criteria.getCompletedReview() != null) {
                specification = specification.and(buildSpecification(criteria.getCompletedReview(), OrderPackages_.completedReview));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), OrderPackages_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), OrderPackages_.lastEditedWhen));
            }
            if (criteria.getOrderLineListId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderLineListId(),
                    root -> root.join(OrderPackages_.orderLineLists, JoinType.LEFT).get(OrderLines_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(OrderPackages_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getDeliveryMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMethodId(),
                    root -> root.join(OrderPackages_.deliveryMethod, JoinType.LEFT).get(DeliveryMethods_.id)));
            }
            if (criteria.getSpecialDealsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialDealsId(),
                    root -> root.join(OrderPackages_.specialDeals, JoinType.LEFT).get(SpecialDeals_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(OrderPackages_.order, JoinType.LEFT).get(Orders_.id)));
            }
        }
        return specification;
    }
}
