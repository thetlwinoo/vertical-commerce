package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.DiscountTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.DiscountTypes}.
 */
public interface DiscountTypesService {

    /**
     * Save a discountTypes.
     *
     * @param discountTypesDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountTypesDTO save(DiscountTypesDTO discountTypesDTO);

    /**
     * Get all the discountTypes.
     *
     * @return the list of entities.
     */
    List<DiscountTypesDTO> findAll();


    /**
     * Get the "id" discountTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountTypesDTO> findOne(Long id);

    /**
     * Delete the "id" discountTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
