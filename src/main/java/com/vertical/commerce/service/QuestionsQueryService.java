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

import com.vertical.commerce.domain.Questions;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.QuestionsRepository;
import com.vertical.commerce.service.dto.QuestionsCriteria;
import com.vertical.commerce.service.dto.QuestionsDTO;
import com.vertical.commerce.service.mapper.QuestionsMapper;

/**
 * Service for executing complex queries for {@link Questions} entities in the database.
 * The main input is a {@link QuestionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuestionsDTO} or a {@link Page} of {@link QuestionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuestionsQueryService extends QueryService<Questions> {

    private final Logger log = LoggerFactory.getLogger(QuestionsQueryService.class);

    private final QuestionsRepository questionsRepository;

    private final QuestionsMapper questionsMapper;

    public QuestionsQueryService(QuestionsRepository questionsRepository, QuestionsMapper questionsMapper) {
        this.questionsRepository = questionsRepository;
        this.questionsMapper = questionsMapper;
    }

    /**
     * Return a {@link List} of {@link QuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuestionsDTO> findByCriteria(QuestionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsMapper.toDto(questionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuestionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuestionsDTO> findByCriteria(QuestionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.findAll(specification, page)
            .map(questionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuestionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Questions> specification = createSpecification(criteria);
        return questionsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuestionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Questions> createSpecification(QuestionsCriteria criteria) {
        Specification<Questions> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Questions_.id));
            }
            if (criteria.getCustomerQuestionOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerQuestionOn(), Questions_.customerQuestionOn));
            }
            if (criteria.getSupplierAnswerOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSupplierAnswerOn(), Questions_.supplierAnswerOn));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Questions_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Questions_.validTo));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Questions_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
            if (criteria.getPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonId(),
                    root -> root.join(Questions_.person, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Questions_.product, JoinType.LEFT).get(Products_.id)));
            }
        }
        return specification;
    }
}
