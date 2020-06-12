package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPaymentPaypal}.
 */
public interface CustomerPaymentPaypalService {

    /**
     * Save a customerPaymentPaypal.
     *
     * @param customerPaymentPaypalDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentPaypalDTO save(CustomerPaymentPaypalDTO customerPaymentPaypalDTO);

    /**
     * Get all the customerPaymentPaypals.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentPaypalDTO> findAll();


    /**
     * Get the "id" customerPaymentPaypal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentPaypalDTO> findOne(Long id);

    /**
     * Delete the "id" customerPaymentPaypal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
