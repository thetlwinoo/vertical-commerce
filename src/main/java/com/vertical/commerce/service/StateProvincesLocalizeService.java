package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StateProvincesLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.StateProvincesLocalize}.
 */
public interface StateProvincesLocalizeService {

    /**
     * Save a stateProvincesLocalize.
     *
     * @param stateProvincesLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    StateProvincesLocalizeDTO save(StateProvincesLocalizeDTO stateProvincesLocalizeDTO);

    /**
     * Get all the stateProvincesLocalizes.
     *
     * @return the list of entities.
     */
    List<StateProvincesLocalizeDTO> findAll();


    /**
     * Get the "id" stateProvincesLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateProvincesLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" stateProvincesLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
