package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.OrderLines;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.repository.OrderLinesExtendRepository;
import com.vertical.commerce.repository.OrderLinesRepository;
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.OrderLinesExtendService;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.mapper.OrderLinesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderLinesExtendServiceImpl implements OrderLinesExtendService {

    private final Logger log = LoggerFactory.getLogger(OrderLinesExtendServiceImpl.class);
    private final OrderLinesRepository orderLinesRepository;
    private final OrderLinesExtendRepository orderLinesExtendRepository;
    private final OrdersRepository ordersRepository;
    private final CommonService commonService;

    private final OrderLinesMapper orderLinesMapper;

    public OrderLinesExtendServiceImpl(OrderLinesRepository orderLinesRepository, OrderLinesExtendRepository orderLinesExtendRepository, OrdersRepository ordersRepository, CommonService commonService, OrderLinesMapper orderLinesMapper) {
        this.orderLinesRepository = orderLinesRepository;
        this.orderLinesExtendRepository = orderLinesExtendRepository;
        this.ordersRepository = ordersRepository;
        this.commonService = commonService;
        this.orderLinesMapper = orderLinesMapper;
    }

    @Override
    public OrderLinesDTO save(OrderLinesDTO orderLinesDTO) {
        log.debug("Request to save OrderLines : {}", orderLinesDTO);
        OrderLines orderLines = orderLinesMapper.toEntity(orderLinesDTO);
        orderLines = orderLinesRepository.save(orderLines);

//        Orders orders = orderLines.getOrder();
//        List<String> orderLineList= new ArrayList<>();
//
//        for (OrderLines i : orders.getOrderLineLists()) {
//            String orderLineString = commonService.getOrderLineString(orders,i);
//            orderLineList.add(orderLineString);
//        }
//        orders.setOrderLineString(String.join(";",orderLineList));
//        ordersRepository.save(orders);

        return orderLinesMapper.toDto(orderLines);
    }

    @Override
    public List<OrderLinesDTO> getOrderLinesByProduct(Long id){
        return orderLinesExtendRepository.getReviewsByProduct(id).stream()
            .map(orderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
