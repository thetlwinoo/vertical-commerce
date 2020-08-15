package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.CitiesLocalizeService;
import com.vertical.commerce.domain.CitiesLocalize;
import com.vertical.commerce.repository.CitiesLocalizeRepository;
import com.vertical.commerce.service.dto.CitiesLocalizeDTO;
import com.vertical.commerce.service.mapper.CitiesLocalizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CitiesLocalize}.
 */
@Service
@Transactional
public class CitiesLocalizeServiceImpl implements CitiesLocalizeService {

    private final Logger log = LoggerFactory.getLogger(CitiesLocalizeServiceImpl.class);

    private final CitiesLocalizeRepository citiesLocalizeRepository;

    private final CitiesLocalizeMapper citiesLocalizeMapper;

    public CitiesLocalizeServiceImpl(CitiesLocalizeRepository citiesLocalizeRepository, CitiesLocalizeMapper citiesLocalizeMapper) {
        this.citiesLocalizeRepository = citiesLocalizeRepository;
        this.citiesLocalizeMapper = citiesLocalizeMapper;
    }

    /**
     * Save a citiesLocalize.
     *
     * @param citiesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CitiesLocalizeDTO save(CitiesLocalizeDTO citiesLocalizeDTO) {
        log.debug("Request to save CitiesLocalize : {}", citiesLocalizeDTO);
        CitiesLocalize citiesLocalize = citiesLocalizeMapper.toEntity(citiesLocalizeDTO);
        citiesLocalize = citiesLocalizeRepository.save(citiesLocalize);
        return citiesLocalizeMapper.toDto(citiesLocalize);
    }

    /**
     * Get all the citiesLocalizes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CitiesLocalizeDTO> findAll() {
        log.debug("Request to get all CitiesLocalizes");
        return citiesLocalizeRepository.findAll().stream()
            .map(citiesLocalizeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one citiesLocalize by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CitiesLocalizeDTO> findOne(Long id) {
        log.debug("Request to get CitiesLocalize : {}", id);
        return citiesLocalizeRepository.findById(id)
            .map(citiesLocalizeMapper::toDto);
    }

    /**
     * Delete the citiesLocalize by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CitiesLocalize : {}", id);

        citiesLocalizeRepository.deleteById(id);
    }
}
