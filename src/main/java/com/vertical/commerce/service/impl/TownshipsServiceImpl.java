package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownshipsService;
import com.vertical.commerce.domain.Townships;
import com.vertical.commerce.repository.TownshipsRepository;
import com.vertical.commerce.service.dto.TownshipsDTO;
import com.vertical.commerce.service.mapper.TownshipsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Townships}.
 */
@Service
@Transactional
public class TownshipsServiceImpl implements TownshipsService {

    private final Logger log = LoggerFactory.getLogger(TownshipsServiceImpl.class);

    private final TownshipsRepository townshipsRepository;

    private final TownshipsMapper townshipsMapper;

    public TownshipsServiceImpl(TownshipsRepository townshipsRepository, TownshipsMapper townshipsMapper) {
        this.townshipsRepository = townshipsRepository;
        this.townshipsMapper = townshipsMapper;
    }

    /**
     * Save a townships.
     *
     * @param townshipsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownshipsDTO save(TownshipsDTO townshipsDTO) {
        log.debug("Request to save Townships : {}", townshipsDTO);
        Townships townships = townshipsMapper.toEntity(townshipsDTO);
        townships = townshipsRepository.save(townships);
        return townshipsMapper.toDto(townships);
    }

    /**
     * Get all the townships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownshipsDTO> findAll() {
        log.debug("Request to get all Townships");
        return townshipsRepository.findAll().stream()
            .map(townshipsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one townships by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownshipsDTO> findOne(Long id) {
        log.debug("Request to get Townships : {}", id);
        return townshipsRepository.findById(id)
            .map(townshipsMapper::toDto);
    }

    /**
     * Delete the townships by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Townships : {}", id);

        townshipsRepository.deleteById(id);
    }
}
