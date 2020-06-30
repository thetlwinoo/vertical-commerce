package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.LogisticsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.LogisticsDTO;
import com.vertical.commerce.service.dto.LogisticsCriteria;
import com.vertical.commerce.service.LogisticsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.Logistics}.
 */
@RestController
@RequestMapping("/api")
public class LogisticsResource {

    private final Logger log = LoggerFactory.getLogger(LogisticsResource.class);

    private static final String ENTITY_NAME = "vscommerceLogistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogisticsService logisticsService;

    private final LogisticsQueryService logisticsQueryService;

    public LogisticsResource(LogisticsService logisticsService, LogisticsQueryService logisticsQueryService) {
        this.logisticsService = logisticsService;
        this.logisticsQueryService = logisticsQueryService;
    }

    /**
     * {@code POST  /logistics} : Create a new logistics.
     *
     * @param logisticsDTO the logisticsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logisticsDTO, or with status {@code 400 (Bad Request)} if the logistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/logistics")
    public ResponseEntity<LogisticsDTO> createLogistics(@Valid @RequestBody LogisticsDTO logisticsDTO) throws URISyntaxException {
        log.debug("REST request to save Logistics : {}", logisticsDTO);
        if (logisticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new logistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogisticsDTO result = logisticsService.save(logisticsDTO);
        return ResponseEntity.created(new URI("/api/logistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /logistics} : Updates an existing logistics.
     *
     * @param logisticsDTO the logisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logisticsDTO,
     * or with status {@code 400 (Bad Request)} if the logisticsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/logistics")
    public ResponseEntity<LogisticsDTO> updateLogistics(@Valid @RequestBody LogisticsDTO logisticsDTO) throws URISyntaxException {
        log.debug("REST request to update Logistics : {}", logisticsDTO);
        if (logisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogisticsDTO result = logisticsService.save(logisticsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logisticsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /logistics} : get all the logistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logistics in body.
     */
    @GetMapping("/logistics")
    public ResponseEntity<List<LogisticsDTO>> getAllLogistics(LogisticsCriteria criteria) {
        log.debug("REST request to get Logistics by criteria: {}", criteria);
        List<LogisticsDTO> entityList = logisticsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /logistics/count} : count all the logistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/logistics/count")
    public ResponseEntity<Long> countLogistics(LogisticsCriteria criteria) {
        log.debug("REST request to count Logistics by criteria: {}", criteria);
        return ResponseEntity.ok().body(logisticsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /logistics/:id} : get the "id" logistics.
     *
     * @param id the id of the logisticsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logisticsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/logistics/{id}")
    public ResponseEntity<LogisticsDTO> getLogistics(@PathVariable Long id) {
        log.debug("REST request to get Logistics : {}", id);
        Optional<LogisticsDTO> logisticsDTO = logisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logisticsDTO);
    }

    /**
     * {@code DELETE  /logistics/:id} : delete the "id" logistics.
     *
     * @param id the id of the logisticsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/logistics/{id}")
    public ResponseEntity<Void> deleteLogistics(@PathVariable Long id) {
        log.debug("REST request to delete Logistics : {}", id);

        logisticsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
