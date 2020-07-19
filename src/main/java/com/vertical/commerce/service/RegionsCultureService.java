package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.RegionsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.RegionsCulture}.
 */
public interface RegionsCultureService {

    /**
     * Save a regionsCulture.
     *
     * @param regionsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    RegionsCultureDTO save(RegionsCultureDTO regionsCultureDTO);

    /**
     * Get all the regionsCultures.
     *
     * @return the list of entities.
     */
    List<RegionsCultureDTO> findAll();


    /**
     * Get the "id" regionsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" regionsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
