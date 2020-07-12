package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.CustomersExtendService;
import com.vertical.commerce.service.dto.CustomersDTO;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * CustomersExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class CustomersExtendResource {

    private final Logger log = LoggerFactory.getLogger(CustomersExtendResource.class);
    private static final String ENTITY_NAME = "vscommerceCustomersExtend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersExtendService customersExtendService;

    public CustomersExtendResource(CustomersExtendService customersExtendService) {
        this.customersExtendService = customersExtendService;
    }

    @PostMapping("/customers-extend/create-account")
    public ResponseEntity<CustomersDTO> createCustomerAccount(Principal principal) throws URISyntaxException {
        log.debug("REST request to create Customers Account: {}", principal);
        if (principal.getName() == null) {
            throw new BadRequestAlertException("Principal Account cannot be null", ENTITY_NAME, "null");
        }
        CustomersDTO customersDTO = customersExtendService.createCustomerAccount(principal);
        return ResponseEntity.created(new URI("/api/customers-extend/" + customersDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, customersDTO.getId().toString()))
            .body(customersDTO);
    }

}
