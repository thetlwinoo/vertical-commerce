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

import com.vertical.commerce.domain.ProductDocuments;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ProductDocumentsRepository;
import com.vertical.commerce.service.dto.ProductDocumentsCriteria;
import com.vertical.commerce.service.dto.ProductDocumentsDTO;
import com.vertical.commerce.service.mapper.ProductDocumentsMapper;

/**
 * Service for executing complex queries for {@link ProductDocuments} entities in the database.
 * The main input is a {@link ProductDocumentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDocumentsDTO} or a {@link Page} of {@link ProductDocumentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductDocumentsQueryService extends QueryService<ProductDocuments> {

    private final Logger log = LoggerFactory.getLogger(ProductDocumentsQueryService.class);

    private final ProductDocumentsRepository productDocumentsRepository;

    private final ProductDocumentsMapper productDocumentsMapper;

    public ProductDocumentsQueryService(ProductDocumentsRepository productDocumentsRepository, ProductDocumentsMapper productDocumentsMapper) {
        this.productDocumentsRepository = productDocumentsRepository;
        this.productDocumentsMapper = productDocumentsMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDocumentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDocumentsDTO> findByCriteria(ProductDocumentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductDocuments> specification = createSpecification(criteria);
        return productDocumentsMapper.toDto(productDocumentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDocumentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDocumentsDTO> findByCriteria(ProductDocumentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductDocuments> specification = createSpecification(criteria);
        return productDocumentsRepository.findAll(specification, page)
            .map(productDocumentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductDocumentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductDocuments> specification = createSpecification(criteria);
        return productDocumentsRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductDocumentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductDocuments> createSpecification(ProductDocumentsCriteria criteria) {
        Specification<ProductDocuments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductDocuments_.id));
            }
            if (criteria.getVideoUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVideoUrl(), ProductDocuments_.videoUrl));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), ProductDocuments_.productType));
            }
            if (criteria.getModelName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelName(), ProductDocuments_.modelName));
            }
            if (criteria.getModelNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModelNumber(), ProductDocuments_.modelNumber));
            }
            if (criteria.getFabricType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFabricType(), ProductDocuments_.fabricType));
            }
            if (criteria.getProductComplianceCertificate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductComplianceCertificate(), ProductDocuments_.productComplianceCertificate));
            }
            if (criteria.getGenuineAndLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getGenuineAndLegal(), ProductDocuments_.genuineAndLegal));
            }
            if (criteria.getCountryOfOrigin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountryOfOrigin(), ProductDocuments_.countryOfOrigin));
            }
            if (criteria.getWarrantyPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPeriod(), ProductDocuments_.warrantyPeriod));
            }
            if (criteria.getWarrantyPolicy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarrantyPolicy(), ProductDocuments_.warrantyPolicy));
            }
            if (criteria.getDangerousGoods() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDangerousGoods(), ProductDocuments_.dangerousGoods));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), ProductDocuments_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), ProductDocuments_.lastEditedWhen));
            }
            if (criteria.getWarrantyTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getWarrantyTypeId(),
                    root -> root.join(ProductDocuments_.warrantyType, JoinType.LEFT).get(WarrantyTypes_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(ProductDocuments_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
