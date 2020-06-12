package com.vertical.commerce.repository;

import com.vertical.commerce.domain.PhoneNumberType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PhoneNumberType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhoneNumberTypeRepository extends JpaRepository<PhoneNumberType, Long>, JpaSpecificationExecutor<PhoneNumberType> {
}
