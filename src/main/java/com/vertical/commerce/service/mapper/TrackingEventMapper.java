package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.TrackingEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TrackingEvent} and its DTO {@link TrackingEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrackingEventMapper extends EntityMapper<TrackingEventDTO, TrackingEvent> {



    default TrackingEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrackingEvent trackingEvent = new TrackingEvent();
        trackingEvent.setId(id);
        return trackingEvent;
    }
}
