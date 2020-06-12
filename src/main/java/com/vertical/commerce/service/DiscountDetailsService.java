package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.DiscountDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.DiscountDetails}.
 */
public interface DiscountDetailsService {

    /**
     * Save a discountDetails.
     *
     * @param discountDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountDetailsDTO save(DiscountDetailsDTO discountDetailsDTO);

    /**
     * Get all the discountDetails.
     *
     * @return the list of entities.
     */
    List<DiscountDetailsDTO> findAll();


    /**
     * Get the "id" discountDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" discountDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
