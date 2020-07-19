package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductCategoryCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductCategoryCulture}.
 */
public interface ProductCategoryCultureService {

    /**
     * Save a productCategoryCulture.
     *
     * @param productCategoryCultureDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCategoryCultureDTO save(ProductCategoryCultureDTO productCategoryCultureDTO);

    /**
     * Get all the productCategoryCultures.
     *
     * @return the list of entities.
     */
    List<ProductCategoryCultureDTO> findAll();


    /**
     * Get the "id" productCategoryCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategoryCultureDTO> findOne(Long id);

    /**
     * Delete the "id" productCategoryCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
