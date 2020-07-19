package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownshipsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TownshipsCulture}.
 */
public interface TownshipsCultureService {

    /**
     * Save a townshipsCulture.
     *
     * @param townshipsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    TownshipsCultureDTO save(TownshipsCultureDTO townshipsCultureDTO);

    /**
     * Get all the townshipsCultures.
     *
     * @return the list of entities.
     */
    List<TownshipsCultureDTO> findAll();


    /**
     * Get the "id" townshipsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownshipsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" townshipsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
