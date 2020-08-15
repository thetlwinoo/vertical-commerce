package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CountriesLocalizeService;
import com.vertical.commerce.domain.CountriesLocalize;
import com.vertical.commerce.repository.CountriesLocalizeRepository;
import com.vertical.commerce.service.dto.CountriesLocalizeDTO;
import com.vertical.commerce.service.mapper.CountriesLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CountriesLocalize}.
 */
@Service
@Transactional
public class CountriesLocalizeServiceImpl implements CountriesLocalizeService {

    private final Logger log = LoggerFactory.getLogger(CountriesLocalizeServiceImpl.class);

    private final CountriesLocalizeRepository countriesLocalizeRepository;

    private final CountriesLocalizeMapper countriesLocalizeMapper;

    public CountriesLocalizeServiceImpl(CountriesLocalizeRepository countriesLocalizeRepository, CountriesLocalizeMapper countriesLocalizeMapper) {
        this.countriesLocalizeRepository = countriesLocalizeRepository;
        this.countriesLocalizeMapper = countriesLocalizeMapper;
    }

    /**
     * Save a countriesLocalize.
     *
     * @param countriesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CountriesLocalizeDTO save(CountriesLocalizeDTO countriesLocalizeDTO) {
        log.debug("Request to save CountriesLocalize : {}", countriesLocalizeDTO);
        CountriesLocalize countriesLocalize = countriesLocalizeMapper.toEntity(countriesLocalizeDTO);
        countriesLocalize = countriesLocalizeRepository.save(countriesLocalize);
        return countriesLocalizeMapper.toDto(countriesLocalize);
    }

    /**
     * Get all the countriesLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CountriesLocalizeDTO> findAll() {
        log.debug("Request to get all CountriesLocalizes");
        return countriesLocalizeRepository.findAll().stream()
            .map(countriesLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one countriesLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CountriesLocalizeDTO> findOne(Long id) {
        log.debug("Request to get CountriesLocalize : {}", id);
        return countriesLocalizeRepository.findById(id)
            .map(countriesLocalizeMapper::toDto);
    }

    /**
     * Delete the countriesLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CountriesLocalize : {}", id);

        countriesLocalizeRepository.deleteById(id);
    }
}
