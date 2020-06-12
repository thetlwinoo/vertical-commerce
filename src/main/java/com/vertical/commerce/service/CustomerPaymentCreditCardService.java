package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPaymentCreditCard}.
 */
public interface CustomerPaymentCreditCardService {

    /**
     * Save a customerPaymentCreditCard.
     *
     * @param customerPaymentCreditCardDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentCreditCardDTO save(CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO);

    /**
     * Get all the customerPaymentCreditCards.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentCreditCardDTO> findAll();


    /**
     * Get the "id" customerPaymentCreditCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentCreditCardDTO> findOne(Long id);

    /**
     * Delete the "id" customerPaymentCreditCard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
