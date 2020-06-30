package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.OrderLines;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.repository.OrderLinesRepository;
import com.vertical.commerce.repository.OrderPackagesRepository;
import com.vertical.commerce.repository.OrdersExtendRepository;
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.OrderPackagesExtendService;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.mapper.OrderLinesMapper;
import com.vertical.commerce.service.mapper.OrderPackagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderPackagesExtendServiceImpl implements OrderPackagesExtendService {

    private final Logger log = LoggerFactory.getLogger(OrderPackagesExtendServiceImpl.class);

    private final OrderPackagesRepository orderPackagesRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final OrderLinesRepository orderLinesRepository;
    private final OrdersRepository ordersRepository;
    private final OrderPackagesMapper orderPackagesMapper;
    private final OrderLinesMapper orderLinesMapper;

    public OrderPackagesExtendServiceImpl(OrderPackagesRepository orderPackagesRepository, OrdersExtendRepository ordersExtendRepository, OrderLinesRepository orderLinesRepository, OrdersRepository ordersRepository, OrderPackagesMapper orderPackagesMapper, OrderLinesMapper orderLinesMapper) {
        this.orderPackagesRepository = orderPackagesRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.orderLinesRepository = orderLinesRepository;
        this.ordersRepository = ordersRepository;
        this.orderPackagesMapper = orderPackagesMapper;
        this.orderLinesMapper = orderLinesMapper;
    }

    @Override
    public OrderPackagesDTO saveOrderPackage(OrderPackagesDTO orderPackagesDTO) {
        log.debug("Request to save OrderPackages : {}", orderPackagesDTO);
        OrderPackages orderPackages = orderPackagesMapper.toEntity(orderPackagesDTO);
        orderPackages = orderPackagesRepository.save(orderPackages);

        Orders saveOrder = orderPackages.getOrder();
        saveOrder.setOrderDetails(ordersExtendRepository.getOrderPackageDetails(saveOrder.getId()));
        ordersRepository.save(saveOrder);

        return orderPackagesMapper.toDto(orderPackages);
    }

    @Override
    public OrderLinesDTO saveOrderLine(OrderLinesDTO orderLinesDTO) {
        log.debug("Request to save OrderLines : {}", orderLinesDTO);
        OrderLines orderLines = orderLinesMapper.toEntity(orderLinesDTO);
        orderLines = orderLinesRepository.save(orderLines);
        return orderLinesMapper.toDto(orderLines);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderPackagesDTO> findOrderPackage(Long id) {
        log.debug("Request to get OrderPackages : {}", id);
        return orderPackagesRepository.findById(id)
            .map(orderPackagesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderLinesDTO> findOrderLine(Long id) {
        log.debug("Request to get OrderLines : {}", id);
        return orderLinesRepository.findById(id)
            .map(orderLinesMapper::toDto);
    }
}
