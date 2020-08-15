package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StockItemsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.StockItemsLocalize}.
 */
public interface StockItemsLocalizeService {

    /**
     * Save a stockItemsLocalize.
     *
     * @param stockItemsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemsLocalizeDTO save(StockItemsLocalizeDTO stockItemsLocalizeDTO);

    /**
     * Get all the stockItemsLocalizes.
     *
     * @return the list of entities.
     */
    List<StockItemsLocalizeDTO> findAll();


    /**
     * Get the "id" stockItemsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
