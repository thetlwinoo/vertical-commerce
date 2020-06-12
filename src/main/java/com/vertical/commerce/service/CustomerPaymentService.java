package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CustomerPaymentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CustomerPayment}.
 */
public interface CustomerPaymentService {

    /**
     * Save a customerPayment.
     *
     * @param customerPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerPaymentDTO save(CustomerPaymentDTO customerPaymentDTO);

    /**
     * Get all the customerPayments.
     *
     * @return the list of entities.
     */
    List<CustomerPaymentDTO> findAll();
    /**
     * Get all the CustomerPaymentDTO where CustomerPaymentCreditCard is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustomerPaymentDTO> findAllWhereCustomerPaymentCreditCardIsNull();
    /**
     * Get all the CustomerPaymentDTO where CustomerPaymentVoucher is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustomerPaymentDTO> findAllWhereCustomerPaymentVoucherIsNull();
    /**
     * Get all the CustomerPaymentDTO where CustomerPaymentBankTransfer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustomerPaymentDTO> findAllWhereCustomerPaymentBankTransferIsNull();
    /**
     * Get all the CustomerPaymentDTO where CustomerPaymentPaypal is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustomerPaymentDTO> findAllWhereCustomerPaymentPaypalIsNull();


    /**
     * Get the "id" customerPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" customerPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
