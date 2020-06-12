package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CardsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Cards}.
 */
public interface CardsService {

    /**
     * Save a cards.
     *
     * @param cardsDTO the entity to save.
     * @return the persisted entity.
     */
    CardsDTO save(CardsDTO cardsDTO);

    /**
     * Get all the cards.
     *
     * @return the list of entities.
     */
    List<CardsDTO> findAll();


    /**
     * Get the "id" cards.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CardsDTO> findOne(Long id);

    /**
     * Delete the "id" cards.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
