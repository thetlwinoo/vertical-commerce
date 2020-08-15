package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TownsLocalize}.
 */
public interface TownsLocalizeService {

    /**
     * Save a townsLocalize.
     *
     * @param townsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    TownsLocalizeDTO save(TownsLocalizeDTO townsLocalizeDTO);

    /**
     * Get all the townsLocalizes.
     *
     * @return the list of entities.
     */
    List<TownsLocalizeDTO> findAll();


    /**
     * Get the "id" townsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" townsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
