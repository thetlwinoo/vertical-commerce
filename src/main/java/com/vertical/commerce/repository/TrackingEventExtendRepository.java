package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TrackingEvent;

public interface TrackingEventExtendRepository extends TrackingEventRepository {
    TrackingEvent findTrackingEventByNameEquals(String trackingName);
}
