package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CardTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CardTypes}.
 */
public interface CardTypesService {

    /**
     * Save a cardTypes.
     *
     * @param cardTypesDTO the entity to save.
     * @return the persisted entity.
     */
    CardTypesDTO save(CardTypesDTO cardTypesDTO);

    /**
     * Get all the cardTypes.
     *
     * @return the list of entities.
     */
    List<CardTypesDTO> findAll();


    /**
     * Get the "id" cardTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CardTypesDTO> findOne(Long id);

    /**
     * Delete the "id" cardTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
