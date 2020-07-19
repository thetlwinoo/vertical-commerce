package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.StateProvincesCultureService;
import com.vertical.commerce.domain.StateProvincesCulture;
import com.vertical.commerce.repository.StateProvincesCultureRepository;
import com.vertical.commerce.service.dto.StateProvincesCultureDTO;
import com.vertical.commerce.service.mapper.StateProvincesCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StateProvincesCulture}.
 */
@Service
@Transactional
public class StateProvincesCultureServiceImpl implements StateProvincesCultureService {

    private final Logger log = LoggerFactory.getLogger(StateProvincesCultureServiceImpl.class);

    private final StateProvincesCultureRepository stateProvincesCultureRepository;

    private final StateProvincesCultureMapper stateProvincesCultureMapper;

    public StateProvincesCultureServiceImpl(StateProvincesCultureRepository stateProvincesCultureRepository, StateProvincesCultureMapper stateProvincesCultureMapper) {
        this.stateProvincesCultureRepository = stateProvincesCultureRepository;
        this.stateProvincesCultureMapper = stateProvincesCultureMapper;
    }

    /**
     * Save a stateProvincesCulture.
     *
     * @param stateProvincesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StateProvincesCultureDTO save(StateProvincesCultureDTO stateProvincesCultureDTO) {
        log.debug("Request to save StateProvincesCulture : {}", stateProvincesCultureDTO);
        StateProvincesCulture stateProvincesCulture = stateProvincesCultureMapper.toEntity(stateProvincesCultureDTO);
        stateProvincesCulture = stateProvincesCultureRepository.save(stateProvincesCulture);
        return stateProvincesCultureMapper.toDto(stateProvincesCulture);
    }

    /**
     * Get all the stateProvincesCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateProvincesCultureDTO> findAll() {
        log.debug("Request to get all StateProvincesCultures");
        return stateProvincesCultureRepository.findAll().stream()
            .map(stateProvincesCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stateProvincesCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateProvincesCultureDTO> findOne(Long id) {
        log.debug("Request to get StateProvincesCulture : {}", id);
        return stateProvincesCultureRepository.findById(id)
            .map(stateProvincesCultureMapper::toDto);
    }

    /**
     * Delete the stateProvincesCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateProvincesCulture : {}", id);

        stateProvincesCultureRepository.deleteById(id);
    }
}
