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

import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.AddressesRepository;
import com.vertical.commerce.service.dto.AddressesCriteria;
import com.vertical.commerce.service.dto.AddressesDTO;
import com.vertical.commerce.service.mapper.AddressesMapper;

/**
 * Service for executing complex queries for {@link Addresses} entities in the database.
 * The main input is a {@link AddressesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AddressesDTO} or a {@link Page} of {@link AddressesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AddressesQueryService extends QueryService<Addresses> {

    private final Logger log = LoggerFactory.getLogger(AddressesQueryService.class);

    private final AddressesRepository addressesRepository;

    private final AddressesMapper addressesMapper;

    public AddressesQueryService(AddressesRepository addressesRepository, AddressesMapper addressesMapper) {
        this.addressesRepository = addressesRepository;
        this.addressesMapper = addressesMapper;
    }

    /**
     * Return a {@link List} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AddressesDTO> findByCriteria(AddressesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesMapper.toDto(addressesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AddressesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AddressesDTO> findByCriteria(AddressesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.findAll(specification, page)
            .map(addressesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AddressesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Addresses> specification = createSpecification(criteria);
        return addressesRepository.count(specification);
    }

    /**
     * Function to convert {@link AddressesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Addresses> createSpecification(AddressesCriteria criteria) {
        Specification<Addresses> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Addresses_.id));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), Addresses_.contactPerson));
            }
            if (criteria.getContactNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNumber(), Addresses_.contactNumber));
            }
            if (criteria.getContactEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactEmailAddress(), Addresses_.contactEmailAddress));
            }
            if (criteria.getAddressLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine1(), Addresses_.addressLine1));
            }
            if (criteria.getAddressLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddressLine2(), Addresses_.addressLine2));
            }
            if (criteria.getPostalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPostalCode(), Addresses_.postalCode));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Addresses_.description));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Addresses_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Addresses_.validTo));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(),
                    root -> root.join(Addresses_.region, JoinType.LEFT).get(Regions_.id)));
            }
            if (criteria.getCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getCityId(),
                    root -> root.join(Addresses_.city, JoinType.LEFT).get(Cities_.id)));
            }
            if (criteria.getTownshipId() != null) {
                specification = specification.and(buildSpecification(criteria.getTownshipId(),
                    root -> root.join(Addresses_.township, JoinType.LEFT).get(Townships_.id)));
            }
            if (criteria.getAddressTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressTypeId(),
                    root -> root.join(Addresses_.addressType, JoinType.LEFT).get(AddressTypes_.id)));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildSpecification(criteria.getCustomerId(),
                    root -> root.join(Addresses_.customer, JoinType.LEFT).get(Customers_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Addresses_.supplier, JoinType.LEFT).get(Suppliers_.id)));
            }
        }
        return specification;
    }
}
