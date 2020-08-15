package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.PostalCodeMappersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.PostalCodeMappers}.
 */
public interface PostalCodeMappersService {

    /**
     * Save a postalCodeMappers.
     *
     * @param postalCodeMappersDTO the entity to save.
     * @return the persisted entity.
     */
    PostalCodeMappersDTO save(PostalCodeMappersDTO postalCodeMappersDTO);

    /**
     * Get all the postalCodeMappers.
     *
     * @return the list of entities.
     */
    List<PostalCodeMappersDTO> findAll();


    /**
     * Get the "id" postalCodeMappers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostalCodeMappersDTO> findOne(Long id);

    /**
     * Delete the "id" postalCodeMappers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
