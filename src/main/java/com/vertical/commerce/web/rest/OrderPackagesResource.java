package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.OrderPackagesService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.dto.OrderPackagesCriteria;
import com.vertical.commerce.service.OrderPackagesQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.OrderPackages}.
 */
@RestController
@RequestMapping("/api")
public class OrderPackagesResource {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesResource.class);

    private static final String ENTITY_NAME = "vscommerceOrderPackages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPackagesService orderPackagesService;

    private final OrderPackagesQueryService orderPackagesQueryService;

    public OrderPackagesResource(OrderPackagesService orderPackagesService, OrderPackagesQueryService orderPackagesQueryService) {
        this.orderPackagesService = orderPackagesService;
        this.orderPackagesQueryService = orderPackagesQueryService;
    }

    /**
     * {@code POST  /order-packages} : Create a new orderPackages.
     *
     * @param orderPackagesDTO the orderPackagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPackagesDTO, or with status {@code 400 (Bad Request)} if the orderPackages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-packages")
    public ResponseEntity<OrderPackagesDTO> createOrderPackages(@Valid @RequestBody OrderPackagesDTO orderPackagesDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPackages : {}", orderPackagesDTO);
        if (orderPackagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPackages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPackagesDTO result = orderPackagesService.save(orderPackagesDTO);
        return ResponseEntity.created(new URI("/api/order-packages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-packages} : Updates an existing orderPackages.
     *
     * @param orderPackagesDTO the orderPackagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPackagesDTO,
     * or with status {@code 400 (Bad Request)} if the orderPackagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPackagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-packages")
    public ResponseEntity<OrderPackagesDTO> updateOrderPackages(@Valid @RequestBody OrderPackagesDTO orderPackagesDTO) throws URISyntaxException {
        log.debug("REST request to update OrderPackages : {}", orderPackagesDTO);
        if (orderPackagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderPackagesDTO result = orderPackagesService.save(orderPackagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPackagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-packages} : get all the orderPackages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPackages in body.
     */
    @GetMapping("/order-packages")
    public ResponseEntity<List<OrderPackagesDTO>> getAllOrderPackages(OrderPackagesCriteria criteria) {
        log.debug("REST request to get OrderPackages by criteria: {}", criteria);
        List<OrderPackagesDTO> entityList = orderPackagesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /order-packages/count} : count all the orderPackages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-packages/count")
    public ResponseEntity<Long> countOrderPackages(OrderPackagesCriteria criteria) {
        log.debug("REST request to count OrderPackages by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderPackagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-packages/:id} : get the "id" orderPackages.
     *
     * @param id the id of the orderPackagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPackagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-packages/{id}")
    public ResponseEntity<OrderPackagesDTO> getOrderPackages(@PathVariable Long id) {
        log.debug("REST request to get OrderPackages : {}", id);
        Optional<OrderPackagesDTO> orderPackagesDTO = orderPackagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPackagesDTO);
    }

    /**
     * {@code DELETE  /order-packages/:id} : delete the "id" orderPackages.
     *
     * @param id the id of the orderPackagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-packages/{id}")
    public ResponseEntity<Void> deleteOrderPackages(@PathVariable Long id) {
        log.debug("REST request to delete OrderPackages : {}", id);

        orderPackagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
