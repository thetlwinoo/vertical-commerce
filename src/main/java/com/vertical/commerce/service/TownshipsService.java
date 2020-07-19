package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownshipsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Townships}.
 */
public interface TownshipsService {

    /**
     * Save a townships.
     *
     * @param townshipsDTO the entity to save.
     * @return the persisted entity.
     */
    TownshipsDTO save(TownshipsDTO townshipsDTO);

    /**
     * Get all the townships.
     *
     * @return the list of entities.
     */
    List<TownshipsDTO> findAll();


    /**
     * Get the "id" townships.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownshipsDTO> findOne(Long id);

    /**
     * Delete the "id" townships.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
