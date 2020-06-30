package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.repository.OrdersExtendRepository;
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.OrderPackagesService;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.repository.OrderPackagesRepository;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.mapper.OrderPackagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link OrderPackages}.
 */
@Service
@Transactional
public class OrderPackagesServiceImpl implements OrderPackagesService {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesServiceImpl.class);

    private final OrderPackagesRepository orderPackagesRepository;

    private final OrderPackagesMapper orderPackagesMapper;

    public OrderPackagesServiceImpl(OrderPackagesRepository orderPackagesRepository, OrderPackagesMapper orderPackagesMapper) {
        this.orderPackagesRepository = orderPackagesRepository;
        this.orderPackagesMapper = orderPackagesMapper;
    }

    /**
     * Save a orderPackages.
     *
     * @param orderPackagesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderPackagesDTO save(OrderPackagesDTO orderPackagesDTO) {
        log.debug("Request to save OrderPackages : {}", orderPackagesDTO);
        OrderPackages orderPackages = orderPackagesMapper.toEntity(orderPackagesDTO);
        orderPackages = orderPackagesRepository.save(orderPackages);

        return orderPackagesMapper.toDto(orderPackages);
    }

    /**
     * Get all the orderPackages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderPackagesDTO> findAll() {
        log.debug("Request to get all OrderPackages");
        return orderPackagesRepository.findAll().stream()
            .map(orderPackagesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one orderPackages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderPackagesDTO> findOne(Long id) {
        log.debug("Request to get OrderPackages : {}", id);
        return orderPackagesRepository.findById(id)
            .map(orderPackagesMapper::toDto);
    }

    /**
     * Delete the orderPackages by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderPackages : {}", id);

        orderPackagesRepository.deleteById(id);
    }
}
