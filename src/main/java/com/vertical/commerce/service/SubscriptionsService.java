package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.SubscriptionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Subscriptions}.
 */
public interface SubscriptionsService {

    /**
     * Save a subscriptions.
     *
     * @param subscriptionsDTO the entity to save.
     * @return the persisted entity.
     */
    SubscriptionsDTO save(SubscriptionsDTO subscriptionsDTO);

    /**
     * Get all the subscriptions.
     *
     * @return the list of entities.
     */
    List<SubscriptionsDTO> findAll();


    /**
     * Get the "id" subscriptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubscriptionsDTO> findOne(Long id);

    /**
     * Delete the "id" subscriptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
