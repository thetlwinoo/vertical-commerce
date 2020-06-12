package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CompareLinesService;
import com.vertical.commerce.domain.CompareLines;
import com.vertical.commerce.repository.CompareLinesRepository;
import com.vertical.commerce.service.dto.CompareLinesDTO;
import com.vertical.commerce.service.mapper.CompareLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompareLines}.
 */
@Service
@Transactional
public class CompareLinesServiceImpl implements CompareLinesService {

    private final Logger log = LoggerFactory.getLogger(CompareLinesServiceImpl.class);

    private final CompareLinesRepository compareLinesRepository;

    private final CompareLinesMapper compareLinesMapper;

    public CompareLinesServiceImpl(CompareLinesRepository compareLinesRepository, CompareLinesMapper compareLinesMapper) {
        this.compareLinesRepository = compareLinesRepository;
        this.compareLinesMapper = compareLinesMapper;
    }

    /**
     * Save a compareLines.
     *
     * @param compareLinesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompareLinesDTO save(CompareLinesDTO compareLinesDTO) {
        log.debug("Request to save CompareLines : {}", compareLinesDTO);
        CompareLines compareLines = compareLinesMapper.toEntity(compareLinesDTO);
        compareLines = compareLinesRepository.save(compareLines);
        return compareLinesMapper.toDto(compareLines);
    }

    /**
     * Get all the compareLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompareLinesDTO> findAll() {
        log.debug("Request to get all CompareLines");
        return compareLinesRepository.findAll().stream()
            .map(compareLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compareLines by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompareLinesDTO> findOne(Long id) {
        log.debug("Request to get CompareLines : {}", id);
        return compareLinesRepository.findById(id)
            .map(compareLinesMapper::toDto);
    }

    /**
     * Delete the compareLines by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompareLines : {}", id);

        compareLinesRepository.deleteById(id);
    }
}
