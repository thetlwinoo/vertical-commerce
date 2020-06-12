package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.WishlistsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wishlists} and its DTO {@link WishlistsDTO}.
 */
@Mapper(componentModel = "spring", uses = {PeopleMapper.class})
public interface WishlistsMapper extends EntityMapper<WishlistsDTO, Wishlists> {

    @Mapping(source = "wishlistUser.id", target = "wishlistUserId")
    WishlistsDTO toDto(Wishlists wishlists);

    @Mapping(source = "wishlistUserId", target = "wishlistUser")
    @Mapping(target = "wishlistLineLists", ignore = true)
    @Mapping(target = "removeWishlistLineList", ignore = true)
    Wishlists toEntity(WishlistsDTO wishlistsDTO);

    default Wishlists fromId(Long id) {
        if (id == null) {
            return null;
        }
        Wishlists wishlists = new Wishlists();
        wishlists.setId(id);
        return wishlists;
    }
}
