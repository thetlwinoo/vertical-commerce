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

import com.vertical.commerce.domain.WishlistLines;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.WishlistLinesRepository;
import com.vertical.commerce.service.dto.WishlistLinesCriteria;
import com.vertical.commerce.service.dto.WishlistLinesDTO;
import com.vertical.commerce.service.mapper.WishlistLinesMapper;

/**
 * Service for executing complex queries for {@link WishlistLines} entities in the database.
 * The main input is a {@link WishlistLinesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WishlistLinesDTO} or a {@link Page} of {@link WishlistLinesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WishlistLinesQueryService extends QueryService<WishlistLines> {

    private final Logger log = LoggerFactory.getLogger(WishlistLinesQueryService.class);

    private final WishlistLinesRepository wishlistLinesRepository;

    private final WishlistLinesMapper wishlistLinesMapper;

    public WishlistLinesQueryService(WishlistLinesRepository wishlistLinesRepository, WishlistLinesMapper wishlistLinesMapper) {
        this.wishlistLinesRepository = wishlistLinesRepository;
        this.wishlistLinesMapper = wishlistLinesMapper;
    }

    /**
     * Return a {@link List} of {@link WishlistLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WishlistLinesDTO> findByCriteria(WishlistLinesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WishlistLines> specification = createSpecification(criteria);
        return wishlistLinesMapper.toDto(wishlistLinesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WishlistLinesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WishlistLinesDTO> findByCriteria(WishlistLinesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WishlistLines> specification = createSpecification(criteria);
        return wishlistLinesRepository.findAll(specification, page)
            .map(wishlistLinesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WishlistLinesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WishlistLines> specification = createSpecification(criteria);
        return wishlistLinesRepository.count(specification);
    }

    /**
     * Function to convert {@link WishlistLinesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WishlistLines> createSpecification(WishlistLinesCriteria criteria) {
        Specification<WishlistLines> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WishlistLines_.id));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(WishlistLines_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getWishlistId() != null) {
                specification = specification.and(buildSpecification(criteria.getWishlistId(),
                    root -> root.join(WishlistLines_.wishlist, JoinType.LEFT).get(Wishlists_.id)));
            }
        }
        return specification;
    }
}
