package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.SubscriptionsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.SubscriptionsDTO;
import com.vertical.commerce.service.dto.SubscriptionsCriteria;
import com.vertical.commerce.service.SubscriptionsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.Subscriptions}.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionsResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionsResource.class);

    private static final String ENTITY_NAME = "vscommerceSubscriptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionsService subscriptionsService;

    private final SubscriptionsQueryService subscriptionsQueryService;

    public SubscriptionsResource(SubscriptionsService subscriptionsService, SubscriptionsQueryService subscriptionsQueryService) {
        this.subscriptionsService = subscriptionsService;
        this.subscriptionsQueryService = subscriptionsQueryService;
    }

    /**
     * {@code POST  /subscriptions} : Create a new subscriptions.
     *
     * @param subscriptionsDTO the subscriptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionsDTO, or with status {@code 400 (Bad Request)} if the subscriptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscriptions")
    public ResponseEntity<SubscriptionsDTO> createSubscriptions(@Valid @RequestBody SubscriptionsDTO subscriptionsDTO) throws URISyntaxException {
        log.debug("REST request to save Subscriptions : {}", subscriptionsDTO);
        if (subscriptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new subscriptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriptionsDTO result = subscriptionsService.save(subscriptionsDTO);
        return ResponseEntity.created(new URI("/api/subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscriptions} : Updates an existing subscriptions.
     *
     * @param subscriptionsDTO the subscriptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionsDTO,
     * or with status {@code 400 (Bad Request)} if the subscriptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscriptions")
    public ResponseEntity<SubscriptionsDTO> updateSubscriptions(@Valid @RequestBody SubscriptionsDTO subscriptionsDTO) throws URISyntaxException {
        log.debug("REST request to update Subscriptions : {}", subscriptionsDTO);
        if (subscriptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubscriptionsDTO result = subscriptionsService.save(subscriptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subscriptions} : get all the subscriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptions in body.
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionsDTO>> getAllSubscriptions(SubscriptionsCriteria criteria) {
        log.debug("REST request to get Subscriptions by criteria: {}", criteria);
        List<SubscriptionsDTO> entityList = subscriptionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /subscriptions/count} : count all the subscriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/subscriptions/count")
    public ResponseEntity<Long> countSubscriptions(SubscriptionsCriteria criteria) {
        log.debug("REST request to count Subscriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(subscriptionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subscriptions/:id} : get the "id" subscriptions.
     *
     * @param id the id of the subscriptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscriptions/{id}")
    public ResponseEntity<SubscriptionsDTO> getSubscriptions(@PathVariable Long id) {
        log.debug("REST request to get Subscriptions : {}", id);
        Optional<SubscriptionsDTO> subscriptionsDTO = subscriptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriptionsDTO);
    }

    /**
     * {@code DELETE  /subscriptions/:id} : delete the "id" subscriptions.
     *
     * @param id the id of the subscriptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity<Void> deleteSubscriptions(@PathVariable Long id) {
        log.debug("REST request to delete Subscriptions : {}", id);

        subscriptionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
