package com.vertical.commerce.service.impl;

import com.vertical.commerce.service.OrderTrackingService;
import com.vertical.commerce.domain.OrderTracking;
import com.vertical.commerce.repository.OrderTrackingRepository;
import com.vertical.commerce.service.dto.OrderTrackingDTO;
import com.vertical.commerce.service.mapper.OrderTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderTracking}.
 */
@Service
@Transactional
public class OrderTrackingServiceImpl implements OrderTrackingService {

    private final Logger log = LoggerFactory.getLogger(OrderTrackingServiceImpl.class);

    private final OrderTrackingRepository orderTrackingRepository;

    private final OrderTrackingMapper orderTrackingMapper;

    public OrderTrackingServiceImpl(OrderTrackingRepository orderTrackingRepository, OrderTrackingMapper orderTrackingMapper) {
        this.orderTrackingRepository = orderTrackingRepository;
        this.orderTrackingMapper = orderTrackingMapper;
    }

    /**
     * Save a orderTracking.
     *
     * @param orderTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderTrackingDTO save(OrderTrackingDTO orderTrackingDTO) {
        log.debug("Request to save OrderTracking : {}", orderTrackingDTO);
        OrderTracking orderTracking = orderTrackingMapper.toEntity(orderTrackingDTO);
        orderTracking = orderTrackingRepository.save(orderTracking);
        return orderTrackingMapper.toDto(orderTracking);
    }

    /**
     * Get all the orderTrackings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderTrackingDTO> findAll() {
        log.debug("Request to get all OrderTrackings");
        return orderTrackingRepository.findAll().stream()
            .map(orderTrackingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderTrackingDTO> findOne(Long id) {
        log.debug("Request to get OrderTracking : {}", id);
        return orderTrackingRepository.findById(id)
            .map(orderTrackingMapper::toDto);
    }

    /**
     * Delete the orderTracking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderTracking : {}", id);

        orderTrackingRepository.deleteById(id);
    }
}
