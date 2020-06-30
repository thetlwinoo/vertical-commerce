package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.AddressesExtendService;
import com.vertical.commerce.service.dto.AddressesDTO;
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
import java.util.List;

/**
 * AddressesExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class AddressesExtendResource {

    private final Logger log = LoggerFactory.getLogger(AddressesExtendResource.class);
    private final AddressesExtendService addressesExtendService;

    private static final String ENTITY_NAME = "zezawarAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AddressesExtendResource(AddressesExtendService addressesExtendService) {
        this.addressesExtendService = addressesExtendService;
    }

    @GetMapping("/addresses-extend/fetch")
    public ResponseEntity<List<AddressesDTO>> getAllAddresses(Principal principal) {
        List<AddressesDTO> entityList = addressesExtendService.fetchAddresses(principal);
        return ResponseEntity.ok().body(entityList);
    }

    @PostMapping("/addresses-extend")
    public ResponseEntity<AddressesDTO> createAddresses(@Valid @RequestBody AddressesDTO addressesDTO,@RequestParam(value="isShipping") Boolean isShipping, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Addresses : {}", addressesDTO);
        if (addressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new addresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressesDTO result = addressesExtendService.crateAddresses(addressesDTO,isShipping, principal);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/addresses-extend")
    public ResponseEntity<AddressesDTO> updateAddresses(@Valid @RequestBody AddressesDTO addressesDTO,@RequestParam(value="isShipping") Boolean isShipping, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Addresses : {}", addressesDTO);
        if (addressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AddressesDTO result = addressesExtendService.updateAddresses(addressesDTO,isShipping, principal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, addressesDTO.getId().toString()))
            .body(result);
    }

    @PostMapping("/addresses-extend/default")
    public ResponseEntity<AddressesDTO> setDefaultAddress(@RequestParam(value="addressId") Long addressId,@RequestParam(value="isShipping") Boolean isShipping, Principal principal) throws URISyntaxException {
       AddressesDTO defaultAddress =  addressesExtendService.setDefaultAddress(addressId,isShipping, principal);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaultAddress.getId().toString()))
            .body(defaultAddress);
    }

    @PostMapping("/addresses-extend/clear")
    public ResponseEntity<AddressesDTO> clearDefaultAddress(Principal principal) throws URISyntaxException {
        addressesExtendService.clearDefaultAddress(principal);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null)).build();
    }
}
