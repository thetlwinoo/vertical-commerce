package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.OrderPackagesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.OrderPackages}.
 */
public interface OrderPackagesService {

    /**
     * Save a orderPackages.
     *
     * @param orderPackagesDTO the entity to save.
     * @return the persisted entity.
     */
    OrderPackagesDTO save(OrderPackagesDTO orderPackagesDTO);

    /**
     * Get all the orderPackages.
     *
     * @return the list of entities.
     */
    List<OrderPackagesDTO> findAll();


    /**
     * Get the "id" orderPackages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderPackagesDTO> findOne(Long id);

    /**
     * Delete the "id" orderPackages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
