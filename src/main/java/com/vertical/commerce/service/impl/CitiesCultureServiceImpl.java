package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CitiesCultureService;
import com.vertical.commerce.domain.CitiesCulture;
import com.vertical.commerce.repository.CitiesCultureRepository;
import com.vertical.commerce.service.dto.CitiesCultureDTO;
import com.vertical.commerce.service.mapper.CitiesCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CitiesCulture}.
 */
@Service
@Transactional
public class CitiesCultureServiceImpl implements CitiesCultureService {

    private final Logger log = LoggerFactory.getLogger(CitiesCultureServiceImpl.class);

    private final CitiesCultureRepository citiesCultureRepository;

    private final CitiesCultureMapper citiesCultureMapper;

    public CitiesCultureServiceImpl(CitiesCultureRepository citiesCultureRepository, CitiesCultureMapper citiesCultureMapper) {
        this.citiesCultureRepository = citiesCultureRepository;
        this.citiesCultureMapper = citiesCultureMapper;
    }

    /**
     * Save a citiesCulture.
     *
     * @param citiesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CitiesCultureDTO save(CitiesCultureDTO citiesCultureDTO) {
        log.debug("Request to save CitiesCulture : {}", citiesCultureDTO);
        CitiesCulture citiesCulture = citiesCultureMapper.toEntity(citiesCultureDTO);
        citiesCulture = citiesCultureRepository.save(citiesCulture);
        return citiesCultureMapper.toDto(citiesCulture);
    }

    /**
     * Get all the citiesCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitiesCultureDTO> findAll() {
        log.debug("Request to get all CitiesCultures");
        return citiesCultureRepository.findAll().stream()
            .map(citiesCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one citiesCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitiesCultureDTO> findOne(Long id) {
        log.debug("Request to get CitiesCulture : {}", id);
        return citiesCultureRepository.findById(id)
            .map(citiesCultureMapper::toDto);
    }

    /**
     * Delete the citiesCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CitiesCulture : {}", id);

        citiesCultureRepository.deleteById(id);
    }
}
