package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentCreditCardExtendedService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedCriteria;
import com.vertical.commerce.service.CustomerPaymentCreditCardExtendedQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPaymentCreditCardExtended}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentCreditCardExtendedResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardExtendedResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPaymentCreditCardExtended";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentCreditCardExtendedService customerPaymentCreditCardExtendedService;

    private final CustomerPaymentCreditCardExtendedQueryService customerPaymentCreditCardExtendedQueryService;

    public CustomerPaymentCreditCardExtendedResource(CustomerPaymentCreditCardExtendedService customerPaymentCreditCardExtendedService, CustomerPaymentCreditCardExtendedQueryService customerPaymentCreditCardExtendedQueryService) {
        this.customerPaymentCreditCardExtendedService = customerPaymentCreditCardExtendedService;
        this.customerPaymentCreditCardExtendedQueryService = customerPaymentCreditCardExtendedQueryService;
    }

    /**
     * {@code POST  /customer-payment-credit-card-extendeds} : Create a new customerPaymentCreditCardExtended.
     *
     * @param customerPaymentCreditCardExtendedDTO the customerPaymentCreditCardExtendedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentCreditCardExtendedDTO, or with status {@code 400 (Bad Request)} if the customerPaymentCreditCardExtended has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-credit-card-extendeds")
    public ResponseEntity<CustomerPaymentCreditCardExtendedDTO> createCustomerPaymentCreditCardExtended(@Valid @RequestBody CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentCreditCardExtended : {}", customerPaymentCreditCardExtendedDTO);
        if (customerPaymentCreditCardExtendedDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentCreditCardExtended cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentCreditCardExtendedDTO result = customerPaymentCreditCardExtendedService.save(customerPaymentCreditCardExtendedDTO);
        return ResponseEntity.created(new URI("/api/customer-payment-credit-card-extendeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-credit-card-extendeds} : Updates an existing customerPaymentCreditCardExtended.
     *
     * @param customerPaymentCreditCardExtendedDTO the customerPaymentCreditCardExtendedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentCreditCardExtendedDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentCreditCardExtendedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentCreditCardExtendedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-credit-card-extendeds")
    public ResponseEntity<CustomerPaymentCreditCardExtendedDTO> updateCustomerPaymentCreditCardExtended(@Valid @RequestBody CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentCreditCardExtended : {}", customerPaymentCreditCardExtendedDTO);
        if (customerPaymentCreditCardExtendedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentCreditCardExtendedDTO result = customerPaymentCreditCardExtendedService.save(customerPaymentCreditCardExtendedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentCreditCardExtendedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-credit-card-extendeds} : get all the customerPaymentCreditCardExtendeds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentCreditCardExtendeds in body.
     */
    @GetMapping("/customer-payment-credit-card-extendeds")
    public ResponseEntity<List<CustomerPaymentCreditCardExtendedDTO>> getAllCustomerPaymentCreditCardExtendeds(CustomerPaymentCreditCardExtendedCriteria criteria) {
        log.debug("REST request to get CustomerPaymentCreditCardExtendeds by criteria: {}", criteria);
        List<CustomerPaymentCreditCardExtendedDTO> entityList = customerPaymentCreditCardExtendedQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payment-credit-card-extendeds/count} : count all the customerPaymentCreditCardExtendeds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payment-credit-card-extendeds/count")
    public ResponseEntity<Long> countCustomerPaymentCreditCardExtendeds(CustomerPaymentCreditCardExtendedCriteria criteria) {
        log.debug("REST request to count CustomerPaymentCreditCardExtendeds by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentCreditCardExtendedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payment-credit-card-extendeds/:id} : get the "id" customerPaymentCreditCardExtended.
     *
     * @param id the id of the customerPaymentCreditCardExtendedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentCreditCardExtendedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-credit-card-extendeds/{id}")
    public ResponseEntity<CustomerPaymentCreditCardExtendedDTO> getCustomerPaymentCreditCardExtended(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentCreditCardExtended : {}", id);
        Optional<CustomerPaymentCreditCardExtendedDTO> customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentCreditCardExtendedDTO);
    }

    /**
     * {@code DELETE  /customer-payment-credit-card-extendeds/:id} : delete the "id" customerPaymentCreditCardExtended.
     *
     * @param id the id of the customerPaymentCreditCardExtendedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-credit-card-extendeds/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentCreditCardExtended(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentCreditCardExtended : {}", id);

        customerPaymentCreditCardExtendedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
