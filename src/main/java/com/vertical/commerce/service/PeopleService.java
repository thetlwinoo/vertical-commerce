package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.PeopleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.People}.
 */
public interface PeopleService {

    /**
     * Save a people.
     *
     * @param peopleDTO the entity to save.
     * @return the persisted entity.
     */
    PeopleDTO save(PeopleDTO peopleDTO);

    /**
     * Get all the people.
     *
     * @return the list of entities.
     */
    List<PeopleDTO> findAll();
    /**
     * Get all the PeopleDTO where Cart is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PeopleDTO> findAllWhereCartIsNull();
    /**
     * Get all the PeopleDTO where Wishlist is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PeopleDTO> findAllWhereWishlistIsNull();
    /**
     * Get all the PeopleDTO where Compare is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PeopleDTO> findAllWhereCompareIsNull();

    /**
     * Get all the people with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PeopleDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" people.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeopleDTO> findOne(Long id);

    /**
     * Delete the "id" people.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
