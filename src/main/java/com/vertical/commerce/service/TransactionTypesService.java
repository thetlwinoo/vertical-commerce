package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.TransactionTypesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.TransactionTypes}.
 */
public interface TransactionTypesService {

    /**
     * Save a transactionTypes.
     *
     * @param transactionTypesDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionTypesDTO save(TransactionTypesDTO transactionTypesDTO);

    /**
     * Get all the transactionTypes.
     *
     * @return the list of entities.
     */
    List<TransactionTypesDTO> findAll();


    /**
     * Get the "id" transactionTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionTypesDTO> findOne(Long id);

    /**
     * Delete the "id" transactionTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
