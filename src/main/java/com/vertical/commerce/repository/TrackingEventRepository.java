package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TrackingEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TrackingEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long>, JpaSpecificationExecutor<TrackingEvent> {
}
