package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.ShippingFeeChartService;
import com.vertical.commerce.domain.ShippingFeeChart;
import com.vertical.commerce.repository.ShippingFeeChartRepository;
import com.vertical.commerce.service.dto.ShippingFeeChartDTO;
import com.vertical.commerce.service.mapper.ShippingFeeChartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShippingFeeChart}.
 */
@Service
@Transactional
public class ShippingFeeChartServiceImpl implements ShippingFeeChartService {

    private final Logger log = LoggerFactory.getLogger(ShippingFeeChartServiceImpl.class);

    private final ShippingFeeChartRepository shippingFeeChartRepository;

    private final ShippingFeeChartMapper shippingFeeChartMapper;

    public ShippingFeeChartServiceImpl(ShippingFeeChartRepository shippingFeeChartRepository, ShippingFeeChartMapper shippingFeeChartMapper) {
        this.shippingFeeChartRepository = shippingFeeChartRepository;
        this.shippingFeeChartMapper = shippingFeeChartMapper;
    }

    /**
     * Save a shippingFeeChart.
     *
     * @param shippingFeeChartDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShippingFeeChartDTO save(ShippingFeeChartDTO shippingFeeChartDTO) {
        log.debug("Request to save ShippingFeeChart : {}", shippingFeeChartDTO);
        ShippingFeeChart shippingFeeChart = shippingFeeChartMapper.toEntity(shippingFeeChartDTO);
        shippingFeeChart = shippingFeeChartRepository.save(shippingFeeChart);
        return shippingFeeChartMapper.toDto(shippingFeeChart);
    }

    /**
     * Get all the shippingFeeCharts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShippingFeeChartDTO> findAll() {
        log.debug("Request to get all ShippingFeeCharts");
        return shippingFeeChartRepository.findAll().stream()
            .map(shippingFeeChartMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shippingFeeChart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingFeeChartDTO> findOne(Long id) {
        log.debug("Request to get ShippingFeeChart : {}", id);
        return shippingFeeChartRepository.findById(id)
            .map(shippingFeeChartMapper::toDto);
    }

    /**
     * Delete the shippingFeeChart by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingFeeChart : {}", id);

        shippingFeeChartRepository.deleteById(id);
    }
}
