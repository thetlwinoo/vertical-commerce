package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.StateProvincesLocalizeService;
import com.vertical.commerce.domain.StateProvincesLocalize;
import com.vertical.commerce.repository.StateProvincesLocalizeRepository;
import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;
import com.vertical.commerce.service.mapper.StateProvincesLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StateProvincesLocalize}.
 */
@Service
@Transactional
public class StateProvincesLocalizeServiceImpl implements StateProvincesLocalizeService {

    private final Logger log = LoggerFactory.getLogger(StateProvincesLocalizeServiceImpl.class);

    private final StateProvincesLocalizeRepository stateProvincesLocalizeRepository;

    private final StateProvincesLocalizeMapper stateProvincesLocalizeMapper;

    public StateProvincesLocalizeServiceImpl(StateProvincesLocalizeRepository stateProvincesLocalizeRepository, StateProvincesLocalizeMapper stateProvincesLocalizeMapper) {
        this.stateProvincesLocalizeRepository = stateProvincesLocalizeRepository;
        this.stateProvincesLocalizeMapper = stateProvincesLocalizeMapper;
    }

    /**
     * Save a stateProvincesLocalize.
     *
     * @param stateProvincesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StateProvincesLocalizeDTO save(StateProvincesLocalizeDTO stateProvincesLocalizeDTO) {
        log.debug("Request to save StateProvincesLocalize : {}", stateProvincesLocalizeDTO);
        StateProvincesLocalize stateProvincesLocalize = stateProvincesLocalizeMapper.toEntity(stateProvincesLocalizeDTO);
        stateProvincesLocalize = stateProvincesLocalizeRepository.save(stateProvincesLocalize);
        return stateProvincesLocalizeMapper.toDto(stateProvincesLocalize);
    }

    /**
     * Get all the stateProvincesLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateProvincesLocalizeDTO> findAll() {
        log.debug("Request to get all StateProvincesLocalizes");
        return stateProvincesLocalizeRepository.findAll().stream()
            .map(stateProvincesLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one stateProvincesLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateProvincesLocalizeDTO> findOne(Long id) {
        log.debug("Request to get StateProvincesLocalize : {}", id);
        return stateProvincesLocalizeRepository.findById(id)
            .map(stateProvincesLocalizeMapper::toDto);
    }

    /**
     * Delete the stateProvincesLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateProvincesLocalize : {}", id);

        stateProvincesLocalizeRepository.deleteById(id);
    }
}
