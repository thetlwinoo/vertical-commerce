package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownsCultureService;
import com.vertical.commerce.domain.TownsCulture;
import com.vertical.commerce.repository.TownsCultureRepository;
import com.vertical.commerce.service.dto.TownsCultureDTO;
import com.vertical.commerce.service.mapper.TownsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TownsCulture}.
 */
@Service
@Transactional
public class TownsCultureServiceImpl implements TownsCultureService {

    private final Logger log = LoggerFactory.getLogger(TownsCultureServiceImpl.class);

    private final TownsCultureRepository townsCultureRepository;

    private final TownsCultureMapper townsCultureMapper;

    public TownsCultureServiceImpl(TownsCultureRepository townsCultureRepository, TownsCultureMapper townsCultureMapper) {
        this.townsCultureRepository = townsCultureRepository;
        this.townsCultureMapper = townsCultureMapper;
    }

    /**
     * Save a townsCulture.
     *
     * @param townsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownsCultureDTO save(TownsCultureDTO townsCultureDTO) {
        log.debug("Request to save TownsCulture : {}", townsCultureDTO);
        TownsCulture townsCulture = townsCultureMapper.toEntity(townsCultureDTO);
        townsCulture = townsCultureRepository.save(townsCulture);
        return townsCultureMapper.toDto(townsCulture);
    }

    /**
     * Get all the townsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownsCultureDTO> findAll() {
        log.debug("Request to get all TownsCultures");
        return townsCultureRepository.findAll().stream()
            .map(townsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one townsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownsCultureDTO> findOne(Long id) {
        log.debug("Request to get TownsCulture : {}", id);
        return townsCultureRepository.findById(id)
            .map(townsCultureMapper::toDto);
    }

    /**
     * Delete the townsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TownsCulture : {}", id);

        townsCultureRepository.deleteById(id);
    }
}
