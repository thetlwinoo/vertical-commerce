package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CitiesLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CitiesLocalize}.
 */
public interface CitiesLocalizeService {

    /**
     * Save a citiesLocalize.
     *
     * @param citiesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    CitiesLocalizeDTO save(CitiesLocalizeDTO citiesLocalizeDTO);

    /**
     * Get all the citiesLocalizes.
     *
     * @return the list of entities.
     */
    List<CitiesLocalizeDTO> findAll();


    /**
     * Get the "id" citiesLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitiesLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" citiesLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
