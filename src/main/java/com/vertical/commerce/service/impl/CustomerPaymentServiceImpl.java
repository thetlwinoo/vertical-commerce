package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentService;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.repository.CustomerPaymentRepository;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link CustomerPayment}.
 */
@Service
@Transactional
public class CustomerPaymentServiceImpl implements CustomerPaymentService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentServiceImpl.class);

    private final CustomerPaymentRepository customerPaymentRepository;

    private final CustomerPaymentMapper customerPaymentMapper;

    public CustomerPaymentServiceImpl(CustomerPaymentRepository customerPaymentRepository, CustomerPaymentMapper customerPaymentMapper) {
        this.customerPaymentRepository = customerPaymentRepository;
        this.customerPaymentMapper = customerPaymentMapper;
    }

    /**
     * Save a customerPayment.
     *
     * @param customerPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentDTO save(CustomerPaymentDTO customerPaymentDTO) {
        log.debug("Request to save CustomerPayment : {}", customerPaymentDTO);
        CustomerPayment customerPayment = customerPaymentMapper.toEntity(customerPaymentDTO);
        customerPayment = customerPaymentRepository.save(customerPayment);
        return customerPaymentMapper.toDto(customerPayment);
    }

    /**
     * Get all the customerPayments.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentDTO> findAll() {
        log.debug("Request to get all CustomerPayments");
        return customerPaymentRepository.findAll().stream()
            .map(customerPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the customerPayments where CustomerPaymentCreditCard is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CustomerPaymentDTO> findAllWhereCustomerPaymentCreditCardIsNull() {
        log.debug("Request to get all customerPayments where CustomerPaymentCreditCard is null");
        return StreamSupport
            .stream(customerPaymentRepository.findAll().spliterator(), false)
            .filter(customerPayment -> customerPayment.getCustomerPaymentCreditCard() == null)
            .map(customerPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the customerPayments where CustomerPaymentVoucher is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CustomerPaymentDTO> findAllWhereCustomerPaymentVoucherIsNull() {
        log.debug("Request to get all customerPayments where CustomerPaymentVoucher is null");
        return StreamSupport
            .stream(customerPaymentRepository.findAll().spliterator(), false)
            .filter(customerPayment -> customerPayment.getCustomerPaymentVoucher() == null)
            .map(customerPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the customerPayments where CustomerPaymentBankTransfer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CustomerPaymentDTO> findAllWhereCustomerPaymentBankTransferIsNull() {
        log.debug("Request to get all customerPayments where CustomerPaymentBankTransfer is null");
        return StreamSupport
            .stream(customerPaymentRepository.findAll().spliterator(), false)
            .filter(customerPayment -> customerPayment.getCustomerPaymentBankTransfer() == null)
            .map(customerPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the customerPayments where CustomerPaymentPaypal is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CustomerPaymentDTO> findAllWhereCustomerPaymentPaypalIsNull() {
        log.debug("Request to get all customerPayments where CustomerPaymentPaypal is null");
        return StreamSupport
            .stream(customerPaymentRepository.findAll().spliterator(), false)
            .filter(customerPayment -> customerPayment.getCustomerPaymentPaypal() == null)
            .map(customerPaymentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one customerPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentDTO> findOne(Long id) {
        log.debug("Request to get CustomerPayment : {}", id);
        return customerPaymentRepository.findById(id)
            .map(customerPaymentMapper::toDto);
    }

    /**
     * Delete the customerPayment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPayment : {}", id);

        customerPaymentRepository.deleteById(id);
    }
}
