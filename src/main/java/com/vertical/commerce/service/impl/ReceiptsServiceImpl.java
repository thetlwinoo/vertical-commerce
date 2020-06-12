package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ReceiptsService;
import com.vertical.commerce.domain.Receipts;
import com.vertical.commerce.repository.ReceiptsRepository;
import com.vertical.commerce.service.dto.ReceiptsDTO;
import com.vertical.commerce.service.mapper.ReceiptsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Receipts}.
 */
@Service
@Transactional
public class ReceiptsServiceImpl implements ReceiptsService {

    private final Logger log = LoggerFactory.getLogger(ReceiptsServiceImpl.class);

    private final ReceiptsRepository receiptsRepository;

    private final ReceiptsMapper receiptsMapper;

    public ReceiptsServiceImpl(ReceiptsRepository receiptsRepository, ReceiptsMapper receiptsMapper) {
        this.receiptsRepository = receiptsRepository;
        this.receiptsMapper = receiptsMapper;
    }

    /**
     * Save a receipts.
     *
     * @param receiptsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ReceiptsDTO save(ReceiptsDTO receiptsDTO) {
        log.debug("Request to save Receipts : {}", receiptsDTO);
        Receipts receipts = receiptsMapper.toEntity(receiptsDTO);
        receipts = receiptsRepository.save(receipts);
        return receiptsMapper.toDto(receipts);
    }

    /**
     * Get all the receipts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReceiptsDTO> findAll() {
        log.debug("Request to get all Receipts");
        return receiptsRepository.findAll().stream()
            .map(receiptsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one receipts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ReceiptsDTO> findOne(Long id) {
        log.debug("Request to get Receipts : {}", id);
        return receiptsRepository.findById(id)
            .map(receiptsMapper::toDto);
    }

    /**
     * Delete the receipts by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receipts : {}", id);

        receiptsRepository.deleteById(id);
    }
}
