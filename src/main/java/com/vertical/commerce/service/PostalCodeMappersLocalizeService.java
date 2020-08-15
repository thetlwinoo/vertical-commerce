package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.PostalCodeMappersLocalize}.
 */
public interface PostalCodeMappersLocalizeService {

    /**
     * Save a postalCodeMappersLocalize.
     *
     * @param postalCodeMappersLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    PostalCodeMappersLocalizeDTO save(PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO);

    /**
     * Get all the postalCodeMappersLocalizes.
     *
     * @return the list of entities.
     */
    List<PostalCodeMappersLocalizeDTO> findAll();


    /**
     * Get the "id" postalCodeMappersLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostalCodeMappersLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" postalCodeMappersLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
