package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TownshipsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TownshipsLocalize}.
 */
public interface TownshipsLocalizeService {

    /**
     * Save a townshipsLocalize.
     *
     * @param townshipsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    TownshipsLocalizeDTO save(TownshipsLocalizeDTO townshipsLocalizeDTO);

    /**
     * Get all the townshipsLocalizes.
     *
     * @return the list of entities.
     */
    List<TownshipsLocalizeDTO> findAll();


    /**
     * Get the "id" townshipsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownshipsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" townshipsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
