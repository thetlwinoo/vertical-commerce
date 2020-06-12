package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.SupplierTransactionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.SupplierTransactions}.
 */
public interface SupplierTransactionsService {

    /**
     * Save a supplierTransactions.
     *
     * @param supplierTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierTransactionsDTO save(SupplierTransactionsDTO supplierTransactionsDTO);

    /**
     * Get all the supplierTransactions.
     *
     * @return the list of entities.
     */
    List<SupplierTransactionsDTO> findAll();


    /**
     * Get the "id" supplierTransactions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierTransactionsDTO> findOne(Long id);

    /**
     * Delete the "id" supplierTransactions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
