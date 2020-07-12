package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.OrderLines;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import com.vertical.commerce.repository.OrderLinesExtendRepository;
import com.vertical.commerce.repository.OrderLinesRepository;
import com.vertical.commerce.repository.OrdersExtendRepository;
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.OrderLinesExtendService;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrderLinesMapper;
import com.vertical.commerce.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderLinesExtendServiceImpl implements OrderLinesExtendService {

    private final Logger log = LoggerFactory.getLogger(OrderLinesExtendServiceImpl.class);
    private final OrderLinesRepository orderLinesRepository;
    private final OrderLinesExtendRepository orderLinesExtendRepository;
    private final OrdersRepository ordersRepository;
    private final CommonService commonService;
    private final OrdersExtendRepository ordersExtendRepository;
    private final OrdersMapper ordersMapper;
    private final OrderLinesMapper orderLinesMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderLinesExtendServiceImpl(OrderLinesRepository orderLinesRepository, OrderLinesExtendRepository orderLinesExtendRepository, OrdersRepository ordersRepository, CommonService commonService, OrdersExtendRepository ordersExtendRepository, OrdersMapper ordersMapper, OrderLinesMapper orderLinesMapper) {
        this.orderLinesRepository = orderLinesRepository;
        this.orderLinesExtendRepository = orderLinesExtendRepository;
        this.ordersRepository = ordersRepository;
        this.commonService = commonService;
        this.ordersExtendRepository = ordersExtendRepository;
        this.ordersMapper = ordersMapper;
        this.orderLinesMapper = orderLinesMapper;
    }

    @Override
    public OrderLinesDTO save(OrderLinesDTO orderLinesDTO) {
        log.debug("Request to save OrderLines : {}", orderLinesDTO);
        OrderLines orderLines = orderLinesMapper.toEntity(orderLinesDTO);
        orderLines = orderLinesRepository.save(orderLines);

        return orderLinesMapper.toDto(orderLines);
    }

    @Override
    public List<OrderLinesDTO> getOrderLinesByProduct(Long id){
        return orderLinesExtendRepository.getReviewsByProduct(id).stream()
            .map(orderLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Orders getOrders(Long id){
        return orderLinesRepository.getOne(id).getOrderPackage().getOrder();
    }

    @Override
    public Map<String, Object> cancelOrderLine(Long id){
        Map<String, Object> response = new HashMap<String, Object>();;
        OrderLines orderLines = orderLinesRepository.getOne(id);
        Orders orders = orderLines.getOrderPackage().getOrder();

        if(orders.getStatus().equals(OrderStatus.NEW_ORDER) || orders.getStatus().equals(OrderStatus.PENDING) || orders.getStatus().equals(OrderStatus.COMPLETED)){
//            entityManager.clear();

            Integer errorCode = ordersExtendRepository.checkOrderCancel(orderLines.getId());

            if(errorCode == 0){
//                entityManager.clear();
                response.put("status", "success");
                response.put("orderId", orders.getId());
                response.put("customerId", orders.getCustomer().getId());
            }else{
                response.put("status", "failed");
                response.put("error", "Order cannot cancel.");
            }
        }else{
            response.put("status", "failed");
            response.put("error", "Order cannot cancel.");
        }

        return response;
    }
}
