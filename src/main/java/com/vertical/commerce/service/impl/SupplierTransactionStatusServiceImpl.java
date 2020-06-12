package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.SupplierTransactionStatusService;
import com.vertical.commerce.domain.SupplierTransactionStatus;
import com.vertical.commerce.repository.SupplierTransactionStatusRepository;
import com.vertical.commerce.service.dto.SupplierTransactionStatusDTO;
import com.vertical.commerce.service.mapper.SupplierTransactionStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SupplierTransactionStatus}.
 */
@Service
@Transactional
public class SupplierTransactionStatusServiceImpl implements SupplierTransactionStatusService {

    private final Logger log = LoggerFactory.getLogger(SupplierTransactionStatusServiceImpl.class);

    private final SupplierTransactionStatusRepository supplierTransactionStatusRepository;

    private final SupplierTransactionStatusMapper supplierTransactionStatusMapper;

    public SupplierTransactionStatusServiceImpl(SupplierTransactionStatusRepository supplierTransactionStatusRepository, SupplierTransactionStatusMapper supplierTransactionStatusMapper) {
        this.supplierTransactionStatusRepository = supplierTransactionStatusRepository;
        this.supplierTransactionStatusMapper = supplierTransactionStatusMapper;
    }

    /**
     * Save a supplierTransactionStatus.
     *
     * @param supplierTransactionStatusDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierTransactionStatusDTO save(SupplierTransactionStatusDTO supplierTransactionStatusDTO) {
        log.debug("Request to save SupplierTransactionStatus : {}", supplierTransactionStatusDTO);
        SupplierTransactionStatus supplierTransactionStatus = supplierTransactionStatusMapper.toEntity(supplierTransactionStatusDTO);
        supplierTransactionStatus = supplierTransactionStatusRepository.save(supplierTransactionStatus);
        return supplierTransactionStatusMapper.toDto(supplierTransactionStatus);
    }

    /**
     * Get all the supplierTransactionStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SupplierTransactionStatusDTO> findAll() {
        log.debug("Request to get all SupplierTransactionStatuses");
        return supplierTransactionStatusRepository.findAll().stream()
            .map(supplierTransactionStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one supplierTransactionStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierTransactionStatusDTO> findOne(Long id) {
        log.debug("Request to get SupplierTransactionStatus : {}", id);
        return supplierTransactionStatusRepository.findById(id)
            .map(supplierTransactionStatusMapper::toDto);
    }

    /**
     * Delete the supplierTransactionStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierTransactionStatus : {}", id);

        supplierTransactionStatusRepository.deleteById(id);
    }
}
