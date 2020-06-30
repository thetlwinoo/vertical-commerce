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

import com.vertical.commerce.domain.DiscountDetails;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DiscountDetailsRepository;
import com.vertical.commerce.service.dto.DiscountDetailsCriteria;
import com.vertical.commerce.service.dto.DiscountDetailsDTO;
import com.vertical.commerce.service.mapper.DiscountDetailsMapper;

/**
 * Service for executing complex queries for {@link DiscountDetails} entities in the database.
 * The main input is a {@link DiscountDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DiscountDetailsDTO} or a {@link Page} of {@link DiscountDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscountDetailsQueryService extends QueryService<DiscountDetails> {

    private final Logger log = LoggerFactory.getLogger(DiscountDetailsQueryService.class);

    private final DiscountDetailsRepository discountDetailsRepository;

    private final DiscountDetailsMapper discountDetailsMapper;

    public DiscountDetailsQueryService(DiscountDetailsRepository discountDetailsRepository, DiscountDetailsMapper discountDetailsMapper) {
        this.discountDetailsRepository = discountDetailsRepository;
        this.discountDetailsMapper = discountDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link DiscountDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountDetailsDTO> findByCriteria(DiscountDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DiscountDetails> specification = createSpecification(criteria);
        return discountDetailsMapper.toDto(discountDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DiscountDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscountDetailsDTO> findByCriteria(DiscountDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DiscountDetails> specification = createSpecification(criteria);
        return discountDetailsRepository.findAll(specification, page)
            .map(discountDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscountDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DiscountDetails> specification = createSpecification(criteria);
        return discountDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscountDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DiscountDetails> createSpecification(DiscountDetailsCriteria criteria) {
        Specification<DiscountDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DiscountDetails_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DiscountDetails_.name));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), DiscountDetails_.amount));
            }
            if (criteria.getIsPercentage() != null) {
                specification = specification.and(buildSpecification(criteria.getIsPercentage(), DiscountDetails_.isPercentage));
            }
            if (criteria.getIsAllowCombinationDiscount() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAllowCombinationDiscount(), DiscountDetails_.isAllowCombinationDiscount));
            }
            if (criteria.getIsFinalBillDiscount() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFinalBillDiscount(), DiscountDetails_.isFinalBillDiscount));
            }
            if (criteria.getStartCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartCount(), DiscountDetails_.startCount));
            }
            if (criteria.getEndCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndCount(), DiscountDetails_.endCount));
            }
            if (criteria.getMultiplyCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMultiplyCount(), DiscountDetails_.multiplyCount));
            }
            if (criteria.getMinAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinAmount(), DiscountDetails_.minAmount));
            }
            if (criteria.getMaxAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxAmount(), DiscountDetails_.maxAmount));
            }
            if (criteria.getMinVolumeWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinVolumeWeight(), DiscountDetails_.minVolumeWeight));
            }
            if (criteria.getMaxVolumeWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxVolumeWeight(), DiscountDetails_.maxVolumeWeight));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), DiscountDetails_.modifiedDate));
            }
            if (criteria.getDiscountId() != null) {
                specification = specification.and(buildSpecification(criteria.getDiscountId(),
                    root -> root.join(DiscountDetails_.discount, JoinType.LEFT).get(Discount_.id)));
            }
            if (criteria.getStockItemId() != null) {
                specification = specification.and(buildSpecification(criteria.getStockItemId(),
                    root -> root.join(DiscountDetails_.stockItem, JoinType.LEFT).get(StockItems_.id)));
            }
            if (criteria.getProductCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductCategoryId(),
                    root -> root.join(DiscountDetails_.productCategory, JoinType.LEFT).get(ProductCategory_.id)));
            }
        }
        return specification;
    }
}
