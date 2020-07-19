package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownshipsCultureService;
import com.vertical.commerce.domain.TownshipsCulture;
import com.vertical.commerce.repository.TownshipsCultureRepository;
import com.vertical.commerce.service.dto.TownshipsCultureDTO;
import com.vertical.commerce.service.mapper.TownshipsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TownshipsCulture}.
 */
@Service
@Transactional
public class TownshipsCultureServiceImpl implements TownshipsCultureService {

    private final Logger log = LoggerFactory.getLogger(TownshipsCultureServiceImpl.class);

    private final TownshipsCultureRepository townshipsCultureRepository;

    private final TownshipsCultureMapper townshipsCultureMapper;

    public TownshipsCultureServiceImpl(TownshipsCultureRepository townshipsCultureRepository, TownshipsCultureMapper townshipsCultureMapper) {
        this.townshipsCultureRepository = townshipsCultureRepository;
        this.townshipsCultureMapper = townshipsCultureMapper;
    }

    /**
     * Save a townshipsCulture.
     *
     * @param townshipsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownshipsCultureDTO save(TownshipsCultureDTO townshipsCultureDTO) {
        log.debug("Request to save TownshipsCulture : {}", townshipsCultureDTO);
        TownshipsCulture townshipsCulture = townshipsCultureMapper.toEntity(townshipsCultureDTO);
        townshipsCulture = townshipsCultureRepository.save(townshipsCulture);
        return townshipsCultureMapper.toDto(townshipsCulture);
    }

    /**
     * Get all the townshipsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownshipsCultureDTO> findAll() {
        log.debug("Request to get all TownshipsCultures");
        return townshipsCultureRepository.findAll().stream()
            .map(townshipsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one townshipsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownshipsCultureDTO> findOne(Long id) {
        log.debug("Request to get TownshipsCulture : {}", id);
        return townshipsCultureRepository.findById(id)
            .map(townshipsCultureMapper::toDto);
    }

    /**
     * Delete the townshipsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TownshipsCulture : {}", id);

        townshipsCultureRepository.deleteById(id);
    }
}
