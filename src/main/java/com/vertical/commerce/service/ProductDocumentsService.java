package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductDocumentsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductDocuments}.
 */
public interface ProductDocumentsService {

    /**
     * Save a productDocuments.
     *
     * @param productDocumentsDTO the entity to save.
     * @return the persisted entity.
     */
    ProductDocumentsDTO save(ProductDocumentsDTO productDocumentsDTO);

    /**
     * Get all the productDocuments.
     *
     * @return the list of entities.
     */
    List<ProductDocumentsDTO> findAll();
    /**
     * Get all the ProductDocumentsDTO where Product is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ProductDocumentsDTO> findAllWhereProductIsNull();


    /**
     * Get the "id" productDocuments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductDocumentsDTO> findOne(Long id);

    /**
     * Delete the "id" productDocuments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
