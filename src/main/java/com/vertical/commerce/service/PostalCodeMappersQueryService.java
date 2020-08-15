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

import com.vertical.commerce.domain.PostalCodeMappers;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.PostalCodeMappersRepository;
import com.vertical.commerce.service.dto.PostalCodeMappersCriteria;
import com.vertical.commerce.service.dto.PostalCodeMappersDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersMapper;

/**
 * Service for executing complex queries for {@link PostalCodeMappers} entities in the database.
 * The main input is a {@link PostalCodeMappersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostalCodeMappersDTO} or a {@link Page} of {@link PostalCodeMappersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostalCodeMappersQueryService extends QueryService<PostalCodeMappers> {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersQueryService.class);

    private final PostalCodeMappersRepository postalCodeMappersRepository;

    private final PostalCodeMappersMapper postalCodeMappersMapper;

    public PostalCodeMappersQueryService(PostalCodeMappersRepository postalCodeMappersRepository, PostalCodeMappersMapper postalCodeMappersMapper) {
        this.postalCodeMappersRepository = postalCodeMappersRepository;
        this.postalCodeMappersMapper = postalCodeMappersMapper;
    }

    /**
     * Return a {@link List} of {@link PostalCodeMappersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostalCodeMappersDTO> findByCriteria(PostalCodeMappersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostalCodeMappers> specification = createSpecification(criteria);
        return postalCodeMappersMapper.toDto(postalCodeMappersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostalCodeMappersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostalCodeMappersDTO> findByCriteria(PostalCodeMappersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostalCodeMappers> specification = createSpecification(criteria);
        return postalCodeMappersRepository.findAll(specification, page)
            .map(postalCodeMappersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostalCodeMappersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostalCodeMappers> specification = createSpecification(criteria);
        return postalCodeMappersRepository.count(specification);
    }

    /**
     * Function to convert {@link PostalCodeMappersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PostalCodeMappers> createSpecification(PostalCodeMappersCriteria criteria) {
        Specification<PostalCodeMappers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostalCodeMappers_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PostalCodeMappers_.name));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), PostalCodeMappers_.postalCode));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), PostalCodeMappers_.description));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), PostalCodeMappers_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), PostalCodeMappers_.validTo));
            }
            if (criteria.getTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownshipId(),
                    root -> root.join(PostalCodeMappers_.township, JoinType.LEFT).get(Townships_.id)));
            }
        }
        return specification;
    }
}
