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

import com.vertical.commerce.domain.PostalCodeMappersLocalize;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.PostalCodeMappersLocalizeRepository;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeCriteria;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;
import com.vertical.commerce.service.mapper.PostalCodeMappersLocalizeMapper;

/**
 * Service for executing complex queries for {@link PostalCodeMappersLocalize} entities in the database.
 * The main input is a {@link PostalCodeMappersLocalizeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostalCodeMappersLocalizeDTO} or a {@link Page} of {@link PostalCodeMappersLocalizeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostalCodeMappersLocalizeQueryService extends QueryService<PostalCodeMappersLocalize> {

    private final Logger log = LoggerFactory.getLogger(PostalCodeMappersLocalizeQueryService.class);

    private final PostalCodeMappersLocalizeRepository postalCodeMappersLocalizeRepository;

    private final PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper;

    public PostalCodeMappersLocalizeQueryService(PostalCodeMappersLocalizeRepository postalCodeMappersLocalizeRepository, PostalCodeMappersLocalizeMapper postalCodeMappersLocalizeMapper) {
        this.postalCodeMappersLocalizeRepository = postalCodeMappersLocalizeRepository;
        this.postalCodeMappersLocalizeMapper = postalCodeMappersLocalizeMapper;
    }

    /**
     * Return a {@link List} of {@link PostalCodeMappersLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostalCodeMappersLocalizeDTO> findByCriteria(PostalCodeMappersLocalizeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostalCodeMappersLocalize> specification = createSpecification(criteria);
        return postalCodeMappersLocalizeMapper.toDto(postalCodeMappersLocalizeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostalCodeMappersLocalizeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostalCodeMappersLocalizeDTO> findByCriteria(PostalCodeMappersLocalizeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostalCodeMappersLocalize> specification = createSpecification(criteria);
        return postalCodeMappersLocalizeRepository.findAll(specification, page)
            .map(postalCodeMappersLocalizeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostalCodeMappersLocalizeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostalCodeMappersLocalize> specification = createSpecification(criteria);
        return postalCodeMappersLocalizeRepository.count(specification);
    }

    /**
     * Function to convert {@link PostalCodeMappersLocalizeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PostalCodeMappersLocalize> createSpecification(PostalCodeMappersLocalizeCriteria criteria) {
        Specification<PostalCodeMappersLocalize> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostalCodeMappersLocalize_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PostalCodeMappersLocalize_.name));
            }
            if (criteria.getCultureId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultureId(),
                    root -> root.join(PostalCodeMappersLocalize_.culture, JoinType.LEFT).get(Culture_.id)));
            }
            if (criteria.getPostalCodeMapperId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostalCodeMapperId(),
                    root -> root.join(PostalCodeMappersLocalize_.postalCodeMapper, JoinType.LEFT).get(PostalCodeMappers_.id)));
            }
        }
        return specification;
    }
}
