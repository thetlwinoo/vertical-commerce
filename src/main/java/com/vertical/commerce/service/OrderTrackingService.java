package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.OrderTrackingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.OrderTracking}.
 */
public interface OrderTrackingService {

    /**
     * Save a orderTracking.
     *
     * @param orderTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    OrderTrackingDTO save(OrderTrackingDTO orderTrackingDTO);

    /**
     * Get all the orderTrackings.
     *
     * @return the list of entities.
     */
    List<OrderTrackingDTO> findAll();


    /**
     * Get the "id" orderTracking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderTrackingDTO> findOne(Long id);

    /**
     * Delete the "id" orderTracking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
