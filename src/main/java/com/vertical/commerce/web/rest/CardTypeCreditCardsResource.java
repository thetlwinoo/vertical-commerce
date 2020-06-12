package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CardTypeCreditCardsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CardTypeCreditCardsDTO;
import com.vertical.commerce.service.dto.CardTypeCreditCardsCriteria;
import com.vertical.commerce.service.CardTypeCreditCardsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CardTypeCreditCards}.
 */
@RestController
@RequestMapping("/api")
public class CardTypeCreditCardsResource {

    private final Logger log = LoggerFactory.getLogger(CardTypeCreditCardsResource.class);

    private static final String ENTITY_NAME = "vscommerceCardTypeCreditCards";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CardTypeCreditCardsService cardTypeCreditCardsService;

    private final CardTypeCreditCardsQueryService cardTypeCreditCardsQueryService;

    public CardTypeCreditCardsResource(CardTypeCreditCardsService cardTypeCreditCardsService, CardTypeCreditCardsQueryService cardTypeCreditCardsQueryService) {
        this.cardTypeCreditCardsService = cardTypeCreditCardsService;
        this.cardTypeCreditCardsQueryService = cardTypeCreditCardsQueryService;
    }

    /**
     * {@code POST  /card-type-credit-cards} : Create a new cardTypeCreditCards.
     *
     * @param cardTypeCreditCardsDTO the cardTypeCreditCardsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cardTypeCreditCardsDTO, or with status {@code 400 (Bad Request)} if the cardTypeCreditCards has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/card-type-credit-cards")
    public ResponseEntity<CardTypeCreditCardsDTO> createCardTypeCreditCards(@RequestBody CardTypeCreditCardsDTO cardTypeCreditCardsDTO) throws URISyntaxException {
        log.debug("REST request to save CardTypeCreditCards : {}", cardTypeCreditCardsDTO);
        if (cardTypeCreditCardsDTO.getId() != null) {
            throw new BadRequestAlertException("A new cardTypeCreditCards cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CardTypeCreditCardsDTO result = cardTypeCreditCardsService.save(cardTypeCreditCardsDTO);
        return ResponseEntity.created(new URI("/api/card-type-credit-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /card-type-credit-cards} : Updates an existing cardTypeCreditCards.
     *
     * @param cardTypeCreditCardsDTO the cardTypeCreditCardsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cardTypeCreditCardsDTO,
     * or with status {@code 400 (Bad Request)} if the cardTypeCreditCardsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cardTypeCreditCardsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/card-type-credit-cards")
    public ResponseEntity<CardTypeCreditCardsDTO> updateCardTypeCreditCards(@RequestBody CardTypeCreditCardsDTO cardTypeCreditCardsDTO) throws URISyntaxException {
        log.debug("REST request to update CardTypeCreditCards : {}", cardTypeCreditCardsDTO);
        if (cardTypeCreditCardsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CardTypeCreditCardsDTO result = cardTypeCreditCardsService.save(cardTypeCreditCardsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cardTypeCreditCardsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /card-type-credit-cards} : get all the cardTypeCreditCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cardTypeCreditCards in body.
     */
    @GetMapping("/card-type-credit-cards")
    public ResponseEntity<List<CardTypeCreditCardsDTO>> getAllCardTypeCreditCards(CardTypeCreditCardsCriteria criteria) {
        log.debug("REST request to get CardTypeCreditCards by criteria: {}", criteria);
        List<CardTypeCreditCardsDTO> entityList = cardTypeCreditCardsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /card-type-credit-cards/count} : count all the cardTypeCreditCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/card-type-credit-cards/count")
    public ResponseEntity<Long> countCardTypeCreditCards(CardTypeCreditCardsCriteria criteria) {
        log.debug("REST request to count CardTypeCreditCards by criteria: {}", criteria);
        return ResponseEntity.ok().body(cardTypeCreditCardsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /card-type-credit-cards/:id} : get the "id" cardTypeCreditCards.
     *
     * @param id the id of the cardTypeCreditCardsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cardTypeCreditCardsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/card-type-credit-cards/{id}")
    public ResponseEntity<CardTypeCreditCardsDTO> getCardTypeCreditCards(@PathVariable Long id) {
        log.debug("REST request to get CardTypeCreditCards : {}", id);
        Optional<CardTypeCreditCardsDTO> cardTypeCreditCardsDTO = cardTypeCreditCardsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cardTypeCreditCardsDTO);
    }

    /**
     * {@code DELETE  /card-type-credit-cards/:id} : delete the "id" cardTypeCreditCards.
     *
     * @param id the id of the cardTypeCreditCardsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/card-type-credit-cards/{id}")
    public ResponseEntity<Void> deleteCardTypeCreditCards(@PathVariable Long id) {
        log.debug("REST request to delete CardTypeCreditCards : {}", id);

        cardTypeCreditCardsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
