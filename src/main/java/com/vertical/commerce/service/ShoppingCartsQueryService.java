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

import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ShoppingCartsRepository;
import com.vertical.commerce.service.dto.ShoppingCartsCriteria;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;

/**
 * Service for executing complex queries for {@link ShoppingCarts} entities in the database.
 * The main input is a {@link ShoppingCartsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShoppingCartsDTO} or a {@link Page} of {@link ShoppingCartsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShoppingCartsQueryService extends QueryService<ShoppingCarts> {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsQueryService.class);

    private final ShoppingCartsRepository shoppingCartsRepository;

    private final ShoppingCartsMapper shoppingCartsMapper;

    public ShoppingCartsQueryService(ShoppingCartsRepository shoppingCartsRepository, ShoppingCartsMapper shoppingCartsMapper) {
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.shoppingCartsMapper = shoppingCartsMapper;
    }

    /**
     * Return a {@link List} of {@link ShoppingCartsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShoppingCartsDTO> findByCriteria(ShoppingCartsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShoppingCarts> specification = createSpecification(criteria);
        return shoppingCartsMapper.toDto(shoppingCartsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShoppingCartsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShoppingCartsDTO> findByCriteria(ShoppingCartsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShoppingCarts> specification = createSpecification(criteria);
        return shoppingCartsRepository.findAll(specification, page)
            .map(shoppingCartsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShoppingCartsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShoppingCarts> specification = createSpecification(criteria);
        return shoppingCartsRepository.count(specification);
    }

    /**
     * Function to convert {@link ShoppingCartsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShoppingCarts> createSpecification(ShoppingCartsCriteria criteria) {
        Specification<ShoppingCarts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShoppingCarts_.id));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), ShoppingCarts_.totalPrice));
            }
            if (criteria.getTotalTaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTaxAmount(), ShoppingCarts_.totalTaxAmount));
            }
            if (criteria.getSubTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubTotalPrice(), ShoppingCarts_.subTotalPrice));
            }
            if (criteria.getTotalShippingFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalShippingFee(), ShoppingCarts_.totalShippingFee));
            }
            if (criteria.getTotalShippingFeeDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalShippingFeeDiscount(), ShoppingCarts_.totalShippingFeeDiscount));
            }
            if (criteria.getPromotionTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPromotionTotal(), ShoppingCarts_.promotionTotal));
            }
            if (criteria.getVoucherTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherTotal(), ShoppingCarts_.voucherTotal));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), ShoppingCarts_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), ShoppingCarts_.lastEditedWhen));
            }
            if (criteria.getCartUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartUserId(),
                    root -> root.join(ShoppingCarts_.cartUser, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getCartItemListId() != null) {
                specification = specification.and(buildSpecification(criteria.getCartItemListId(),
                    root -> root.join(ShoppingCarts_.cartItemLists, JoinType.LEFT).get(ShoppingCartItems_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(ShoppingCarts_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getSpecialDealsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialDealsId(),
                    root -> root.join(ShoppingCarts_.specialDeals, JoinType.LEFT).get(SpecialDeals_.id)));
            }
        }
        return specification;
    }
}
