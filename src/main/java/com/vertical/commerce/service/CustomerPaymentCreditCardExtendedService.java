package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPaymentCreditCardExtended}.
 */
public interface CustomerPaymentCreditCardExtendedService {

    /**
     * Save a customerPaymentCreditCardExtended.
     *
     * @param customerPaymentCreditCardExtendedDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentCreditCardExtendedDTO save(CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO);

    /**
     * Get all the customerPaymentCreditCardExtendeds.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentCreditCardExtendedDTO> findAll();


    /**
     * Get the "id" customerPaymentCreditCardExtended.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentCreditCardExtendedDTO> findOne(Long id);

    /**
     * Delete the "id" customerPaymentCreditCardExtended.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
