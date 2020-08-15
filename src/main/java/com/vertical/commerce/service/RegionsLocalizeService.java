package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.RegionsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.RegionsLocalize}.
 */
public interface RegionsLocalizeService {

    /**
     * Save a regionsLocalize.
     *
     * @param regionsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    RegionsLocalizeDTO save(RegionsLocalizeDTO regionsLocalizeDTO);

    /**
     * Get all the regionsLocalizes.
     *
     * @return the list of entities.
     */
    List<RegionsLocalizeDTO> findAll();


    /**
     * Get the "id" regionsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegionsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" regionsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
