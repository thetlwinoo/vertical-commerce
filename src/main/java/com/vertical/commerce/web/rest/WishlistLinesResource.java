package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.WishlistLinesService;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import com.vertical.commerce.service.dto.WishlistLinesDTO;
import com.vertical.commerce.service.dto.WishlistLinesCriteria;
import com.vertical.commerce.service.WishlistLinesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vertical.commerce.domain.WishlistLines}.
 */
@RestController
@RequestMapping("/api")
public class WishlistLinesResource {

    private final Logger log = LoggerFactory.getLogger(WishlistLinesResource.class);

    private static final String ENTITY_NAME = "vscommerceWishlistLines";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WishlistLinesService wishlistLinesService;

    private final WishlistLinesQueryService wishlistLinesQueryService;

    public WishlistLinesResource(WishlistLinesService wishlistLinesService, WishlistLinesQueryService wishlistLinesQueryService) {
        this.wishlistLinesService = wishlistLinesService;
        this.wishlistLinesQueryService = wishlistLinesQueryService;
    }

    /**
     * {@code POST  /wishlist-lines} : Create a new wishlistLines.
     *
     * @param wishlistLinesDTO the wishlistLinesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wishlistLinesDTO, or with status {@code 400 (Bad Request)} if the wishlistLines has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wishlist-lines")
    public ResponseEntity<WishlistLinesDTO> createWishlistLines(@RequestBody WishlistLinesDTO wishlistLinesDTO) throws URISyntaxException {
        log.debug("REST request to save WishlistLines : {}", wishlistLinesDTO);
        if (wishlistLinesDTO.getId() != null) {
            throw new BadRequestAlertException("A new wishlistLines cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WishlistLinesDTO result = wishlistLinesService.save(wishlistLinesDTO);
        return ResponseEntity.created(new URI("/api/wishlist-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wishlist-lines} : Updates an existing wishlistLines.
     *
     * @param wishlistLinesDTO the wishlistLinesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wishlistLinesDTO,
     * or with status {@code 400 (Bad Request)} if the wishlistLinesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wishlistLinesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wishlist-lines")
    public ResponseEntity<WishlistLinesDTO> updateWishlistLines(@RequestBody WishlistLinesDTO wishlistLinesDTO) throws URISyntaxException {
        log.debug("REST request to update WishlistLines : {}", wishlistLinesDTO);
        if (wishlistLinesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WishlistLinesDTO result = wishlistLinesService.save(wishlistLinesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wishlistLinesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /wishlist-lines} : get all the wishlistLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wishlistLines in body.
     */
    @GetMapping("/wishlist-lines")
    public ResponseEntity<List<WishlistLinesDTO>> getAllWishlistLines(WishlistLinesCriteria criteria) {
        log.debug("REST request to get WishlistLines by criteria: {}", criteria);
        List<WishlistLinesDTO> entityList = wishlistLinesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /wishlist-lines/count} : count all the wishlistLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/wishlist-lines/count")
    public ResponseEntity<Long> countWishlistLines(WishlistLinesCriteria criteria) {
        log.debug("REST request to count WishlistLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(wishlistLinesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /wishlist-lines/:id} : get the "id" wishlistLines.
     *
     * @param id the id of the wishlistLinesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wishlistLinesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wishlist-lines/{id}")
    public ResponseEntity<WishlistLinesDTO> getWishlistLines(@PathVariable Long id) {
        log.debug("REST request to get WishlistLines : {}", id);
        Optional<WishlistLinesDTO> wishlistLinesDTO = wishlistLinesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wishlistLinesDTO);
    }

    /**
     * {@code DELETE  /wishlist-lines/:id} : delete the "id" wishlistLines.
     *
     * @param id the id of the wishlistLinesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wishlist-lines/{id}")
    public ResponseEntity<Void> deleteWishlistLines(@PathVariable Long id) {
        log.debug("REST request to delete WishlistLines : {}", id);

        wishlistLinesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
