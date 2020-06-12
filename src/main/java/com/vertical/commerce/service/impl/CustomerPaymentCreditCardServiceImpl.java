package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentCreditCardService;
import com.vertical.commerce.domain.CustomerPaymentCreditCard;
import com.vertical.commerce.repository.CustomerPaymentCreditCardRepository;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerPaymentCreditCard}.
 */
@Service
@Transactional
public class CustomerPaymentCreditCardServiceImpl implements CustomerPaymentCreditCardService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardServiceImpl.class);

    private final CustomerPaymentCreditCardRepository customerPaymentCreditCardRepository;

    private final CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper;

    public CustomerPaymentCreditCardServiceImpl(CustomerPaymentCreditCardRepository customerPaymentCreditCardRepository, CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper) {
        this.customerPaymentCreditCardRepository = customerPaymentCreditCardRepository;
        this.customerPaymentCreditCardMapper = customerPaymentCreditCardMapper;
    }

    /**
     * Save a customerPaymentCreditCard.
     *
     * @param customerPaymentCreditCardDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentCreditCardDTO save(CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO) {
        log.debug("Request to save CustomerPaymentCreditCard : {}", customerPaymentCreditCardDTO);
        CustomerPaymentCreditCard customerPaymentCreditCard = customerPaymentCreditCardMapper.toEntity(customerPaymentCreditCardDTO);
        customerPaymentCreditCard = customerPaymentCreditCardRepository.save(customerPaymentCreditCard);
        return customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);
    }

    /**
     * Get all the customerPaymentCreditCards.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentCreditCardDTO> findAll() {
        log.debug("Request to get all CustomerPaymentCreditCards");
        return customerPaymentCreditCardRepository.findAll().stream()
            .map(customerPaymentCreditCardMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerPaymentCreditCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentCreditCardDTO> findOne(Long id) {
        log.debug("Request to get CustomerPaymentCreditCard : {}", id);
        return customerPaymentCreditCardRepository.findById(id)
            .map(customerPaymentCreditCardMapper::toDto);
    }

    /**
     * Delete the customerPaymentCreditCard by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentCreditCard : {}", id);

        customerPaymentCreditCardRepository.deleteById(id);
    }
}
