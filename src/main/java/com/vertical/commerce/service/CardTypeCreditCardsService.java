package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CardTypeCreditCards}.
 */
public interface CardTypeCreditCardsService {

    /**
     * Save a cardTypeCreditCards.
     *
     * @param cardTypeCreditCardsDTO the entity to save.
     * @return the persisted entity.
     */
    CardTypeCreditCardsDTO save(CardTypeCreditCardsDTO cardTypeCreditCardsDTO);

    /**
     * Get all the cardTypeCreditCards.
     *
     * @return the list of entities.
     */
    List<CardTypeCreditCardsDTO> findAll();


    /**
     * Get the "id" cardTypeCreditCards.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CardTypeCreditCardsDTO> findOne(Long id);

    /**
     * Delete the "id" cardTypeCreditCards.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
