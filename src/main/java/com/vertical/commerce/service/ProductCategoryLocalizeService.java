package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductCategoryLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductCategoryLocalize}.
 */
public interface ProductCategoryLocalizeService {

    /**
     * Save a productCategoryLocalize.
     *
     * @param productCategoryLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductCategoryLocalizeDTO save(ProductCategoryLocalizeDTO productCategoryLocalizeDTO);

    /**
     * Get all the productCategoryLocalizes.
     *
     * @return the list of entities.
     */
    List<ProductCategoryLocalizeDTO> findAll();


    /**
     * Get the "id" productCategoryLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductCategoryLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" productCategoryLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
