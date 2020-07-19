package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductsCulture}.
 */
public interface ProductsCultureService {

    /**
     * Save a productsCulture.
     *
     * @param productsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    ProductsCultureDTO save(ProductsCultureDTO productsCultureDTO);

    /**
     * Get all the productsCultures.
     *
     * @return the list of entities.
     */
    List<ProductsCultureDTO> findAll();


    /**
     * Get the "id" productsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" productsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
