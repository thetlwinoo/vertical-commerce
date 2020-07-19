package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.RegionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Regions}.
 */
public interface RegionsService {

    /**
     * Save a regions.
     *
     * @param regionsDTO the entity to save.
     * @return the persisted entity.
     */
    RegionsDTO save(RegionsDTO regionsDTO);

    /**
     * Get all the regions.
     *
     * @return the list of entities.
     */
    List<RegionsDTO> findAll();


    /**
     * Get the "id" regions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionsDTO> findOne(Long id);

    /**
     * Delete the "id" regions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
