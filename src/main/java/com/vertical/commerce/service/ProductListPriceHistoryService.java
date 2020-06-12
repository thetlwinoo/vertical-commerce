package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductListPriceHistory}.
 */
public interface ProductListPriceHistoryService {

    /**
     * Save a productListPriceHistory.
     *
     * @param productListPriceHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    ProductListPriceHistoryDTO save(ProductListPriceHistoryDTO productListPriceHistoryDTO);

    /**
     * Get all the productListPriceHistories.
     *
     * @return the list of entities.
     */
    List<ProductListPriceHistoryDTO> findAll();


    /**
     * Get the "id" productListPriceHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductListPriceHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" productListPriceHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
