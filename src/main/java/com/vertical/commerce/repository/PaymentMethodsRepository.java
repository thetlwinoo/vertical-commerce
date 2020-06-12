package com.vertical.commerce.repository;

import com.vertical.commerce.domain.PaymentMethods;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PaymentMethods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Long>, JpaSpecificationExecutor<PaymentMethods> {
}
