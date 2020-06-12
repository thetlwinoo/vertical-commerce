package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentCreditCardExtendedService;
import com.vertical.commerce.domain.CustomerPaymentCreditCardExtended;
import com.vertical.commerce.repository.CustomerPaymentCreditCardExtendedRepository;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardExtendedMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerPaymentCreditCardExtended}.
 */
@Service
@Transactional
public class CustomerPaymentCreditCardExtendedServiceImpl implements CustomerPaymentCreditCardExtendedService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardExtendedServiceImpl.class);

    private final CustomerPaymentCreditCardExtendedRepository customerPaymentCreditCardExtendedRepository;

    private final CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper;

    public CustomerPaymentCreditCardExtendedServiceImpl(CustomerPaymentCreditCardExtendedRepository customerPaymentCreditCardExtendedRepository, CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper) {
        this.customerPaymentCreditCardExtendedRepository = customerPaymentCreditCardExtendedRepository;
        this.customerPaymentCreditCardExtendedMapper = customerPaymentCreditCardExtendedMapper;
    }

    /**
     * Save a customerPaymentCreditCardExtended.
     *
     * @param customerPaymentCreditCardExtendedDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentCreditCardExtendedDTO save(CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO) {
        log.debug("Request to save CustomerPaymentCreditCardExtended : {}", customerPaymentCreditCardExtendedDTO);
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended = customerPaymentCreditCardExtendedMapper.toEntity(customerPaymentCreditCardExtendedDTO);
        customerPaymentCreditCardExtended = customerPaymentCreditCardExtendedRepository.save(customerPaymentCreditCardExtended);
        return customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);
    }

    /**
     * Get all the customerPaymentCreditCardExtendeds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentCreditCardExtendedDTO> findAll() {
        log.debug("Request to get all CustomerPaymentCreditCardExtendeds");
        return customerPaymentCreditCardExtendedRepository.findAll().stream()
            .map(customerPaymentCreditCardExtendedMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerPaymentCreditCardExtended by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentCreditCardExtendedDTO> findOne(Long id) {
        log.debug("Request to get CustomerPaymentCreditCardExtended : {}", id);
        return customerPaymentCreditCardExtendedRepository.findById(id)
            .map(customerPaymentCreditCardExtendedMapper::toDto);
    }

    /**
     * Delete the customerPaymentCreditCardExtended by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentCreditCardExtended : {}", id);

        customerPaymentCreditCardExtendedRepository.deleteById(id);
    }
}
