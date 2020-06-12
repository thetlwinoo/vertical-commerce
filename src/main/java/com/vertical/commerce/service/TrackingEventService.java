package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TrackingEventDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TrackingEvent}.
 */
public interface TrackingEventService {

    /**
     * Save a trackingEvent.
     *
     * @param trackingEventDTO the entity to save.
     * @return the persisted entity.
     */
    TrackingEventDTO save(TrackingEventDTO trackingEventDTO);

    /**
     * Get all the trackingEvents.
     *
     * @return the list of entities.
     */
    List<TrackingEventDTO> findAll();


    /**
     * Get the "id" trackingEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrackingEventDTO> findOne(Long id);

    /**
     * Delete the "id" trackingEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
