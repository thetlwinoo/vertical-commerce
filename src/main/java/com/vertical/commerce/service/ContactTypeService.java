package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ContactTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ContactType}.
 */
public interface ContactTypeService {

    /**
     * Save a contactType.
     *
     * @param contactTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ContactTypeDTO save(ContactTypeDTO contactTypeDTO);

    /**
     * Get all the contactTypes.
     *
     * @return the list of entities.
     */
    List<ContactTypeDTO> findAll();


    /**
     * Get the "id" contactType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactTypeDTO> findOne(Long id);

    /**
     * Delete the "id" contactType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
