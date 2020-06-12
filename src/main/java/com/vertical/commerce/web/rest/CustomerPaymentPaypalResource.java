package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentPaypalService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalCriteria;
import com.vertical.commerce.service.CustomerPaymentPaypalQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPaymentPaypal}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentPaypalResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentPaypalResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPaymentPaypal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentPaypalService customerPaymentPaypalService;

    private final CustomerPaymentPaypalQueryService customerPaymentPaypalQueryService;

    public CustomerPaymentPaypalResource(CustomerPaymentPaypalService customerPaymentPaypalService, CustomerPaymentPaypalQueryService customerPaymentPaypalQueryService) {
        this.customerPaymentPaypalService = customerPaymentPaypalService;
        this.customerPaymentPaypalQueryService = customerPaymentPaypalQueryService;
    }

    /**
     * {@code POST  /customer-payment-paypals} : Create a new customerPaymentPaypal.
     *
     * @param customerPaymentPaypalDTO the customerPaymentPaypalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentPaypalDTO, or with status {@code 400 (Bad Request)} if the customerPaymentPaypal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-paypals")
    public ResponseEntity<CustomerPaymentPaypalDTO> createCustomerPaymentPaypal(@Valid @RequestBody CustomerPaymentPaypalDTO customerPaymentPaypalDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentPaypal : {}", customerPaymentPaypalDTO);
        if (customerPaymentPaypalDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentPaypal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentPaypalDTO result = customerPaymentPaypalService.save(customerPaymentPaypalDTO);
        return ResponseEntity.created(new URI("/api/customer-payment-paypals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-paypals} : Updates an existing customerPaymentPaypal.
     *
     * @param customerPaymentPaypalDTO the customerPaymentPaypalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentPaypalDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentPaypalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentPaypalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-paypals")
    public ResponseEntity<CustomerPaymentPaypalDTO> updateCustomerPaymentPaypal(@Valid @RequestBody CustomerPaymentPaypalDTO customerPaymentPaypalDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentPaypal : {}", customerPaymentPaypalDTO);
        if (customerPaymentPaypalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentPaypalDTO result = customerPaymentPaypalService.save(customerPaymentPaypalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentPaypalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-paypals} : get all the customerPaymentPaypals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentPaypals in body.
     */
    @GetMapping("/customer-payment-paypals")
    public ResponseEntity<List<CustomerPaymentPaypalDTO>> getAllCustomerPaymentPaypals(CustomerPaymentPaypalCriteria criteria) {
        log.debug("REST request to get CustomerPaymentPaypals by criteria: {}", criteria);
        List<CustomerPaymentPaypalDTO> entityList = customerPaymentPaypalQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payment-paypals/count} : count all the customerPaymentPaypals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payment-paypals/count")
    public ResponseEntity<Long> countCustomerPaymentPaypals(CustomerPaymentPaypalCriteria criteria) {
        log.debug("REST request to count CustomerPaymentPaypals by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentPaypalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payment-paypals/:id} : get the "id" customerPaymentPaypal.
     *
     * @param id the id of the customerPaymentPaypalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentPaypalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-paypals/{id}")
    public ResponseEntity<CustomerPaymentPaypalDTO> getCustomerPaymentPaypal(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentPaypal : {}", id);
        Optional<CustomerPaymentPaypalDTO> customerPaymentPaypalDTO = customerPaymentPaypalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentPaypalDTO);
    }

    /**
     * {@code DELETE  /customer-payment-paypals/:id} : delete the "id" customerPaymentPaypal.
     *
     * @param id the id of the customerPaymentPaypalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-paypals/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentPaypal(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentPaypal : {}", id);

        customerPaymentPaypalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
