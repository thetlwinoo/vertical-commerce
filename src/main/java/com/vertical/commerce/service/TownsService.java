package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Towns}.
 */
public interface TownsService {

    /**
     * Save a towns.
     *
     * @param townsDTO the entity to save.
     * @return the persisted entity.
     */
    TownsDTO save(TownsDTO townsDTO);

    /**
     * Get all the towns.
     *
     * @return the list of entities.
     */
    List<TownsDTO> findAll();


    /**
     * Get the "id" towns.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownsDTO> findOne(Long id);

    /**
     * Delete the "id" towns.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
