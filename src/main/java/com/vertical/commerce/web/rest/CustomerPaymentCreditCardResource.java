package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentCreditCardService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardCriteria;
import com.vertical.commerce.service.CustomerPaymentCreditCardQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPaymentCreditCard}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentCreditCardResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentCreditCardResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPaymentCreditCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentCreditCardService customerPaymentCreditCardService;

    private final CustomerPaymentCreditCardQueryService customerPaymentCreditCardQueryService;

    public CustomerPaymentCreditCardResource(CustomerPaymentCreditCardService customerPaymentCreditCardService, CustomerPaymentCreditCardQueryService customerPaymentCreditCardQueryService) {
        this.customerPaymentCreditCardService = customerPaymentCreditCardService;
        this.customerPaymentCreditCardQueryService = customerPaymentCreditCardQueryService;
    }

    /**
     * {@code POST  /customer-payment-credit-cards} : Create a new customerPaymentCreditCard.
     *
     * @param customerPaymentCreditCardDTO the customerPaymentCreditCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentCreditCardDTO, or with status {@code 400 (Bad Request)} if the customerPaymentCreditCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-credit-cards")
    public ResponseEntity<CustomerPaymentCreditCardDTO> createCustomerPaymentCreditCard(@Valid @RequestBody CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentCreditCard : {}", customerPaymentCreditCardDTO);
        if (customerPaymentCreditCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentCreditCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentCreditCardDTO result = customerPaymentCreditCardService.save(customerPaymentCreditCardDTO);
        return ResponseEntity.created(new URI("/api/customer-payment-credit-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-credit-cards} : Updates an existing customerPaymentCreditCard.
     *
     * @param customerPaymentCreditCardDTO the customerPaymentCreditCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentCreditCardDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentCreditCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentCreditCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-credit-cards")
    public ResponseEntity<CustomerPaymentCreditCardDTO> updateCustomerPaymentCreditCard(@Valid @RequestBody CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentCreditCard : {}", customerPaymentCreditCardDTO);
        if (customerPaymentCreditCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentCreditCardDTO result = customerPaymentCreditCardService.save(customerPaymentCreditCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentCreditCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-credit-cards} : get all the customerPaymentCreditCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentCreditCards in body.
     */
    @GetMapping("/customer-payment-credit-cards")
    public ResponseEntity<List<CustomerPaymentCreditCardDTO>> getAllCustomerPaymentCreditCards(CustomerPaymentCreditCardCriteria criteria) {
        log.debug("REST request to get CustomerPaymentCreditCards by criteria: {}", criteria);
        List<CustomerPaymentCreditCardDTO> entityList = customerPaymentCreditCardQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payment-credit-cards/count} : count all the customerPaymentCreditCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payment-credit-cards/count")
    public ResponseEntity<Long> countCustomerPaymentCreditCards(CustomerPaymentCreditCardCriteria criteria) {
        log.debug("REST request to count CustomerPaymentCreditCards by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentCreditCardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payment-credit-cards/:id} : get the "id" customerPaymentCreditCard.
     *
     * @param id the id of the customerPaymentCreditCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentCreditCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-credit-cards/{id}")
    public ResponseEntity<CustomerPaymentCreditCardDTO> getCustomerPaymentCreditCard(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentCreditCard : {}", id);
        Optional<CustomerPaymentCreditCardDTO> customerPaymentCreditCardDTO = customerPaymentCreditCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentCreditCardDTO);
    }

    /**
     * {@code DELETE  /customer-payment-credit-cards/:id} : delete the "id" customerPaymentCreditCard.
     *
     * @param id the id of the customerPaymentCreditCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-credit-cards/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentCreditCard(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentCreditCard : {}", id);

        customerPaymentCreditCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
