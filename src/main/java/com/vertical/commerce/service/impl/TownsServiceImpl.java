package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownsService;
import com.vertical.commerce.domain.Towns;
import com.vertical.commerce.repository.TownsRepository;
import com.vertical.commerce.service.dto.TownsDTO;
import com.vertical.commerce.service.mapper.TownsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Towns}.
 */
@Service
@Transactional
public class TownsServiceImpl implements TownsService {

    private final Logger log = LoggerFactory.getLogger(TownsServiceImpl.class);

    private final TownsRepository townsRepository;

    private final TownsMapper townsMapper;

    public TownsServiceImpl(TownsRepository townsRepository, TownsMapper townsMapper) {
        this.townsRepository = townsRepository;
        this.townsMapper = townsMapper;
    }

    /**
     * Save a towns.
     *
     * @param townsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownsDTO save(TownsDTO townsDTO) {
        log.debug("Request to save Towns : {}", townsDTO);
        Towns towns = townsMapper.toEntity(townsDTO);
        towns = townsRepository.save(towns);
        return townsMapper.toDto(towns);
    }

    /**
     * Get all the towns.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownsDTO> findAll() {
        log.debug("Request to get all Towns");
        return townsRepository.findAll().stream()
            .map(townsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one towns by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownsDTO> findOne(Long id) {
        log.debug("Request to get Towns : {}", id);
        return townsRepository.findById(id)
            .map(townsMapper::toDto);
    }

    /**
     * Delete the towns by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Towns : {}", id);

        townsRepository.deleteById(id);
    }
}
