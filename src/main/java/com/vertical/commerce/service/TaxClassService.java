package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TaxClassDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TaxClass}.
 */
public interface TaxClassService {

    /**
     * Save a taxClass.
     *
     * @param taxClassDTO the entity to save.
     * @return the persisted entity.
     */
    TaxClassDTO save(TaxClassDTO taxClassDTO);

    /**
     * Get all the taxClasses.
     *
     * @return the list of entities.
     */
    List<TaxClassDTO> findAll();


    /**
     * Get the "id" taxClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxClassDTO> findOne(Long id);

    /**
     * Delete the "id" taxClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
