package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CreditCardType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CreditCardType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditCardTypeRepository extends JpaRepository<CreditCardType, Long>, JpaSpecificationExecutor<CreditCardType> {
}
