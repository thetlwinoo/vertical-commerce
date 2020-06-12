package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.OrderTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderTracking} and its DTO {@link OrderTrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class, TrackingEventMapper.class})
public interface OrderTrackingMapper extends EntityMapper<OrderTrackingDTO, OrderTracking> {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "trackingEvent.id", target = "trackingEventId")
    @Mapping(source = "trackingEvent.name", target = "trackingEventName")
    OrderTrackingDTO toDto(OrderTracking orderTracking);

    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "trackingEventId", target = "trackingEvent")
    OrderTracking toEntity(OrderTrackingDTO orderTrackingDTO);

    default OrderTracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setId(id);
        return orderTracking;
    }
}
