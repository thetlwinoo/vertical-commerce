package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.TownshipsLocalizeService;
import com.vertical.commerce.domain.TownshipsLocalize;
import com.vertical.commerce.repository.TownshipsLocalizeRepository;
import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;
import com.vertical.commerce.service.mapper.TownshipsLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TownshipsLocalize}.
 */
@Service
@Transactional
public class TownshipsLocalizeServiceImpl implements TownshipsLocalizeService {

    private final Logger log = LoggerFactory.getLogger(TownshipsLocalizeServiceImpl.class);

    private final TownshipsLocalizeRepository townshipsLocalizeRepository;

    private final TownshipsLocalizeMapper townshipsLocalizeMapper;

    public TownshipsLocalizeServiceImpl(TownshipsLocalizeRepository townshipsLocalizeRepository, TownshipsLocalizeMapper townshipsLocalizeMapper) {
        this.townshipsLocalizeRepository = townshipsLocalizeRepository;
        this.townshipsLocalizeMapper = townshipsLocalizeMapper;
    }

    /**
     * Save a townshipsLocalize.
     *
     * @param townshipsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TownshipsLocalizeDTO save(TownshipsLocalizeDTO townshipsLocalizeDTO) {
        log.debug("Request to save TownshipsLocalize : {}", townshipsLocalizeDTO);
        TownshipsLocalize townshipsLocalize = townshipsLocalizeMapper.toEntity(townshipsLocalizeDTO);
        townshipsLocalize = townshipsLocalizeRepository.save(townshipsLocalize);
        return townshipsLocalizeMapper.toDto(townshipsLocalize);
    }

    /**
     * Get all the townshipsLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TownshipsLocalizeDTO> findAll() {
        log.debug("Request to get all TownshipsLocalizes");
        return townshipsLocalizeRepository.findAll().stream()
            .map(townshipsLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one townshipsLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TownshipsLocalizeDTO> findOne(Long id) {
        log.debug("Request to get TownshipsLocalize : {}", id);
        return townshipsLocalizeRepository.findById(id)
            .map(townshipsLocalizeMapper::toDto);
    }

    /**
     * Delete the townshipsLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TownshipsLocalize : {}", id);

        townshipsLocalizeRepository.deleteById(id);
    }
}
