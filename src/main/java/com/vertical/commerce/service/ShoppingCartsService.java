package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ShoppingCartsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ShoppingCarts}.
 */
public interface ShoppingCartsService {

    /**
     * Save a shoppingCarts.
     *
     * @param shoppingCartsDTO the entity to save.
     * @return the persisted entity.
     */
    ShoppingCartsDTO save(ShoppingCartsDTO shoppingCartsDTO);

    /**
     * Get all the shoppingCarts.
     *
     * @return the list of entities.
     */
    List<ShoppingCartsDTO> findAll();


    /**
     * Get the "id" shoppingCarts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingCartsDTO> findOne(Long id);

    /**
     * Delete the "id" shoppingCarts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
