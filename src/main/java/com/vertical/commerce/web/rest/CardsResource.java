package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CardsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CardsDTO;
import com.vertical.commerce.service.dto.CardsCriteria;
import com.vertical.commerce.service.CardsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Cards}.
 */
@RestController
@RequestMapping("/api")
public class CardsResource {

    private final Logger log = LoggerFactory.getLogger(CardsResource.class);

    private static final String ENTITY_NAME = "vscommerceCards";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardsService cardsService;

    private final CardsQueryService cardsQueryService;

    public CardsResource(CardsService cardsService, CardsQueryService cardsQueryService) {
        this.cardsService = cardsService;
        this.cardsQueryService = cardsQueryService;
    }

    /**
     * {@code POST  /cards} : Create a new cards.
     *
     * @param cardsDTO the cardsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardsDTO, or with status {@code 400 (Bad Request)} if the cards has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cards")
    public ResponseEntity<CardsDTO> createCards(@RequestBody CardsDTO cardsDTO) throws URISyntaxException {
        log.debug("REST request to save Cards : {}", cardsDTO);
        if (cardsDTO.getId() != null) {
            throw new BadRequestAlertException("A new cards cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardsDTO result = cardsService.save(cardsDTO);
        return ResponseEntity.created(new URI("/api/cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cards} : Updates an existing cards.
     *
     * @param cardsDTO the cardsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardsDTO,
     * or with status {@code 400 (Bad Request)} if the cardsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cards")
    public ResponseEntity<CardsDTO> updateCards(@RequestBody CardsDTO cardsDTO) throws URISyntaxException {
        log.debug("REST request to update Cards : {}", cardsDTO);
        if (cardsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CardsDTO result = cardsService.save(cardsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cards} : get all the cards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cards in body.
     */
    @GetMapping("/cards")
    public ResponseEntity<List<CardsDTO>> getAllCards(CardsCriteria criteria) {
        log.debug("REST request to get Cards by criteria: {}", criteria);
        List<CardsDTO> entityList = cardsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cards/count} : count all the cards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cards/count")
    public ResponseEntity<Long> countCards(CardsCriteria criteria) {
        log.debug("REST request to count Cards by criteria: {}", criteria);
        return ResponseEntity.ok().body(cardsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cards/:id} : get the "id" cards.
     *
     * @param id the id of the cardsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cards/{id}")
    public ResponseEntity<CardsDTO> getCards(@PathVariable Long id) {
        log.debug("REST request to get Cards : {}", id);
        Optional<CardsDTO> cardsDTO = cardsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardsDTO);
    }

    /**
     * {@code DELETE  /cards/:id} : delete the "id" cards.
     *
     * @param id the id of the cardsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> deleteCards(@PathVariable Long id) {
        log.debug("REST request to delete Cards : {}", id);

        cardsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
