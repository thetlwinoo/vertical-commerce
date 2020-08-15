package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CountriesLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CountriesLocalize}.
 */
public interface CountriesLocalizeService {

    /**
     * Save a countriesLocalize.
     *
     * @param countriesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    CountriesLocalizeDTO save(CountriesLocalizeDTO countriesLocalizeDTO);

    /**
     * Get all the countriesLocalizes.
     *
     * @return the list of entities.
     */
    List<CountriesLocalizeDTO> findAll();


    /**
     * Get the "id" countriesLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountriesLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" countriesLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
