package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.CampaignDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {



    default Campaign fromId(Long id) {
        if (id == null) {
            return null;
        }
        Campaign campaign = new Campaign();
        campaign.setId(id);
        return campaign;
    }
}
