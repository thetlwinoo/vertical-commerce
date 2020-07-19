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

import com.vertical.commerce.domain.Districts;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.DistrictsRepository;
import com.vertical.commerce.service.dto.DistrictsCriteria;
import com.vertical.commerce.service.dto.DistrictsDTO;
import com.vertical.commerce.service.mapper.DistrictsMapper;

/**
 * Service for executing complex queries for {@link Districts} entities in the database.
 * The main input is a {@link DistrictsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DistrictsDTO} or a {@link Page} of {@link DistrictsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DistrictsQueryService extends QueryService<Districts> {

    private final Logger log = LoggerFactory.getLogger(DistrictsQueryService.class);

    private final DistrictsRepository districtsRepository;

    private final DistrictsMapper districtsMapper;

    public DistrictsQueryService(DistrictsRepository districtsRepository, DistrictsMapper districtsMapper) {
        this.districtsRepository = districtsRepository;
        this.districtsMapper = districtsMapper;
    }

    /**
     * Return a {@link List} of {@link DistrictsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DistrictsDTO> findByCriteria(DistrictsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Districts> specification = createSpecification(criteria);
        return districtsMapper.toDto(districtsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DistrictsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DistrictsDTO> findByCriteria(DistrictsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Districts> specification = createSpecification(criteria);
        return districtsRepository.findAll(specification, page)
            .map(districtsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DistrictsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Districts> specification = createSpecification(criteria);
        return districtsRepository.count(specification);
    }

    /**
     * Function to convert {@link DistrictsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Districts> createSpecification(DistrictsCriteria criteria) {
        Specification<Districts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Districts_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Districts_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Districts_.name));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Districts_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Districts_.validTo));
            }
        }
        return specification;
    }
}
