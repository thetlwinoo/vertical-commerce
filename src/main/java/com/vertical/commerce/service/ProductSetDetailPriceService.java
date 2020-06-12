package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ProductSetDetailPrice}.
 */
public interface ProductSetDetailPriceService {

    /**
     * Save a productSetDetailPrice.
     *
     * @param productSetDetailPriceDTO the entity to save.
     * @return the persisted entity.
     */
    ProductSetDetailPriceDTO save(ProductSetDetailPriceDTO productSetDetailPriceDTO);

    /**
     * Get all the productSetDetailPrices.
     *
     * @return the list of entities.
     */
    List<ProductSetDetailPriceDTO> findAll();


    /**
     * Get the "id" productSetDetailPrice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductSetDetailPriceDTO> findOne(Long id);

    /**
     * Delete the "id" productSetDetailPrice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
