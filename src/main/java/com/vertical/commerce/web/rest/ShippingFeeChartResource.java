package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.ShippingFeeChartService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;
import com.vertical.commerce.service.dto.ShippingFeeChartCriteria;
import com.vertical.commerce.service.ShippingFeeChartQueryService;

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
 * REST controller for managing {@link com.vertical.commerce.domain.ShippingFeeChart}.
 */
@RestController
@RequestMapping("/api")
public class ShippingFeeChartResource {

    private final Logger log = LoggerFactory.getLogger(ShippingFeeChartResource.class);

    private static final String ENTITY_NAME = "vscommerceShippingFeeChart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingFeeChartService shippingFeeChartService;

    private final ShippingFeeChartQueryService shippingFeeChartQueryService;

    public ShippingFeeChartResource(ShippingFeeChartService shippingFeeChartService, ShippingFeeChartQueryService shippingFeeChartQueryService) {
        this.shippingFeeChartService = shippingFeeChartService;
        this.shippingFeeChartQueryService = shippingFeeChartQueryService;
    }

    /**
     * {@code POST  /shipping-fee-charts} : Create a new shippingFeeChart.
     *
     * @param shippingFeeChartDTO the shippingFeeChartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippingFeeChartDTO, or with status {@code 400 (Bad Request)} if the shippingFeeChart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipping-fee-charts")
    public ResponseEntity<ShippingFeeChartDTO> createShippingFeeChart(@Valid @RequestBody ShippingFeeChartDTO shippingFeeChartDTO) throws URISyntaxException {
        log.debug("REST request to save ShippingFeeChart : {}", shippingFeeChartDTO);
        if (shippingFeeChartDTO.getId() != null) {
            throw new BadRequestAlertException("A new shippingFeeChart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingFeeChartDTO result = shippingFeeChartService.save(shippingFeeChartDTO);
        return ResponseEntity.created(new URI("/api/shipping-fee-charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipping-fee-charts} : Updates an existing shippingFeeChart.
     *
     * @param shippingFeeChartDTO the shippingFeeChartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingFeeChartDTO,
     * or with status {@code 400 (Bad Request)} if the shippingFeeChartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippingFeeChartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipping-fee-charts")
    public ResponseEntity<ShippingFeeChartDTO> updateShippingFeeChart(@Valid @RequestBody ShippingFeeChartDTO shippingFeeChartDTO) throws URISyntaxException {
        log.debug("REST request to update ShippingFeeChart : {}", shippingFeeChartDTO);
        if (shippingFeeChartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShippingFeeChartDTO result = shippingFeeChartService.save(shippingFeeChartDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shippingFeeChartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shipping-fee-charts} : get all the shippingFeeCharts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippingFeeCharts in body.
     */
    @GetMapping("/shipping-fee-charts")
    public ResponseEntity<List<ShippingFeeChartDTO>> getAllShippingFeeCharts(ShippingFeeChartCriteria criteria) {
        log.debug("REST request to get ShippingFeeCharts by criteria: {}", criteria);
        List<ShippingFeeChartDTO> entityList = shippingFeeChartQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /shipping-fee-charts/count} : count all the shippingFeeCharts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/shipping-fee-charts/count")
    public ResponseEntity<Long> countShippingFeeCharts(ShippingFeeChartCriteria criteria) {
        log.debug("REST request to count ShippingFeeCharts by criteria: {}", criteria);
        return ResponseEntity.ok().body(shippingFeeChartQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shipping-fee-charts/:id} : get the "id" shippingFeeChart.
     *
     * @param id the id of the shippingFeeChartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippingFeeChartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipping-fee-charts/{id}")
    public ResponseEntity<ShippingFeeChartDTO> getShippingFeeChart(@PathVariable Long id) {
        log.debug("REST request to get ShippingFeeChart : {}", id);
        Optional<ShippingFeeChartDTO> shippingFeeChartDTO = shippingFeeChartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shippingFeeChartDTO);
    }

    /**
     * {@code DELETE  /shipping-fee-charts/:id} : delete the "id" shippingFeeChart.
     *
     * @param id the id of the shippingFeeChartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipping-fee-charts/{id}")
    public ResponseEntity<Void> deleteShippingFeeChart(@PathVariable Long id) {
        log.debug("REST request to delete ShippingFeeChart : {}", id);

        shippingFeeChartService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
