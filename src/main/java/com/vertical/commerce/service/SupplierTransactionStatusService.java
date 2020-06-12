package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.SupplierTransactionStatus}.
 */
public interface SupplierTransactionStatusService {

    /**
     * Save a supplierTransactionStatus.
     *
     * @param supplierTransactionStatusDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierTransactionStatusDTO save(SupplierTransactionStatusDTO supplierTransactionStatusDTO);

    /**
     * Get all the supplierTransactionStatuses.
     *
     * @return the list of entities.
     */
    List<SupplierTransactionStatusDTO> findAll();


    /**
     * Get the "id" supplierTransactionStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierTransactionStatusDTO> findOne(Long id);

    /**
     * Delete the "id" supplierTransactionStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
