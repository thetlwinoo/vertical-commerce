package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.SuppliersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.Suppliers}.
 */
public interface SuppliersService {

    /**
     * Save a suppliers.
     *
     * @param suppliersDTO the entity to save.
     * @return the persisted entity.
     */
    SuppliersDTO save(SuppliersDTO suppliersDTO);

    /**
     * Get all the suppliers.
     *
     * @return the list of entities.
     */
    List<SuppliersDTO> findAll();

    /**
     * Get all the suppliers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<SuppliersDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" suppliers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SuppliersDTO> findOne(Long id);

    /**
     * Delete the "id" suppliers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
