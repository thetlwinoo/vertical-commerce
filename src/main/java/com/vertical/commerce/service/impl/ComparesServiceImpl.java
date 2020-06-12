package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ComparesService;
import com.vertical.commerce.domain.Compares;
import com.vertical.commerce.repository.ComparesRepository;
import com.vertical.commerce.service.dto.ComparesDTO;
import com.vertical.commerce.service.mapper.ComparesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Compares}.
 */
@Service
@Transactional
public class ComparesServiceImpl implements ComparesService {

    private final Logger log = LoggerFactory.getLogger(ComparesServiceImpl.class);

    private final ComparesRepository comparesRepository;

    private final ComparesMapper comparesMapper;

    public ComparesServiceImpl(ComparesRepository comparesRepository, ComparesMapper comparesMapper) {
        this.comparesRepository = comparesRepository;
        this.comparesMapper = comparesMapper;
    }

    /**
     * Save a compares.
     *
     * @param comparesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ComparesDTO save(ComparesDTO comparesDTO) {
        log.debug("Request to save Compares : {}", comparesDTO);
        Compares compares = comparesMapper.toEntity(comparesDTO);
        compares = comparesRepository.save(compares);
        return comparesMapper.toDto(compares);
    }

    /**
     * Get all the compares.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ComparesDTO> findAll() {
        log.debug("Request to get all Compares");
        return comparesRepository.findAll().stream()
            .map(comparesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one compares by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ComparesDTO> findOne(Long id) {
        log.debug("Request to get Compares : {}", id);
        return comparesRepository.findById(id)
            .map(comparesMapper::toDto);
    }

    /**
     * Delete the compares by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compares : {}", id);

        comparesRepository.deleteById(id);
    }
}
