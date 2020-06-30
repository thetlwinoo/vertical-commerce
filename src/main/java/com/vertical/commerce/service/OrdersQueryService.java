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

import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.dto.OrdersCriteria;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrdersMapper;

/**
 * Service for executing complex queries for {@link Orders} entities in the database.
 * The main input is a {@link OrdersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrdersDTO} or a {@link Page} of {@link OrdersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrdersQueryService extends QueryService<Orders> {

    private final Logger log = LoggerFactory.getLogger(OrdersQueryService.class);

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    public OrdersQueryService(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    /**
     * Return a {@link List} of {@link OrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrdersDTO> findByCriteria(OrdersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersMapper.toDto(ordersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrdersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrdersDTO> findByCriteria(OrdersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersRepository.findAll(specification, page)
            .map(ordersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrdersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Orders> specification = createSpecification(criteria);
        return ordersRepository.count(specification);
    }

    /**
     * Function to convert {@link OrdersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Orders> createSpecification(OrdersCriteria criteria) {
        Specification<Orders> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Orders_.id));
            }
            if (criteria.getOrderDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderDate(), Orders_.orderDate));
            }
            if (criteria.getSubTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubTotal(), Orders_.subTotal));
            }
            if (criteria.getTotalTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTaxAmount(), Orders_.totalTaxAmount));
            }
            if (criteria.getTotalShippingFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalShippingFee(), Orders_.totalShippingFee));
            }
            if (criteria.getTotalShippingFeeDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalShippingFeeDiscount(), Orders_.totalShippingFeeDiscount));
            }
            if (criteria.getTotalVoucherDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalVoucherDiscount(), Orders_.totalVoucherDiscount));
            }
            if (criteria.getTotalPromtionDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPromtionDiscount(), Orders_.totalPromtionDiscount));
            }
            if (criteria.getTotalDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDue(), Orders_.totalDue));
            }
            if (criteria.getPaymentStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentStatus(), Orders_.paymentStatus));
            }
            if (criteria.getCustomerPurchaseOrderNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerPurchaseOrderNumber(), Orders_.customerPurchaseOrderNumber));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Orders_.status));
            }
            if (criteria.getIsUnderSupplyBackOrdered() != null) {
                specification = specification.and(buildSpecification(criteria.getIsUnderSupplyBackOrdered(), Orders_.isUnderSupplyBackOrdered));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Orders_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Orders_.lastEditedWhen));
            }
            if (criteria.getOrderPackageListId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderPackageListId(),
                    root -> root.join(Orders_.orderPackageLists, JoinType.LEFT).get(OrderPackages_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Orders_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getShipToAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getShipToAddressId(),
                    root -> root.join(Orders_.shipToAddress, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getBillToAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getBillToAddressId(),
                    root -> root.join(Orders_.billToAddress, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getCurrencyRateId() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrencyRateId(),
                    root -> root.join(Orders_.currencyRate, JoinType.LEFT).get(CurrencyRate_.id)));
            }
            if (criteria.getPaymentMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethodId(),
                    root -> root.join(Orders_.paymentMethod, JoinType.LEFT).get(PaymentMethods_.id)));
            }
            if (criteria.getSalePersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getSalePersonId(),
                    root -> root.join(Orders_.salePerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getOrderTrackingId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderTrackingId(),
                    root -> root.join(Orders_.orderTracking, JoinType.LEFT).get(OrderTracking_.id)));
            }
            if (criteria.getSpecialDealsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialDealsId(),
                    root -> root.join(Orders_.specialDeals, JoinType.LEFT).get(SpecialDeals_.id)));
            }
        }
        return specification;
    }
}
