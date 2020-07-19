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

import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.*; // for static metamodels
import com.vertical.commerce.repository.SuppliersRepository;
import com.vertical.commerce.service.dto.SuppliersCriteria;
import com.vertical.commerce.service.dto.SuppliersDTO;
import com.vertical.commerce.service.mapper.SuppliersMapper;

/**
 * Service for executing complex queries for {@link Suppliers} entities in the database.
 * The main input is a {@link SuppliersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SuppliersDTO} or a {@link Page} of {@link SuppliersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SuppliersQueryService extends QueryService<Suppliers> {

    private final Logger log = LoggerFactory.getLogger(SuppliersQueryService.class);

    private final SuppliersRepository suppliersRepository;

    private final SuppliersMapper suppliersMapper;

    public SuppliersQueryService(SuppliersRepository suppliersRepository, SuppliersMapper suppliersMapper) {
        this.suppliersRepository = suppliersRepository;
        this.suppliersMapper = suppliersMapper;
    }

    /**
     * Return a {@link List} of {@link SuppliersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SuppliersDTO> findByCriteria(SuppliersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersMapper.toDto(suppliersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SuppliersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SuppliersDTO> findByCriteria(SuppliersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersRepository.findAll(specification, page)
            .map(suppliersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SuppliersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Suppliers> specification = createSpecification(criteria);
        return suppliersRepository.count(specification);
    }

    /**
     * Function to convert {@link SuppliersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Suppliers> createSpecification(SuppliersCriteria criteria) {
        Specification<Suppliers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Suppliers_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Suppliers_.name));
            }
            if (criteria.getSupplierReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSupplierReference(), Suppliers_.supplierReference));
            }
            if (criteria.getBankAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountName(), Suppliers_.bankAccountName));
            }
            if (criteria.getBankAccountBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountBranch(), Suppliers_.bankAccountBranch));
            }
            if (criteria.getBankAccountCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountCode(), Suppliers_.bankAccountCode));
            }
            if (criteria.getBankAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountNumber(), Suppliers_.bankAccountNumber));
            }
            if (criteria.getBankInternationalCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankInternationalCode(), Suppliers_.bankInternationalCode));
            }
            if (criteria.getPaymentDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDays(), Suppliers_.paymentDays));
            }
            if (criteria.getInternalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInternalComments(), Suppliers_.internalComments));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Suppliers_.phoneNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), Suppliers_.emailAddress));
            }
            if (criteria.getNric() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNric(), Suppliers_.nric));
            }
            if (criteria.getCompanyRegistrationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyRegistrationNo(), Suppliers_.companyRegistrationNo));
            }
            if (criteria.getFaxNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaxNumber(), Suppliers_.faxNumber));
            }
            if (criteria.getWebsiteUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsiteUrl(), Suppliers_.websiteUrl));
            }
            if (criteria.getWebServiceUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebServiceUrl(), Suppliers_.webServiceUrl));
            }
            if (criteria.getCreditRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditRating(), Suppliers_.creditRating));
            }
            if (criteria.getOfficialStoreInd() != null) {
                specification = specification.and(buildSpecification(criteria.getOfficialStoreInd(), Suppliers_.officialStoreInd));
            }
            if (criteria.getStoreName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStoreName(), Suppliers_.storeName));
            }
            if (criteria.getLogo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogo(), Suppliers_.logo));
            }
            if (criteria.getNricFrontPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNricFrontPhoto(), Suppliers_.nricFrontPhoto));
            }
            if (criteria.getNricBackPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNricBackPhoto(), Suppliers_.nricBackPhoto));
            }
            if (criteria.getBankBookPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankBookPhoto(), Suppliers_.bankBookPhoto));
            }
            if (criteria.getCompanyRegistrationPhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyRegistrationPhoto(), Suppliers_.companyRegistrationPhoto));
            }
            if (criteria.getDistributorCertificatePhoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistributorCertificatePhoto(), Suppliers_.distributorCertificatePhoto));
            }
            if (criteria.getActiveFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveFlag(), Suppliers_.activeFlag));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), Suppliers_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), Suppliers_.validTo));
            }
            if (criteria.getBannerListId() != null) {
                specification = specification.and(buildSpecification(criteria.getBannerListId(),
                    root -> root.join(Suppliers_.bannerLists, JoinType.LEFT).get(Photos_.id)));
            }
            if (criteria.getDocumentListId() != null) {
                specification = specification.and(buildSpecification(criteria.getDocumentListId(),
                    root -> root.join(Suppliers_.documentLists, JoinType.LEFT).get(Photos_.id)));
            }
            if (criteria.getSupplierCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierCategoryId(),
                    root -> root.join(Suppliers_.supplierCategory, JoinType.LEFT).get(SupplierCategories_.id)));
            }
            if (criteria.getPickupAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getPickupAddressId(),
                    root -> root.join(Suppliers_.pickupAddress, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getHeadOfficeAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getHeadOfficeAddressId(),
                    root -> root.join(Suppliers_.headOfficeAddress, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getReturnAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getReturnAddressId(),
                    root -> root.join(Suppliers_.returnAddress, JoinType.LEFT).get(Addresses_.id)));
            }
            if (criteria.getContactPersonId() != null) {
                specification = specification.and(buildSpecification(criteria.getContactPersonId(),
                    root -> root.join(Suppliers_.contactPerson, JoinType.LEFT).get(People_.id)));
            }
            if (criteria.getDeliveryMethodId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryMethodId(),
                    root -> root.join(Suppliers_.deliveryMethods, JoinType.LEFT).get(DeliveryMethods_.id)));
            }
            if (criteria.getPeopleId() != null) {
                specification = specification.and(buildSpecification(criteria.getPeopleId(),
                    root -> root.join(Suppliers_.people, JoinType.LEFT).get(People_.id)));
            }
        }
        return specification;
    }
}
