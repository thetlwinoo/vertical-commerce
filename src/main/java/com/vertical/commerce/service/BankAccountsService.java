package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.BankAccountsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.BankAccounts}.
 */
public interface BankAccountsService {

    /**
     * Save a bankAccounts.
     *
     * @param bankAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    BankAccountsDTO save(BankAccountsDTO bankAccountsDTO);

    /**
     * Get all the bankAccounts.
     *
     * @return the list of entities.
     */
    List<BankAccountsDTO> findAll();


    /**
     * Get the "id" bankAccounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankAccountsDTO> findOne(Long id);

    /**
     * Delete the "id" bankAccounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
