package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductDocumentsCultureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductDocumentsCulture}.
 */
public interface ProductDocumentsCultureService {

    /**
     * Save a productDocumentsCulture.
     *
     * @param productDocumentsCultureDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDocumentsCultureDTO save(ProductDocumentsCultureDTO productDocumentsCultureDTO);

    /**
     * Get all the productDocumentsCultures.
     *
     * @return the list of entities.
     */
    List<ProductDocumentsCultureDTO> findAll();


    /**
     * Get the "id" productDocumentsCulture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDocumentsCultureDTO> findOne(Long id);

    /**
     * Delete the "id" productDocumentsCulture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
