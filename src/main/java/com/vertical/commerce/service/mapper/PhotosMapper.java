package com.vertical.commerce.service.mapper;


import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.PhotosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photos} and its DTO {@link PhotosDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class, SuppliersMapper.class})
public interface PhotosMapper extends EntityMapper<PhotosDTO, Photos> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    @Mapping(source = "supplierBanner.id", target = "supplierBannerId")
    @Mapping(source = "supplierDocument.id", target = "supplierDocumentId")
    PhotosDTO toDto(Photos photos);

    @Mapping(source = "stockItemId", target = "stockItem")
    @Mapping(source = "supplierBannerId", target = "supplierBanner")
    @Mapping(source = "supplierDocumentId", target = "supplierDocument")
    Photos toEntity(PhotosDTO photosDTO);

    default Photos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photos photos = new Photos();
        photos.setId(id);
        return photos;
    }
}
