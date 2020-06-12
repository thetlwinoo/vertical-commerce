package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.DeliveryMethodsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.DeliveryMethods}.
 */
public interface DeliveryMethodsService {

    /**
     * Save a deliveryMethods.
     *
     * @param deliveryMethodsDTO the entity to save.
     * @return the persisted entity.
     */
    DeliveryMethodsDTO save(DeliveryMethodsDTO deliveryMethodsDTO);

    /**
     * Get all the deliveryMethods.
     *
     * @return the list of entities.
     */
    List<DeliveryMethodsDTO> findAll();


    /**
     * Get the "id" deliveryMethods.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeliveryMethodsDTO> findOne(Long id);

    /**
     * Delete the "id" deliveryMethods.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
