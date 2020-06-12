package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentBankTransferService;
import com.vertical.commerce.domain.CustomerPaymentBankTransfer;
import com.vertical.commerce.repository.CustomerPaymentBankTransferRepository;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentBankTransferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerPaymentBankTransfer}.
 */
@Service
@Transactional
public class CustomerPaymentBankTransferServiceImpl implements CustomerPaymentBankTransferService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentBankTransferServiceImpl.class);

    private final CustomerPaymentBankTransferRepository customerPaymentBankTransferRepository;

    private final CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper;

    public CustomerPaymentBankTransferServiceImpl(CustomerPaymentBankTransferRepository customerPaymentBankTransferRepository, CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper) {
        this.customerPaymentBankTransferRepository = customerPaymentBankTransferRepository;
        this.customerPaymentBankTransferMapper = customerPaymentBankTransferMapper;
    }

    /**
     * Save a customerPaymentBankTransfer.
     *
     * @param customerPaymentBankTransferDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentBankTransferDTO save(CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO) {
        log.debug("Request to save CustomerPaymentBankTransfer : {}", customerPaymentBankTransferDTO);
        CustomerPaymentBankTransfer customerPaymentBankTransfer = customerPaymentBankTransferMapper.toEntity(customerPaymentBankTransferDTO);
        customerPaymentBankTransfer = customerPaymentBankTransferRepository.save(customerPaymentBankTransfer);
        return customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);
    }

    /**
     * Get all the customerPaymentBankTransfers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentBankTransferDTO> findAll() {
        log.debug("Request to get all CustomerPaymentBankTransfers");
        return customerPaymentBankTransferRepository.findAll().stream()
            .map(customerPaymentBankTransferMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerPaymentBankTransfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentBankTransferDTO> findOne(Long id) {
        log.debug("Request to get CustomerPaymentBankTransfer : {}", id);
        return customerPaymentBankTransferRepository.findById(id)
            .map(customerPaymentBankTransferMapper::toDto);
    }

    /**
     * Delete the customerPaymentBankTransfer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentBankTransfer : {}", id);

        customerPaymentBankTransferRepository.deleteById(id);
    }
}
