package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.DistrictsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Districts}.
 */
public interface DistrictsService {

    /**
     * Save a districts.
     *
     * @param districtsDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictsDTO save(DistrictsDTO districtsDTO);

    /**
     * Get all the districts.
     *
     * @return the list of entities.
     */
    List<DistrictsDTO> findAll();


    /**
     * Get the "id" districts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictsDTO> findOne(Long id);

    /**
     * Delete the "id" districts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
