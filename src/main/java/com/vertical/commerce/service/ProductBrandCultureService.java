package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductBrandCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductBrandCulture}.
 */
public interface ProductBrandCultureService {

    /**
     * Save a productBrandCulture.
     *
     * @param productBrandCultureDTO the entity to save.
     * @return the persisted entity.
     */
    ProductBrandCultureDTO save(ProductBrandCultureDTO productBrandCultureDTO);

    /**
     * Get all the productBrandCultures.
     *
     * @return the list of entities.
     */
    List<ProductBrandCultureDTO> findAll();


    /**
     * Get the "id" productBrandCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductBrandCultureDTO> findOne(Long id);

    /**
     * Delete the "id" productBrandCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
