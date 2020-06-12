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

import com.vertical.commerce.domain.Receipts;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.ReceiptsRepository;
import com.vertical.commerce.service.dto.ReceiptsCriteria;
import com.vertical.commerce.service.dto.ReceiptsDTO;
import com.vertical.commerce.service.mapper.ReceiptsMapper;

/**
 * Service for executing complex queries for {@link Receipts} entities in the database.
 * The main input is a {@link ReceiptsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ReceiptsDTO} or a {@link Page} of {@link ReceiptsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ReceiptsQueryService extends QueryService<Receipts> {

    private final Logger log = LoggerFactory.getLogger(ReceiptsQueryService.class);

    private final ReceiptsRepository receiptsRepository;

    private final ReceiptsMapper receiptsMapper;

    public ReceiptsQueryService(ReceiptsRepository receiptsRepository, ReceiptsMapper receiptsMapper) {
        this.receiptsRepository = receiptsRepository;
        this.receiptsMapper = receiptsMapper;
    }

    /**
     * Return a {@link List} of {@link ReceiptsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ReceiptsDTO> findByCriteria(ReceiptsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Receipts> specification = createSpecification(criteria);
        return receiptsMapper.toDto(receiptsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ReceiptsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ReceiptsDTO> findByCriteria(ReceiptsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Receipts> specification = createSpecification(criteria);
        return receiptsRepository.findAll(specification, page)
            .map(receiptsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ReceiptsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Receipts> specification = createSpecification(criteria);
        return receiptsRepository.count(specification);
    }

    /**
     * Function to convert {@link ReceiptsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Receipts> createSpecification(ReceiptsCriteria criteria) {
        Specification<Receipts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Receipts_.id));
            }
            if (criteria.getReceiptNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiptNo(), Receipts_.receiptNo));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), Receipts_.issuedDate));
            }
            if (criteria.getPrintCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrintCount(), Receipts_.printCount));
            }
            if (criteria.getLastEditedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditedBy(), Receipts_.lastEditedBy));
            }
            if (criteria.getLastEditedWhen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastEditedWhen(), Receipts_.lastEditedWhen));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Receipts_.order, JoinType.LEFT).get(Orders_.id)));
            }
            if (criteria.getInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getInvoiceId(),
                    root -> root.join(Receipts_.invoice, JoinType.LEFT).get(Invoices_.id)));
            }
        }
        return specification;
    }
}
