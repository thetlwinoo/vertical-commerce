package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CitiesLocalizeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CitiesLocalizeDTO;
import com.vertical.commerce.service.dto.CitiesLocalizeCriteria;
import com.vertical.commerce.service.CitiesLocalizeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CitiesLocalize}.
 */
@RestController
@RequestMapping("/api")
public class CitiesLocalizeResource {

    private final Logger log = LoggerFactory.getLogger(CitiesLocalizeResource.class);

    private static final String ENTITY_NAME = "vscommerceCitiesLocalize";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitiesLocalizeService citiesLocalizeService;

    private final CitiesLocalizeQueryService citiesLocalizeQueryService;

    public CitiesLocalizeResource(CitiesLocalizeService citiesLocalizeService, CitiesLocalizeQueryService citiesLocalizeQueryService) {
        this.citiesLocalizeService = citiesLocalizeService;
        this.citiesLocalizeQueryService = citiesLocalizeQueryService;
    }

    /**
     * {@code POST  /cities-localizes} : Create a new citiesLocalize.
     *
     * @param citiesLocalizeDTO the citiesLocalizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citiesLocalizeDTO, or with status {@code 400 (Bad Request)} if the citiesLocalize has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities-localizes")
    public ResponseEntity<CitiesLocalizeDTO> createCitiesLocalize(@Valid @RequestBody CitiesLocalizeDTO citiesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to save CitiesLocalize : {}", citiesLocalizeDTO);
        if (citiesLocalizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new citiesLocalize cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitiesLocalizeDTO result = citiesLocalizeService.save(citiesLocalizeDTO);
        return ResponseEntity.created(new URI("/api/cities-localizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities-localizes} : Updates an existing citiesLocalize.
     *
     * @param citiesLocalizeDTO the citiesLocalizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citiesLocalizeDTO,
     * or with status {@code 400 (Bad Request)} if the citiesLocalizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citiesLocalizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities-localizes")
    public ResponseEntity<CitiesLocalizeDTO> updateCitiesLocalize(@Valid @RequestBody CitiesLocalizeDTO citiesLocalizeDTO) throws URISyntaxException {
        log.debug("REST request to update CitiesLocalize : {}", citiesLocalizeDTO);
        if (citiesLocalizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CitiesLocalizeDTO result = citiesLocalizeService.save(citiesLocalizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citiesLocalizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cities-localizes} : get all the citiesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citiesLocalizes in body.
     */
    @GetMapping("/cities-localizes")
    public ResponseEntity<List<CitiesLocalizeDTO>> getAllCitiesLocalizes(CitiesLocalizeCriteria criteria) {
        log.debug("REST request to get CitiesLocalizes by criteria: {}", criteria);
        List<CitiesLocalizeDTO> entityList = citiesLocalizeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cities-localizes/count} : count all the citiesLocalizes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cities-localizes/count")
    public ResponseEntity<Long> countCitiesLocalizes(CitiesLocalizeCriteria criteria) {
        log.debug("REST request to count CitiesLocalizes by criteria: {}", criteria);
        return ResponseEntity.ok().body(citiesLocalizeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cities-localizes/:id} : get the "id" citiesLocalize.
     *
     * @param id the id of the citiesLocalizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citiesLocalizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities-localizes/{id}")
    public ResponseEntity<CitiesLocalizeDTO> getCitiesLocalize(@PathVariable Long id) {
        log.debug("REST request to get CitiesLocalize : {}", id);
        Optional<CitiesLocalizeDTO> citiesLocalizeDTO = citiesLocalizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citiesLocalizeDTO);
    }

    /**
     * {@code DELETE  /cities-localizes/:id} : delete the "id" citiesLocalize.
     *
     * @param id the id of the citiesLocalizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities-localizes/{id}")
    public ResponseEntity<Void> deleteCitiesLocalize(@PathVariable Long id) {
        log.debug("REST request to delete CitiesLocalize : {}", id);

        citiesLocalizeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
