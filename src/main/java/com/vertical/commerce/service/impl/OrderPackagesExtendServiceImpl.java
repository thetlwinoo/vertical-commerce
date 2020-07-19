package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.OrderPackagesExtendService;
import com.vertical.commerce.service.ProductsExtendService;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.mapper.OrderLinesMapper;
import com.vertical.commerce.service.mapper.OrderPackagesMapper;
import com.vertical.commerce.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
    private final ProductsExtendService productsExtendService;
    private final ProductsRepository productsRepository;
    private final PhotosRepository photosRepository;

    public OrderPackagesExtendServiceImpl(OrderPackagesRepository orderPackagesRepository, OrdersExtendRepository ordersExtendRepository, OrderLinesRepository orderLinesRepository, OrdersRepository ordersRepository, OrderPackagesMapper orderPackagesMapper, OrderLinesMapper orderLinesMapper, ProductsExtendService productsExtendService, ProductsRepository productsRepository, PhotosRepository photosRepository) {
        this.orderPackagesRepository = orderPackagesRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.orderLinesRepository = orderLinesRepository;
        this.ordersRepository = ordersRepository;
        this.orderPackagesMapper = orderPackagesMapper;
        this.orderLinesMapper = orderLinesMapper;
        this.productsExtendService = productsExtendService;
        this.productsRepository = productsRepository;
        this.photosRepository = photosRepository;
    }

//    @Override
//    public OrderPackagesDTO saveOrderPackage(OrderPackagesDTO orderPackagesDTO) {
//        log.debug("Request to save OrderPackages : {}", orderPackagesDTO);
//        OrderPackages orderPackages = orderPackagesMapper.toEntity(orderPackagesDTO);
//        orderPackages = orderPackagesRepository.save(orderPackages);
//
//        Orders saveOrder = orderPackages.getOrder();
//        saveOrder.setOrderDetails(ordersExtendRepository.getOrderPackageDetails(saveOrder.getId()));
//        ordersRepository.save(saveOrder);
//
//        return orderPackagesMapper.toDto(orderPackages);
//    }

    @Override
    public OrderPackagesDTO saveOrderPackage(String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("id");
        JsonNode jsonNode2 = actualObj.get("completedReview");
        JsonNode jsonNode3 = actualObj.get("customerReviewedOn");
        JsonNode jsonNode4 = actualObj.get("deliveryRating");
        JsonNode jsonNode5 = actualObj.get("deliveryReview");
        JsonNode jsonNode6 = actualObj.get("reviewAsAnonymous");
        JsonNode jsonNode7 = actualObj.get("sellerRating");
        JsonNode jsonNode8 = actualObj.get("sellerReview");
        JsonNode jsonNode9 = actualObj.get("lineReviewList");

        if (jsonNode1 == null) {
            throw new BadRequestAlertException("Invalid id", "vscommerceOrderPackagesExtend", "idnull");
        }

        OrderPackages orderPackages = orderPackagesRepository.getOne(jsonNode1.longValue());

        orderPackages.setCompletedReview(jsonNode2.booleanValue());
        orderPackages.setCustomerReviewedOn(Instant.parse(jsonNode3.textValue()));
        orderPackages.setDeliveryRating(jsonNode4.intValue());
        orderPackages.setDeliveryReview(jsonNode5.textValue());
        orderPackages.setReviewAsAnonymous(jsonNode6.booleanValue());
        orderPackages.setSellerRating(jsonNode7.intValue());
        orderPackages.setSellerReview(jsonNode8.textValue());

        if(jsonNode9.isArray()){
            for (final JsonNode objNode : jsonNode9) {
                System.out.println(objNode);
                JsonNode idNode = objNode.get("id");
                JsonNode lineRatingNode = objNode.get("lineRating");
                JsonNode lineReviewNode = objNode.get("lineReview");
                JsonNode reviewImageNode = objNode.get("reviewImageId");

                OrderLines orderLines= orderLinesRepository.getOne(idNode.longValue());

                orderLines.setLineRating(lineRatingNode.intValue());
                orderLines.setLineReview(lineReviewNode.textValue());
                orderLines.setCustomerReviewedOn(Instant.now());
                orderLines.setReviewPhoto(reviewImageNode.textValue());
//                if(reviewImageNode != null){
//                    Photos photos = photosRepository.getOne(reviewImageNode.longValue());
//                    orderLines.setReviewImage(photos);
//                }

                orderLines.setOrderPackage(orderPackages);
            }
        }

        orderPackagesRepository.save(orderPackages);

        updateProductDetailsByOrder(orderPackages.getOrder().getId());

        Orders orders = ordersRepository.getOne(orderPackages.getOrder().getId());
        orders.setOrderDetails(ordersExtendRepository.getOrderPackageDetails(orders.getId()));
        ordersRepository.save(orders);

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
    public void updateProductDetailsByOrder(Long orderId) throws JsonProcessingException {
        productsExtendService.updateProductDetailsByOrder(orderId);
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

    @Override
    public void orderDetailsBatchUpdate(){
        for(Orders orders:ordersRepository.findAll()){
            orders.setOrderDetails(ordersExtendRepository.getOrderPackageDetails(orders.getId()));
            ordersRepository.save(orders);
        }
    }
}
