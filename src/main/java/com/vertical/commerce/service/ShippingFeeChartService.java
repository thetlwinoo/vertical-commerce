package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.ShippingFeeChartDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.vertical.commerce.domain.ShippingFeeChart}.
 */
public interface ShippingFeeChartService {

    /**
     * Save a shippingFeeChart.
     *
     * @param shippingFeeChartDTO the entity to save.
     * @return the persisted entity.
     */
    ShippingFeeChartDTO save(ShippingFeeChartDTO shippingFeeChartDTO);

    /**
     * Get all the shippingFeeCharts.
     *
     * @return the list of entities.
     */
    List<ShippingFeeChartDTO> findAll();


    /**
     * Get the "id" shippingFeeChart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingFeeChartDTO> findOne(Long id);

    /**
     * Delete the "id" shippingFeeChart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
