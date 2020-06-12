package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StockItemTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.StockItemTransactions}.
 */
public interface StockItemTransactionsService {

    /**
     * Save a stockItemTransactions.
     *
     * @param stockItemTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    StockItemTransactionsDTO save(StockItemTransactionsDTO stockItemTransactionsDTO);

    /**
     * Get all the stockItemTransactions.
     *
     * @return the list of entities.
     */
    List<StockItemTransactionsDTO> findAll();


    /**
     * Get the "id" stockItemTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItemTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" stockItemTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
