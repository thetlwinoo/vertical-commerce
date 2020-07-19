package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StateProvincesCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.StateProvincesCulture}.
 */
public interface StateProvincesCultureService {

    /**
     * Save a stateProvincesCulture.
     *
     * @param stateProvincesCultureDTO the entity to save.
     * @return the persisted entity.
     */
    StateProvincesCultureDTO save(StateProvincesCultureDTO stateProvincesCultureDTO);

    /**
     * Get all the stateProvincesCultures.
     *
     * @return the list of entities.
     */
    List<StateProvincesCultureDTO> findAll();


    /**
     * Get the "id" stateProvincesCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateProvincesCultureDTO> findOne(Long id);

    /**
     * Delete the "id" stateProvincesCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
