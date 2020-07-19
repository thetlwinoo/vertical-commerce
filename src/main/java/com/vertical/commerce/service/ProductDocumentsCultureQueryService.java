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

import com.vertical.commerce.domain.ProductDocumentsCulture;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductDocumentsCultureRepository;
import com.vertical.commerce.service.dto.ProductDocumentsCultureCriteria;
import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsCultureMapper;

/**
 * Service for executing complex queries for {@link ProductDocumentsCulture} entities in the database.
 * The main input is a {@link ProductDocumentsCultureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDocumentsCultureDTO} or a {@link Page} of {@link ProductDocumentsCultureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductDocumentsCultureQueryService extends QueryService<ProductDocumentsCulture> {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsCultureQueryService.class);

    private final ProductDocumentsCultureRepository productDocumentsCultureRepository;

    private final ProductDocumentsCultureMapper productDocumentsCultureMapper;

    public ProductDocumentsCultureQueryService(ProductDocumentsCultureRepository productDocumentsCultureRepository, ProductDocumentsCultureMapper productDocumentsCultureMapper) {
        this.productDocumentsCultureRepository = productDocumentsCultureRepository;
        this.productDocumentsCultureMapper = productDocumentsCultureMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDocumentsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDocumentsCultureDTO> findByCriteria(ProductDocumentsCultureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductDocumentsCulture> specification = createSpecification(criteria);
        return productDocumentsCultureMapper.toDto(productDocumentsCultureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDocumentsCultureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDocumentsCultureDTO> findByCriteria(ProductDocumentsCultureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductDocumentsCulture> specification = createSpecification(criteria);
        return productDocumentsCultureRepository.findAll(specification, page)
            .map(productDocumentsCultureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductDocumentsCultureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductDocumentsCulture> specification = createSpecification(criteria);
        return productDocumentsCultureRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductDocumentsCultureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductDocumentsCulture> createSpecification(ProductDocumentsCultureCriteria criteria) {
        Specification<ProductDocumentsCulture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductDocumentsCulture_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductDocumentsCulture_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(ProductDocumentsCulture_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getProductDocumentId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductDocumentId(),
                    root -> root.join(ProductDocumentsCulture_.productDocument, JoinType.LEFT).get(ProductDocuments_.id)));
            }
        }
        return specification;
    }
}
