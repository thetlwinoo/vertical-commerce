package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.MaterialsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.MaterialsCulture}.
 */
public interface MaterialsCultureService {

    /**
     * Save a materialsCulture.
     *
     * @param materialsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    MaterialsCultureDTO save(MaterialsCultureDTO materialsCultureDTO);

    /**
     * Get all the materialsCultures.
     *
     * @return the list of entities.
     */
    List<MaterialsCultureDTO> findAll();


    /**
     * Get the "id" materialsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" materialsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
