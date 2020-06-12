package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerTransactionsService;
import com.vertical.commerce.domain.CustomerTransactions;
import com.vertical.commerce.repository.CustomerTransactionsRepository;
import com.vertical.commerce.service.dto.CustomerTransactionsDTO;
import com.vertical.commerce.service.mapper.CustomerTransactionsMapper;
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
 * Service Implementation for managing {@link CustomerTransactions}.
 */
@Service
@Transactional
public class CustomerTransactionsServiceImpl implements CustomerTransactionsService {

    private final Logger log = LoggerFactory.getLogger(CustomerTransactionsServiceImpl.class);

    private final CustomerTransactionsRepository customerTransactionsRepository;

    private final CustomerTransactionsMapper customerTransactionsMapper;

    public CustomerTransactionsServiceImpl(CustomerTransactionsRepository customerTransactionsRepository, CustomerTransactionsMapper customerTransactionsMapper) {
        this.customerTransactionsRepository = customerTransactionsRepository;
        this.customerTransactionsMapper = customerTransactionsMapper;
    }

    /**
     * Save a customerTransactions.
     *
     * @param customerTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerTransactionsDTO save(CustomerTransactionsDTO customerTransactionsDTO) {
        log.debug("Request to save CustomerTransactions : {}", customerTransactionsDTO);
        CustomerTransactions customerTransactions = customerTransactionsMapper.toEntity(customerTransactionsDTO);
        customerTransactions = customerTransactionsRepository.save(customerTransactions);
        return customerTransactionsMapper.toDto(customerTransactions);
    }

    /**
     * Get all the customerTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerTransactionsDTO> findAll() {
        log.debug("Request to get all CustomerTransactions");
        return customerTransactionsRepository.findAll().stream()
            .map(customerTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the customerTransactions where CustomerPayment is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<CustomerTransactionsDTO> findAllWhereCustomerPaymentIsNull() {
        log.debug("Request to get all customerTransactions where CustomerPayment is null");
        return StreamSupport
            .stream(customerTransactionsRepository.findAll().spliterator(), false)
            .filter(customerTransactions -> customerTransactions.getCustomerPayment() == null)
            .map(customerTransactionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one customerTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerTransactionsDTO> findOne(Long id) {
        log.debug("Request to get CustomerTransactions : {}", id);
        return customerTransactionsRepository.findById(id)
            .map(customerTransactionsMapper::toDto);
    }

    /**
     * Delete the customerTransactions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerTransactions : {}", id);

        customerTransactionsRepository.deleteById(id);
    }
}
