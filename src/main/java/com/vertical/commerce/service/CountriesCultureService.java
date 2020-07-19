package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CountriesCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CountriesCulture}.
 */
public interface CountriesCultureService {

    /**
     * Save a countriesCulture.
     *
     * @param countriesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    CountriesCultureDTO save(CountriesCultureDTO countriesCultureDTO);

    /**
     * Get all the countriesCultures.
     *
     * @return the list of entities.
     */
    List<CountriesCultureDTO> findAll();


    /**
     * Get the "id" countriesCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountriesCultureDTO> findOne(Long id);

    /**
     * Delete the "id" countriesCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
