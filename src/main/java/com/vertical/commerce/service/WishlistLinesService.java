package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.WishlistLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.WishlistLines}.
 */
public interface WishlistLinesService {

    /**
     * Save a wishlistLines.
     *
     * @param wishlistLinesDTO the entity to save.
     * @return the persisted entity.
     */
    WishlistLinesDTO save(WishlistLinesDTO wishlistLinesDTO);

    /**
     * Get all the wishlistLines.
     *
     * @return the list of entities.
     */
    List<WishlistLinesDTO> findAll();


    /**
     * Get the "id" wishlistLines.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WishlistLinesDTO> findOne(Long id);

    /**
     * Delete the "id" wishlistLines.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
