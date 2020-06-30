package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.LogisticsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Logistics}.
 */
public interface LogisticsService {

    /**
     * Save a logistics.
     *
     * @param logisticsDTO the entity to save.
     * @return the persisted entity.
     */
    LogisticsDTO save(LogisticsDTO logisticsDTO);

    /**
     * Get all the logistics.
     *
     * @return the list of entities.
     */
    List<LogisticsDTO> findAll();


    /**
     * Get the "id" logistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LogisticsDTO> findOne(Long id);

    /**
     * Delete the "id" logistics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
