package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Subscriptions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subscriptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long>, JpaSpecificationExecutor<Subscriptions> {
}
