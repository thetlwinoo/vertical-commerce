package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductBrandLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductBrandLocalize}.
 */
public interface ProductBrandLocalizeService {

    /**
     * Save a productBrandLocalize.
     *
     * @param productBrandLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductBrandLocalizeDTO save(ProductBrandLocalizeDTO productBrandLocalizeDTO);

    /**
     * Get all the productBrandLocalizes.
     *
     * @return the list of entities.
     */
    List<ProductBrandLocalizeDTO> findAll();


    /**
     * Get the "id" productBrandLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductBrandLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" productBrandLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
