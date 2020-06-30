package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TaxDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Tax}.
 */
public interface TaxService {

    /**
     * Save a tax.
     *
     * @param taxDTO the entity to save.
     * @return the persisted entity.
     */
    TaxDTO save(TaxDTO taxDTO);

    /**
     * Get all the taxes.
     *
     * @return the list of entities.
     */
    List<TaxDTO> findAll();


    /**
     * Get the "id" tax.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaxDTO> findOne(Long id);

    /**
     * Delete the "id" tax.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
