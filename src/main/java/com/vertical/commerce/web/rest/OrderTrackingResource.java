package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.OrderTrackingService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.OrderTrackingDTO;
import com.vertical.commerce.service.dto.OrderTrackingCriteria;
import com.vertical.commerce.service.OrderTrackingQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.OrderTracking}.
 */
@RestController
@RequestMapping("/api")
public class OrderTrackingResource {

    private final Logger log = LoggerFactory.getLogger(OrderTrackingResource.class);

    private static final String ENTITY_NAME = "vscommerceOrderTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderTrackingService orderTrackingService;

    private final OrderTrackingQueryService orderTrackingQueryService;

    public OrderTrackingResource(OrderTrackingService orderTrackingService, OrderTrackingQueryService orderTrackingQueryService) {
        this.orderTrackingService = orderTrackingService;
        this.orderTrackingQueryService = orderTrackingQueryService;
    }

    /**
     * {@code POST  /order-trackings} : Create a new orderTracking.
     *
     * @param orderTrackingDTO the orderTrackingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderTrackingDTO, or with status {@code 400 (Bad Request)} if the orderTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-trackings")
    public ResponseEntity<OrderTrackingDTO> createOrderTracking(@Valid @RequestBody OrderTrackingDTO orderTrackingDTO) throws URISyntaxException {
        log.debug("REST request to save OrderTracking : {}", orderTrackingDTO);
        if (orderTrackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderTrackingDTO result = orderTrackingService.save(orderTrackingDTO);
        return ResponseEntity.created(new URI("/api/order-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-trackings} : Updates an existing orderTracking.
     *
     * @param orderTrackingDTO the orderTrackingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderTrackingDTO,
     * or with status {@code 400 (Bad Request)} if the orderTrackingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderTrackingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-trackings")
    public ResponseEntity<OrderTrackingDTO> updateOrderTracking(@Valid @RequestBody OrderTrackingDTO orderTrackingDTO) throws URISyntaxException {
        log.debug("REST request to update OrderTracking : {}", orderTrackingDTO);
        if (orderTrackingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderTrackingDTO result = orderTrackingService.save(orderTrackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderTrackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-trackings} : get all the orderTrackings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderTrackings in body.
     */
    @GetMapping("/order-trackings")
    public ResponseEntity<List<OrderTrackingDTO>> getAllOrderTrackings(OrderTrackingCriteria criteria) {
        log.debug("REST request to get OrderTrackings by criteria: {}", criteria);
        List<OrderTrackingDTO> entityList = orderTrackingQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /order-trackings/count} : count all the orderTrackings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-trackings/count")
    public ResponseEntity<Long> countOrderTrackings(OrderTrackingCriteria criteria) {
        log.debug("REST request to count OrderTrackings by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderTrackingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-trackings/:id} : get the "id" orderTracking.
     *
     * @param id the id of the orderTrackingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderTrackingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-trackings/{id}")
    public ResponseEntity<OrderTrackingDTO> getOrderTracking(@PathVariable Long id) {
        log.debug("REST request to get OrderTracking : {}", id);
        Optional<OrderTrackingDTO> orderTrackingDTO = orderTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderTrackingDTO);
    }

    /**
     * {@code DELETE  /order-trackings/:id} : delete the "id" orderTracking.
     *
     * @param id the id of the orderTrackingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-trackings/{id}")
    public ResponseEntity<Void> deleteOrderTracking(@PathVariable Long id) {
        log.debug("REST request to delete OrderTracking : {}", id);

        orderTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
