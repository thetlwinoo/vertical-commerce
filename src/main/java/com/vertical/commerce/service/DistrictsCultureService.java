package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.DistrictsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.DistrictsCulture}.
 */
public interface DistrictsCultureService {

    /**
     * Save a districtsCulture.
     *
     * @param districtsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictsCultureDTO save(DistrictsCultureDTO districtsCultureDTO);

    /**
     * Get all the districtsCultures.
     *
     * @return the list of entities.
     */
    List<DistrictsCultureDTO> findAll();


    /**
     * Get the "id" districtsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" districtsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
