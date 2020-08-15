package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownsLocalizeService;
import com.vertical.commerce.domain.TownsLocalize;
import com.vertical.commerce.repository.TownsLocalizeRepository;
import com.vertical.commerce.service.dto.TownsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TownsLocalize}.
 */
@Service
@Transactional
public class TownsLocalizeServiceImpl implements TownsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(TownsLocalizeServiceImpl.class);

    private final TownsLocalizeRepository townsLocalizeRepository;

    private final TownsLocalizeMapper townsLocalizeMapper;

    public TownsLocalizeServiceImpl(TownsLocalizeRepository townsLocalizeRepository, TownsLocalizeMapper townsLocalizeMapper) {
        this.townsLocalizeRepository = townsLocalizeRepository;
        this.townsLocalizeMapper = townsLocalizeMapper;
    }

    /**
     * Save a townsLocalize.
     *
     * @param townsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownsLocalizeDTO save(TownsLocalizeDTO townsLocalizeDTO) {
        log.debug("Request to save TownsLocalize : {}", townsLocalizeDTO);
        TownsLocalize townsLocalize = townsLocalizeMapper.toEntity(townsLocalizeDTO);
        townsLocalize = townsLocalizeRepository.save(townsLocalize);
        return townsLocalizeMapper.toDto(townsLocalize);
    }

    /**
     * Get all the townsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownsLocalizeDTO> findAll() {
        log.debug("Request to get all TownsLocalizes");
        return townsLocalizeRepository.findAll().stream()
            .map(townsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one townsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get TownsLocalize : {}", id);
        return townsLocalizeRepository.findById(id)
            .map(townsLocalizeMapper::toDto);
    }

    /**
     * Delete the townsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TownsLocalize : {}", id);

        townsLocalizeRepository.deleteById(id);
    }
}
