package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.SubscriptionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subscriptions} and its DTO {@link SubscriptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionsMapper extends EntityMapper<SubscriptionsDTO, Subscriptions> {



    default Subscriptions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setId(id);
        return subscriptions;
    }
}
