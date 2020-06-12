package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentBankTransferService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferCriteria;
import com.vertical.commerce.service.CustomerPaymentBankTransferQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPaymentBankTransfer}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentBankTransferResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentBankTransferResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPaymentBankTransfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentBankTransferService customerPaymentBankTransferService;

    private final CustomerPaymentBankTransferQueryService customerPaymentBankTransferQueryService;

    public CustomerPaymentBankTransferResource(CustomerPaymentBankTransferService customerPaymentBankTransferService, CustomerPaymentBankTransferQueryService customerPaymentBankTransferQueryService) {
        this.customerPaymentBankTransferService = customerPaymentBankTransferService;
        this.customerPaymentBankTransferQueryService = customerPaymentBankTransferQueryService;
    }

    /**
     * {@code POST  /customer-payment-bank-transfers} : Create a new customerPaymentBankTransfer.
     *
     * @param customerPaymentBankTransferDTO the customerPaymentBankTransferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentBankTransferDTO, or with status {@code 400 (Bad Request)} if the customerPaymentBankTransfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-bank-transfers")
    public ResponseEntity<CustomerPaymentBankTransferDTO> createCustomerPaymentBankTransfer(@Valid @RequestBody CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentBankTransfer : {}", customerPaymentBankTransferDTO);
        if (customerPaymentBankTransferDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentBankTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentBankTransferDTO result = customerPaymentBankTransferService.save(customerPaymentBankTransferDTO);
        return ResponseEntity.created(new URI("/api/customer-payment-bank-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-bank-transfers} : Updates an existing customerPaymentBankTransfer.
     *
     * @param customerPaymentBankTransferDTO the customerPaymentBankTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentBankTransferDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentBankTransferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentBankTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-bank-transfers")
    public ResponseEntity<CustomerPaymentBankTransferDTO> updateCustomerPaymentBankTransfer(@Valid @RequestBody CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentBankTransfer : {}", customerPaymentBankTransferDTO);
        if (customerPaymentBankTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentBankTransferDTO result = customerPaymentBankTransferService.save(customerPaymentBankTransferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentBankTransferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-bank-transfers} : get all the customerPaymentBankTransfers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentBankTransfers in body.
     */
    @GetMapping("/customer-payment-bank-transfers")
    public ResponseEntity<List<CustomerPaymentBankTransferDTO>> getAllCustomerPaymentBankTransfers(CustomerPaymentBankTransferCriteria criteria) {
        log.debug("REST request to get CustomerPaymentBankTransfers by criteria: {}", criteria);
        List<CustomerPaymentBankTransferDTO> entityList = customerPaymentBankTransferQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payment-bank-transfers/count} : count all the customerPaymentBankTransfers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payment-bank-transfers/count")
    public ResponseEntity<Long> countCustomerPaymentBankTransfers(CustomerPaymentBankTransferCriteria criteria) {
        log.debug("REST request to count CustomerPaymentBankTransfers by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentBankTransferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payment-bank-transfers/:id} : get the "id" customerPaymentBankTransfer.
     *
     * @param id the id of the customerPaymentBankTransferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentBankTransferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-bank-transfers/{id}")
    public ResponseEntity<CustomerPaymentBankTransferDTO> getCustomerPaymentBankTransfer(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentBankTransfer : {}", id);
        Optional<CustomerPaymentBankTransferDTO> customerPaymentBankTransferDTO = customerPaymentBankTransferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentBankTransferDTO);
    }

    /**
     * {@code DELETE  /customer-payment-bank-transfers/:id} : delete the "id" customerPaymentBankTransfer.
     *
     * @param id the id of the customerPaymentBankTransferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-bank-transfers/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentBankTransfer(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentBankTransfer : {}", id);

        customerPaymentBankTransferService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
