package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.MaterialsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.MaterialsLocalize}.
 */
public interface MaterialsLocalizeService {

    /**
     * Save a materialsLocalize.
     *
     * @param materialsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    MaterialsLocalizeDTO save(MaterialsLocalizeDTO materialsLocalizeDTO);

    /**
     * Get all the materialsLocalizes.
     *
     * @return the list of entities.
     */
    List<MaterialsLocalizeDTO> findAll();


    /**
     * Get the "id" materialsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" materialsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
