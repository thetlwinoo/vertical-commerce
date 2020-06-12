package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPaymentBankTransfer}.
 */
public interface CustomerPaymentBankTransferService {

    /**
     * Save a customerPaymentBankTransfer.
     *
     * @param customerPaymentBankTransferDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentBankTransferDTO save(CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO);

    /**
     * Get all the customerPaymentBankTransfers.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentBankTransferDTO> findAll();


    /**
     * Get the "id" customerPaymentBankTransfer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentBankTransferDTO> findOne(Long id);

    /**
     * Delete the "id" customerPaymentBankTransfer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
