package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CardTypesService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CardTypesDTO;
import com.vertical.commerce.service.dto.CardTypesCriteria;
import com.vertical.commerce.service.CardTypesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.CardTypes}.
 */
@RestController
@RequestMapping("/api")
public class CardTypesResource {

    private final Logger log = LoggerFactory.getLogger(CardTypesResource.class);

    private static final String ENTITY_NAME = "vscommerceCardTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardTypesService cardTypesService;

    private final CardTypesQueryService cardTypesQueryService;

    public CardTypesResource(CardTypesService cardTypesService, CardTypesQueryService cardTypesQueryService) {
        this.cardTypesService = cardTypesService;
        this.cardTypesQueryService = cardTypesQueryService;
    }

    /**
     * {@code POST  /card-types} : Create a new cardTypes.
     *
     * @param cardTypesDTO the cardTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardTypesDTO, or with status {@code 400 (Bad Request)} if the cardTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/card-types")
    public ResponseEntity<CardTypesDTO> createCardTypes(@RequestBody CardTypesDTO cardTypesDTO) throws URISyntaxException {
        log.debug("REST request to save CardTypes : {}", cardTypesDTO);
        if (cardTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cardTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardTypesDTO result = cardTypesService.save(cardTypesDTO);
        return ResponseEntity.created(new URI("/api/card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /card-types} : Updates an existing cardTypes.
     *
     * @param cardTypesDTO the cardTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardTypesDTO,
     * or with status {@code 400 (Bad Request)} if the cardTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/card-types")
    public ResponseEntity<CardTypesDTO> updateCardTypes(@RequestBody CardTypesDTO cardTypesDTO) throws URISyntaxException {
        log.debug("REST request to update CardTypes : {}", cardTypesDTO);
        if (cardTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CardTypesDTO result = cardTypesService.save(cardTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /card-types} : get all the cardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardTypes in body.
     */
    @GetMapping("/card-types")
    public ResponseEntity<List<CardTypesDTO>> getAllCardTypes(CardTypesCriteria criteria) {
        log.debug("REST request to get CardTypes by criteria: {}", criteria);
        List<CardTypesDTO> entityList = cardTypesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /card-types/count} : count all the cardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/card-types/count")
    public ResponseEntity<Long> countCardTypes(CardTypesCriteria criteria) {
        log.debug("REST request to count CardTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cardTypesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /card-types/:id} : get the "id" cardTypes.
     *
     * @param id the id of the cardTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/card-types/{id}")
    public ResponseEntity<CardTypesDTO> getCardTypes(@PathVariable Long id) {
        log.debug("REST request to get CardTypes : {}", id);
        Optional<CardTypesDTO> cardTypesDTO = cardTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardTypesDTO);
    }

    /**
     * {@code DELETE  /card-types/:id} : delete the "id" cardTypes.
     *
     * @param id the id of the cardTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/card-types/{id}")
    public ResponseEntity<Void> deleteCardTypes(@PathVariable Long id) {
        log.debug("REST request to delete CardTypes : {}", id);

        cardTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
