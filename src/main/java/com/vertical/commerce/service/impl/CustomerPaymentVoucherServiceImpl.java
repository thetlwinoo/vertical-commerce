package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CustomerPaymentVoucherService;
import com.vertical.commerce.domain.CustomerPaymentVoucher;
import com.vertical.commerce.repository.CustomerPaymentVoucherRepository;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CustomerPaymentVoucher}.
 */
@Service
@Transactional
public class CustomerPaymentVoucherServiceImpl implements CustomerPaymentVoucherService {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentVoucherServiceImpl.class);

    private final CustomerPaymentVoucherRepository customerPaymentVoucherRepository;

    private final CustomerPaymentVoucherMapper customerPaymentVoucherMapper;

    public CustomerPaymentVoucherServiceImpl(CustomerPaymentVoucherRepository customerPaymentVoucherRepository, CustomerPaymentVoucherMapper customerPaymentVoucherMapper) {
        this.customerPaymentVoucherRepository = customerPaymentVoucherRepository;
        this.customerPaymentVoucherMapper = customerPaymentVoucherMapper;
    }

    /**
     * Save a customerPaymentVoucher.
     *
     * @param customerPaymentVoucherDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CustomerPaymentVoucherDTO save(CustomerPaymentVoucherDTO customerPaymentVoucherDTO) {
        log.debug("Request to save CustomerPaymentVoucher : {}", customerPaymentVoucherDTO);
        CustomerPaymentVoucher customerPaymentVoucher = customerPaymentVoucherMapper.toEntity(customerPaymentVoucherDTO);
        customerPaymentVoucher = customerPaymentVoucherRepository.save(customerPaymentVoucher);
        return customerPaymentVoucherMapper.toDto(customerPaymentVoucher);
    }

    /**
     * Get all the customerPaymentVouchers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomerPaymentVoucherDTO> findAll() {
        log.debug("Request to get all CustomerPaymentVouchers");
        return customerPaymentVoucherRepository.findAll().stream()
            .map(customerPaymentVoucherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one customerPaymentVoucher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerPaymentVoucherDTO> findOne(Long id) {
        log.debug("Request to get CustomerPaymentVoucher : {}", id);
        return customerPaymentVoucherRepository.findById(id)
            .map(customerPaymentVoucherMapper::toDto);
    }

    /**
     * Delete the customerPaymentVoucher by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerPaymentVoucher : {}", id);

        customerPaymentVoucherRepository.deleteById(id);
    }
}
