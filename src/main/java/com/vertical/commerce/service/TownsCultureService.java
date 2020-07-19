package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TownsCulture}.
 */
public interface TownsCultureService {

    /**
     * Save a townsCulture.
     *
     * @param townsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    TownsCultureDTO save(TownsCultureDTO townsCultureDTO);

    /**
     * Get all the townsCultures.
     *
     * @return the list of entities.
     */
    List<TownsCultureDTO> findAll();


    /**
     * Get the "id" townsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" townsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
