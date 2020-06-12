package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductBrandDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductBrand}.
 */
public interface ProductBrandService {

    /**
     * Save a productBrand.
     *
     * @param productBrandDTO the entity to save.
     * @return the persisted entity.
     */
    ProductBrandDTO save(ProductBrandDTO productBrandDTO);

    /**
     * Get all the productBrands.
     *
     * @return the list of entities.
     */
    List<ProductBrandDTO> findAll();


    /**
     * Get the "id" productBrand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductBrandDTO> findOne(Long id);

    /**
     * Delete the "id" productBrand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
