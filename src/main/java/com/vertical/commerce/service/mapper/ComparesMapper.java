package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.ComparesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compares} and its DTO {@link ComparesDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface ComparesMapper extends EntityMapper<ComparesDTO, Compares> {

    @Mapping(source = "compareUser.id", target = "compareUserId")
    @Mapping(source = "compareUser.fullName", target = "compareUserFullName")
    ComparesDTO toDto(Compares compares);

    @Mapping(source = "compareUserId", target = "compareUser")
    @Mapping(target = "compareLineLists", ignore = true)
    @Mapping(target = "removeCompareLineList", ignore = true)
    Compares toEntity(ComparesDTO comparesDTO);

    default Compares fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compares compares = new Compares();
        compares.setId(id);
        return compares;
    }
}
