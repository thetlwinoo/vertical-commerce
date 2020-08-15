package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductsLocalizeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductsLocalize}.
 */
public interface ProductsLocalizeService {

    /**
     * Save a productsLocalize.
     *
     * @param productsLocalizeDTO the entity to save.
     * @return the persisted entity.
     */
    ProductsLocalizeDTO save(ProductsLocalizeDTO productsLocalizeDTO);

    /**
     * Get all the productsLocalizes.
     *
     * @return the list of entities.
     */
    List<ProductsLocalizeDTO> findAll();


    /**
     * Get the "id" productsLocalize.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductsLocalizeDTO> findOne(Long id);

    /**
     * Delete the "id" productsLocalize.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
