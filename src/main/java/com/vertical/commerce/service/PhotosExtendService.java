package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.PhotosDTO;

import java.util.Optional;

public interface PhotosExtendService {
    Optional<PhotosDTO> setDefault(Long photoId);

    PhotosDTO save(PhotosDTO photosDTO);

    Optional<PhotosDTO> findOne(Long id);

    void deleteByBlobId(String id);
}
