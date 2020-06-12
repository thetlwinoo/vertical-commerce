package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CreditCardTypeService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CreditCardTypeDTO;
import com.vertical.commerce.service.dto.CreditCardTypeCriteria;
import com.vertical.commerce.service.CreditCardTypeQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CreditCardType}.
 */
@RestController
@RequestMapping("/api")
public class CreditCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(CreditCardTypeResource.class);

    private static final String ENTITY_NAME = "vscommerceCreditCardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCardTypeService creditCardTypeService;

    private final CreditCardTypeQueryService creditCardTypeQueryService;

    public CreditCardTypeResource(CreditCardTypeService creditCardTypeService, CreditCardTypeQueryService creditCardTypeQueryService) {
        this.creditCardTypeService = creditCardTypeService;
        this.creditCardTypeQueryService = creditCardTypeQueryService;
    }

    /**
     * {@code POST  /credit-card-types} : Create a new creditCardType.
     *
     * @param creditCardTypeDTO the creditCardTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCardTypeDTO, or with status {@code 400 (Bad Request)} if the creditCardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-card-types")
    public ResponseEntity<CreditCardTypeDTO> createCreditCardType(@RequestBody CreditCardTypeDTO creditCardTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CreditCardType : {}", creditCardTypeDTO);
        if (creditCardTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCardTypeDTO result = creditCardTypeService.save(creditCardTypeDTO);
        return ResponseEntity.created(new URI("/api/credit-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-card-types} : Updates an existing creditCardType.
     *
     * @param creditCardTypeDTO the creditCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the creditCardTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-card-types")
    public ResponseEntity<CreditCardTypeDTO> updateCreditCardType(@RequestBody CreditCardTypeDTO creditCardTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CreditCardType : {}", creditCardTypeDTO);
        if (creditCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditCardTypeDTO result = creditCardTypeService.save(creditCardTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, creditCardTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /credit-card-types} : get all the creditCardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCardTypes in body.
     */
    @GetMapping("/credit-card-types")
    public ResponseEntity<List<CreditCardTypeDTO>> getAllCreditCardTypes(CreditCardTypeCriteria criteria) {
        log.debug("REST request to get CreditCardTypes by criteria: {}", criteria);
        List<CreditCardTypeDTO> entityList = creditCardTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /credit-card-types/count} : count all the creditCardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/credit-card-types/count")
    public ResponseEntity<Long> countCreditCardTypes(CreditCardTypeCriteria criteria) {
        log.debug("REST request to count CreditCardTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(creditCardTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /credit-card-types/:id} : get the "id" creditCardType.
     *
     * @param id the id of the creditCardTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCardTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-card-types/{id}")
    public ResponseEntity<CreditCardTypeDTO> getCreditCardType(@PathVariable Long id) {
        log.debug("REST request to get CreditCardType : {}", id);
        Optional<CreditCardTypeDTO> creditCardTypeDTO = creditCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCardTypeDTO);
    }

    /**
     * {@code DELETE  /credit-card-types/:id} : delete the "id" creditCardType.
     *
     * @param id the id of the creditCardTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-card-types/{id}")
    public ResponseEntity<Void> deleteCreditCardType(@PathVariable Long id) {
        log.debug("REST request to delete CreditCardType : {}", id);

        creditCardTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
