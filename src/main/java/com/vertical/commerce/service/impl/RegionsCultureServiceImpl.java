package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.RegionsCultureService;
import com.vertical.commerce.domain.RegionsCulture;
import com.vertical.commerce.repository.RegionsCultureRepository;
import com.vertical.commerce.service.dto.RegionsCultureDTO;
import com.vertical.commerce.service.mapper.RegionsCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RegionsCulture}.
 */
@Service
@Transactional
public class RegionsCultureServiceImpl implements RegionsCultureService {

    private final Logger log = LoggerFactory.getLogger(RegionsCultureServiceImpl.class);

    private final RegionsCultureRepository regionsCultureRepository;

    private final RegionsCultureMapper regionsCultureMapper;

    public RegionsCultureServiceImpl(RegionsCultureRepository regionsCultureRepository, RegionsCultureMapper regionsCultureMapper) {
        this.regionsCultureRepository = regionsCultureRepository;
        this.regionsCultureMapper = regionsCultureMapper;
    }

    /**
     * Save a regionsCulture.
     *
     * @param regionsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegionsCultureDTO save(RegionsCultureDTO regionsCultureDTO) {
        log.debug("Request to save RegionsCulture : {}", regionsCultureDTO);
        RegionsCulture regionsCulture = regionsCultureMapper.toEntity(regionsCultureDTO);
        regionsCulture = regionsCultureRepository.save(regionsCulture);
        return regionsCultureMapper.toDto(regionsCulture);
    }

    /**
     * Get all the regionsCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegionsCultureDTO> findAll() {
        log.debug("Request to get all RegionsCultures");
        return regionsCultureRepository.findAll().stream()
            .map(regionsCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one regionsCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegionsCultureDTO> findOne(Long id) {
        log.debug("Request to get RegionsCulture : {}", id);
        return regionsCultureRepository.findById(id)
            .map(regionsCultureMapper::toDto);
    }

    /**
     * Delete the regionsCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegionsCulture : {}", id);

        regionsCultureRepository.deleteById(id);
    }
}
