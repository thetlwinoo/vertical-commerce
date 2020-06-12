package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.BusinessEntityContactDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.BusinessEntityContact}.
 */
public interface BusinessEntityContactService {

    /**
     * Save a businessEntityContact.
     *
     * @param businessEntityContactDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessEntityContactDTO save(BusinessEntityContactDTO businessEntityContactDTO);

    /**
     * Get all the businessEntityContacts.
     *
     * @return the list of entities.
     */
    List<BusinessEntityContactDTO> findAll();


    /**
     * Get the "id" businessEntityContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessEntityContactDTO> findOne(Long id);

    /**
     * Delete the "id" businessEntityContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
