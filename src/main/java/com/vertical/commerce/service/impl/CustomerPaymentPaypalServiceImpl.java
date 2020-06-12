package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentPaypalService;
import com.vertical.commerce.domain.CustomerPaymentPaypal;
import com.vertical.commerce.repository.CustomerPaymentPaypalRepository;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentPaypalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerPaymentPaypal}.
 */
@Service
@Transactional
public class CustomerPaymentPaypalServiceImpl implements CustomerPaymentPaypalService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentPaypalServiceImpl.class);

    private final CustomerPaymentPaypalRepository customerPaymentPaypalRepository;

    private final CustomerPaymentPaypalMapper customerPaymentPaypalMapper;

    public CustomerPaymentPaypalServiceImpl(CustomerPaymentPaypalRepository customerPaymentPaypalRepository, CustomerPaymentPaypalMapper customerPaymentPaypalMapper) {
        this.customerPaymentPaypalRepository = customerPaymentPaypalRepository;
        this.customerPaymentPaypalMapper = customerPaymentPaypalMapper;
    }

    /**
     * Save a customerPaymentPaypal.
     *
     * @param customerPaymentPaypalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentPaypalDTO save(CustomerPaymentPaypalDTO customerPaymentPaypalDTO) {
        log.debug("Request to save CustomerPaymentPaypal : {}", customerPaymentPaypalDTO);
        CustomerPaymentPaypal customerPaymentPaypal = customerPaymentPaypalMapper.toEntity(customerPaymentPaypalDTO);
        customerPaymentPaypal = customerPaymentPaypalRepository.save(customerPaymentPaypal);
        return customerPaymentPaypalMapper.toDto(customerPaymentPaypal);
    }

    /**
     * Get all the customerPaymentPaypals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentPaypalDTO> findAll() {
        log.debug("Request to get all CustomerPaymentPaypals");
        return customerPaymentPaypalRepository.findAll().stream()
            .map(customerPaymentPaypalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerPaymentPaypal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentPaypalDTO> findOne(Long id) {
        log.debug("Request to get CustomerPaymentPaypal : {}", id);
        return customerPaymentPaypalRepository.findById(id)
            .map(customerPaymentPaypalMapper::toDto);
    }

    /**
     * Delete the customerPaymentPaypal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentPaypal : {}", id);

        customerPaymentPaypalRepository.deleteById(id);
    }
}
