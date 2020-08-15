package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.PostalCodeMappersLocalizeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostalCodeMappersLocalize} and its DTO {@link PostalCodeMappersLocalizeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CultureMapper.class, PostalCodeMappersMapper.class})
public interface PostalCodeMappersLocalizeMapper extends EntityMapper<PostalCodeMappersLocalizeDTO, PostalCodeMappersLocalize> {

    @Mapping(source = "culture.id", target = "cultureId")
    @Mapping(source = "culture.code", target = "cultureCode")
    @Mapping(source = "postalCodeMapper.id", target = "postalCodeMapperId")
    @Mapping(source = "postalCodeMapper.name", target = "postalCodeMapperName")
    PostalCodeMappersLocalizeDTO toDto(PostalCodeMappersLocalize postalCodeMappersLocalize);

    @Mapping(source = "cultureId", target = "culture")
    @Mapping(source = "postalCodeMapperId", target = "postalCodeMapper")
    PostalCodeMappersLocalize toEntity(PostalCodeMappersLocalizeDTO postalCodeMappersLocalizeDTO);

    default PostalCodeMappersLocalize fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostalCodeMappersLocalize postalCodeMappersLocalize = new PostalCodeMappersLocalize();
        postalCodeMappersLocalize.setId(id);
        return postalCodeMappersLocalize;
    }
}
