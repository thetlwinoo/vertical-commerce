package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPaymentVoucher}.
 */
public interface CustomerPaymentVoucherService {

    /**
     * Save a customerPaymentVoucher.
     *
     * @param customerPaymentVoucherDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentVoucherDTO save(CustomerPaymentVoucherDTO customerPaymentVoucherDTO);

    /**
     * Get all the customerPaymentVouchers.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentVoucherDTO> findAll();


    /**
     * Get the "id" customerPaymentVoucher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentVoucherDTO> findOne(Long id);

    /**
     * Delete the "id" customerPaymentVoucher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
