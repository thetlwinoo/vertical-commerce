package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.PostalCodeMappersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostalCodeMappers} and its DTO {@link PostalCodeMappersDTO}.
 */
@Mapper(componentModel = "spring", uses = {TownshipsMapper.class})
public interface PostalCodeMappersMapper extends EntityMapper<PostalCodeMappersDTO, PostalCodeMappers> {

    @Mapping(source = "township.id", target = "townshipId")
    @Mapping(source = "township.name", target = "townshipName")
    PostalCodeMappersDTO toDto(PostalCodeMappers postalCodeMappers);

    @Mapping(source = "townshipId", target = "township")
    PostalCodeMappers toEntity(PostalCodeMappersDTO postalCodeMappersDTO);

    default PostalCodeMappers fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostalCodeMappers postalCodeMappers = new PostalCodeMappers();
        postalCodeMappers.setId(id);
        return postalCodeMappers;
    }
}
