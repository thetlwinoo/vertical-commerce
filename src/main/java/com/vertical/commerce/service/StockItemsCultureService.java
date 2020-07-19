package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StockItemsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.StockItemsCulture}.
 */
public interface StockItemsCultureService {

    /**
     * Save a stockItemsCulture.
     *
     * @param stockItemsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemsCultureDTO save(StockItemsCultureDTO stockItemsCultureDTO);

    /**
     * Get all the stockItemsCultures.
     *
     * @return the list of entities.
     */
    List<StockItemsCultureDTO> findAll();


    /**
     * Get the "id" stockItemsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
