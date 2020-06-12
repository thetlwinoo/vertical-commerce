package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.TrackingEventService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.TrackingEventDTO;
import com.vertical.commerce.service.dto.TrackingEventCriteria;
import com.vertical.commerce.service.TrackingEventQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.TrackingEvent}.
 */
@RestController
@RequestMapping("/api")
public class TrackingEventResource {

    private final Logger log = LoggerFactory.getLogger(TrackingEventResource.class);

    private static final String ENTITY_NAME = "vscommerceTrackingEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrackingEventService trackingEventService;

    private final TrackingEventQueryService trackingEventQueryService;

    public TrackingEventResource(TrackingEventService trackingEventService, TrackingEventQueryService trackingEventQueryService) {
        this.trackingEventService = trackingEventService;
        this.trackingEventQueryService = trackingEventQueryService;
    }

    /**
     * {@code POST  /tracking-events} : Create a new trackingEvent.
     *
     * @param trackingEventDTO the trackingEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trackingEventDTO, or with status {@code 400 (Bad Request)} if the trackingEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tracking-events")
    public ResponseEntity<TrackingEventDTO> createTrackingEvent(@Valid @RequestBody TrackingEventDTO trackingEventDTO) throws URISyntaxException {
        log.debug("REST request to save TrackingEvent : {}", trackingEventDTO);
        if (trackingEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new trackingEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrackingEventDTO result = trackingEventService.save(trackingEventDTO);
        return ResponseEntity.created(new URI("/api/tracking-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tracking-events} : Updates an existing trackingEvent.
     *
     * @param trackingEventDTO the trackingEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trackingEventDTO,
     * or with status {@code 400 (Bad Request)} if the trackingEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trackingEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tracking-events")
    public ResponseEntity<TrackingEventDTO> updateTrackingEvent(@Valid @RequestBody TrackingEventDTO trackingEventDTO) throws URISyntaxException {
        log.debug("REST request to update TrackingEvent : {}", trackingEventDTO);
        if (trackingEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TrackingEventDTO result = trackingEventService.save(trackingEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trackingEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tracking-events} : get all the trackingEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trackingEvents in body.
     */
    @GetMapping("/tracking-events")
    public ResponseEntity<List<TrackingEventDTO>> getAllTrackingEvents(TrackingEventCriteria criteria) {
        log.debug("REST request to get TrackingEvents by criteria: {}", criteria);
        List<TrackingEventDTO> entityList = trackingEventQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tracking-events/count} : count all the trackingEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tracking-events/count")
    public ResponseEntity<Long> countTrackingEvents(TrackingEventCriteria criteria) {
        log.debug("REST request to count TrackingEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(trackingEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tracking-events/:id} : get the "id" trackingEvent.
     *
     * @param id the id of the trackingEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trackingEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tracking-events/{id}")
    public ResponseEntity<TrackingEventDTO> getTrackingEvent(@PathVariable Long id) {
        log.debug("REST request to get TrackingEvent : {}", id);
        Optional<TrackingEventDTO> trackingEventDTO = trackingEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trackingEventDTO);
    }

    /**
     * {@code DELETE  /tracking-events/:id} : delete the "id" trackingEvent.
     *
     * @param id the id of the trackingEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tracking-events/{id}")
    public ResponseEntity<Void> deleteTrackingEvent(@PathVariable Long id) {
        log.debug("REST request to delete TrackingEvent : {}", id);

        trackingEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
