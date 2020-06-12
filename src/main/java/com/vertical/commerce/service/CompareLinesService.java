package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CompareLinesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CompareLines}.
 */
public interface CompareLinesService {

    /**
     * Save a compareLines.
     *
     * @param compareLinesDTO the entity to save.
     * @return the persisted entity.
     */
    CompareLinesDTO save(CompareLinesDTO compareLinesDTO);

    /**
     * Get all the compareLines.
     *
     * @return the list of entities.
     */
    List<CompareLinesDTO> findAll();


    /**
     * Get the "id" compareLines.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompareLinesDTO> findOne(Long id);

    /**
     * Delete the "id" compareLines.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
