package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.CreditCardTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.CreditCardType}.
 */
public interface CreditCardTypeService {

    /**
     * Save a creditCardType.
     *
     * @param creditCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CreditCardTypeDTO save(CreditCardTypeDTO creditCardTypeDTO);

    /**
     * Get all the creditCardTypes.
     *
     * @return the list of entities.
     */
    List<CreditCardTypeDTO> findAll();


    /**
     * Get the "id" creditCardType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCardTypeDTO> findOne(Long id);

    /**
     * Delete the "id" creditCardType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
