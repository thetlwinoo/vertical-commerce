package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ReceiptsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Receipts}.
 */
public interface ReceiptsService {

    /**
     * Save a receipts.
     *
     * @param receiptsDTO the entity to save.
     * @return the persisted entity.
     */
    ReceiptsDTO save(ReceiptsDTO receiptsDTO);

    /**
     * Get all the receipts.
     *
     * @return the list of entities.
     */
    List<ReceiptsDTO> findAll();


    /**
     * Get the "id" receipts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReceiptsDTO> findOne(Long id);

    /**
     * Delete the "id" receipts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
