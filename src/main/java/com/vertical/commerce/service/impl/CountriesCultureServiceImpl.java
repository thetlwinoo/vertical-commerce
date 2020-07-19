package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CountriesCultureService;
import com.vertical.commerce.domain.CountriesCulture;
import com.vertical.commerce.repository.CountriesCultureRepository;
import com.vertical.commerce.service.dto.CountriesCultureDTO;
import com.vertical.commerce.service.mapper.CountriesCultureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CountriesCulture}.
 */
@Service
@Transactional
public class CountriesCultureServiceImpl implements CountriesCultureService {

    private final Logger log = LoggerFactory.getLogger(CountriesCultureServiceImpl.class);

    private final CountriesCultureRepository countriesCultureRepository;

    private final CountriesCultureMapper countriesCultureMapper;

    public CountriesCultureServiceImpl(CountriesCultureRepository countriesCultureRepository, CountriesCultureMapper countriesCultureMapper) {
        this.countriesCultureRepository = countriesCultureRepository;
        this.countriesCultureMapper = countriesCultureMapper;
    }

    /**
     * Save a countriesCulture.
     *
     * @param countriesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CountriesCultureDTO save(CountriesCultureDTO countriesCultureDTO) {
        log.debug("Request to save CountriesCulture : {}", countriesCultureDTO);
        CountriesCulture countriesCulture = countriesCultureMapper.toEntity(countriesCultureDTO);
        countriesCulture = countriesCultureRepository.save(countriesCulture);
        return countriesCultureMapper.toDto(countriesCulture);
    }

    /**
     * Get all the countriesCultures.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountriesCultureDTO> findAll() {
        log.debug("Request to get all CountriesCultures");
        return countriesCultureRepository.findAll().stream()
            .map(countriesCultureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one countriesCulture by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CountriesCultureDTO> findOne(Long id) {
        log.debug("Request to get CountriesCulture : {}", id);
        return countriesCultureRepository.findById(id)
            .map(countriesCultureMapper::toDto);
    }

    /**
     * Delete the countriesCulture by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountriesCulture : {}", id);

        countriesCultureRepository.deleteById(id);
    }
}
