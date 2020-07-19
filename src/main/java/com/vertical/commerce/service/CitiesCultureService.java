package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CitiesCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CitiesCulture}.
 */
public interface CitiesCultureService {

    /**
     * Save a citiesCulture.
     *
     * @param citiesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    CitiesCultureDTO save(CitiesCultureDTO citiesCultureDTO);

    /**
     * Get all the citiesCultures.
     *
     * @return the list of entities.
     */
    List<CitiesCultureDTO> findAll();


    /**
     * Get the "id" citiesCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitiesCultureDTO> findOne(Long id);

    /**
     * Delete the "id" citiesCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
