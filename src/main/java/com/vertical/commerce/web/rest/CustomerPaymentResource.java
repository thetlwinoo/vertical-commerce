package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;
import com.vertical.commerce.service.dto.CustomerPaymentCriteria;
import com.vertical.commerce.service.CustomerPaymentQueryService;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPayment}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentService customerPaymentService;

    private final CustomerPaymentQueryService customerPaymentQueryService;

    public CustomerPaymentResource(CustomerPaymentService customerPaymentService, CustomerPaymentQueryService customerPaymentQueryService) {
        this.customerPaymentService = customerPaymentService;
        this.customerPaymentQueryService = customerPaymentQueryService;
    }

    /**
     * {@code POST  /customer-payments} : Create a new customerPayment.
     *
     * @param customerPaymentDTO the customerPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentDTO, or with status {@code 400 (Bad Request)} if the customerPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payments")
    public ResponseEntity<CustomerPaymentDTO> createCustomerPayment(@Valid @RequestBody CustomerPaymentDTO customerPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPayment : {}", customerPaymentDTO);
        if (customerPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentDTO result = customerPaymentService.save(customerPaymentDTO);
        return ResponseEntity.created(new URI("/api/customer-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payments} : Updates an existing customerPayment.
     *
     * @param customerPaymentDTO the customerPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payments")
    public ResponseEntity<CustomerPaymentDTO> updateCustomerPayment(@Valid @RequestBody CustomerPaymentDTO customerPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPayment : {}", customerPaymentDTO);
        if (customerPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentDTO result = customerPaymentService.save(customerPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payments} : get all the customerPayments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPayments in body.
     */
    @GetMapping("/customer-payments")
    public ResponseEntity<List<CustomerPaymentDTO>> getAllCustomerPayments(CustomerPaymentCriteria criteria) {
        log.debug("REST request to get CustomerPayments by criteria: {}", criteria);
        List<CustomerPaymentDTO> entityList = customerPaymentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payments/count} : count all the customerPayments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payments/count")
    public ResponseEntity<Long> countCustomerPayments(CustomerPaymentCriteria criteria) {
        log.debug("REST request to count CustomerPayments by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payments/:id} : get the "id" customerPayment.
     *
     * @param id the id of the customerPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payments/{id}")
    public ResponseEntity<CustomerPaymentDTO> getCustomerPayment(@PathVariable Long id) {
        log.debug("REST request to get CustomerPayment : {}", id);
        Optional<CustomerPaymentDTO> customerPaymentDTO = customerPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentDTO);
    }

    /**
     * {@code DELETE  /customer-payments/:id} : delete the "id" customerPayment.
     *
     * @param id the id of the customerPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payments/{id}")
    public ResponseEntity<Void> deleteCustomerPayment(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPayment : {}", id);

        customerPaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
