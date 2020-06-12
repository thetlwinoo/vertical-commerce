package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.BankAccountsService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.BankAccountsDTO;
import com.vertical.commerce.service.dto.BankAccountsCriteria;
import com.vertical.commerce.service.BankAccountsQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.BankAccounts}.
 */
@RestController
@RequestMapping("/api")
public class BankAccountsResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountsResource.class);

    private static final String ENTITY_NAME = "vscommerceBankAccounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankAccountsService bankAccountsService;

    private final BankAccountsQueryService bankAccountsQueryService;

    public BankAccountsResource(BankAccountsService bankAccountsService, BankAccountsQueryService bankAccountsQueryService) {
        this.bankAccountsService = bankAccountsService;
        this.bankAccountsQueryService = bankAccountsQueryService;
    }

    /**
     * {@code POST  /bank-accounts} : Create a new bankAccounts.
     *
     * @param bankAccountsDTO the bankAccountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankAccountsDTO, or with status {@code 400 (Bad Request)} if the bankAccounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccountsDTO> createBankAccounts(@Valid @RequestBody BankAccountsDTO bankAccountsDTO) throws URISyntaxException {
        log.debug("REST request to save BankAccounts : {}", bankAccountsDTO);
        if (bankAccountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankAccounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountsDTO result = bankAccountsService.save(bankAccountsDTO);
        return ResponseEntity.created(new URI("/api/bank-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-accounts} : Updates an existing bankAccounts.
     *
     * @param bankAccountsDTO the bankAccountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankAccountsDTO,
     * or with status {@code 400 (Bad Request)} if the bankAccountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankAccountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-accounts")
    public ResponseEntity<BankAccountsDTO> updateBankAccounts(@Valid @RequestBody BankAccountsDTO bankAccountsDTO) throws URISyntaxException {
        log.debug("REST request to update BankAccounts : {}", bankAccountsDTO);
        if (bankAccountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankAccountsDTO result = bankAccountsService.save(bankAccountsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankAccountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bank-accounts} : get all the bankAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankAccounts in body.
     */
    @GetMapping("/bank-accounts")
    public ResponseEntity<List<BankAccountsDTO>> getAllBankAccounts(BankAccountsCriteria criteria) {
        log.debug("REST request to get BankAccounts by criteria: {}", criteria);
        List<BankAccountsDTO> entityList = bankAccountsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bank-accounts/count} : count all the bankAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bank-accounts/count")
    public ResponseEntity<Long> countBankAccounts(BankAccountsCriteria criteria) {
        log.debug("REST request to count BankAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankAccountsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bank-accounts/:id} : get the "id" bankAccounts.
     *
     * @param id the id of the bankAccountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankAccountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-accounts/{id}")
    public ResponseEntity<BankAccountsDTO> getBankAccounts(@PathVariable Long id) {
        log.debug("REST request to get BankAccounts : {}", id);
        Optional<BankAccountsDTO> bankAccountsDTO = bankAccountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankAccountsDTO);
    }

    /**
     * {@code DELETE  /bank-accounts/:id} : delete the "id" bankAccounts.
     *
     * @param id the id of the bankAccountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-accounts/{id}")
    public ResponseEntity<Void> deleteBankAccounts(@PathVariable Long id) {
        log.debug("REST request to delete BankAccounts : {}", id);

        bankAccountsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
