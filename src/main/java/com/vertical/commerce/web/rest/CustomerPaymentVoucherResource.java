package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomerPaymentVoucherService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherCriteria;
import com.vertical.commerce.service.CustomerPaymentVoucherQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.CustomerPaymentVoucher}.
 */
@RestController
@RequestMapping("/api")
public class CustomerPaymentVoucherResource {

    private final Logger log = LoggerFactory.getLogger(CustomerPaymentVoucherResource.class);

    private static final String ENTITY_NAME = "vscommerceCustomerPaymentVoucher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerPaymentVoucherService customerPaymentVoucherService;

    private final CustomerPaymentVoucherQueryService customerPaymentVoucherQueryService;

    public CustomerPaymentVoucherResource(CustomerPaymentVoucherService customerPaymentVoucherService, CustomerPaymentVoucherQueryService customerPaymentVoucherQueryService) {
        this.customerPaymentVoucherService = customerPaymentVoucherService;
        this.customerPaymentVoucherQueryService = customerPaymentVoucherQueryService;
    }

    /**
     * {@code POST  /customer-payment-vouchers} : Create a new customerPaymentVoucher.
     *
     * @param customerPaymentVoucherDTO the customerPaymentVoucherDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerPaymentVoucherDTO, or with status {@code 400 (Bad Request)} if the customerPaymentVoucher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-payment-vouchers")
    public ResponseEntity<CustomerPaymentVoucherDTO> createCustomerPaymentVoucher(@Valid @RequestBody CustomerPaymentVoucherDTO customerPaymentVoucherDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerPaymentVoucher : {}", customerPaymentVoucherDTO);
        if (customerPaymentVoucherDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerPaymentVoucher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerPaymentVoucherDTO result = customerPaymentVoucherService.save(customerPaymentVoucherDTO);
        return ResponseEntity.created(new URI("/api/customer-payment-vouchers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-payment-vouchers} : Updates an existing customerPaymentVoucher.
     *
     * @param customerPaymentVoucherDTO the customerPaymentVoucherDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerPaymentVoucherDTO,
     * or with status {@code 400 (Bad Request)} if the customerPaymentVoucherDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerPaymentVoucherDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-payment-vouchers")
    public ResponseEntity<CustomerPaymentVoucherDTO> updateCustomerPaymentVoucher(@Valid @RequestBody CustomerPaymentVoucherDTO customerPaymentVoucherDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerPaymentVoucher : {}", customerPaymentVoucherDTO);
        if (customerPaymentVoucherDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomerPaymentVoucherDTO result = customerPaymentVoucherService.save(customerPaymentVoucherDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerPaymentVoucherDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /customer-payment-vouchers} : get all the customerPaymentVouchers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerPaymentVouchers in body.
     */
    @GetMapping("/customer-payment-vouchers")
    public ResponseEntity<List<CustomerPaymentVoucherDTO>> getAllCustomerPaymentVouchers(CustomerPaymentVoucherCriteria criteria) {
        log.debug("REST request to get CustomerPaymentVouchers by criteria: {}", criteria);
        List<CustomerPaymentVoucherDTO> entityList = customerPaymentVoucherQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /customer-payment-vouchers/count} : count all the customerPaymentVouchers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/customer-payment-vouchers/count")
    public ResponseEntity<Long> countCustomerPaymentVouchers(CustomerPaymentVoucherCriteria criteria) {
        log.debug("REST request to count CustomerPaymentVouchers by criteria: {}", criteria);
        return ResponseEntity.ok().body(customerPaymentVoucherQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /customer-payment-vouchers/:id} : get the "id" customerPaymentVoucher.
     *
     * @param id the id of the customerPaymentVoucherDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerPaymentVoucherDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-payment-vouchers/{id}")
    public ResponseEntity<CustomerPaymentVoucherDTO> getCustomerPaymentVoucher(@PathVariable Long id) {
        log.debug("REST request to get CustomerPaymentVoucher : {}", id);
        Optional<CustomerPaymentVoucherDTO> customerPaymentVoucherDTO = customerPaymentVoucherService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerPaymentVoucherDTO);
    }

    /**
     * {@code DELETE  /customer-payment-vouchers/:id} : delete the "id" customerPaymentVoucher.
     *
     * @param id the id of the customerPaymentVoucherDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-payment-vouchers/{id}")
    public ResponseEntity<Void> deleteCustomerPaymentVoucher(@PathVariable Long id) {
        log.debug("REST request to delete CustomerPaymentVoucher : {}", id);

        customerPaymentVoucherService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
