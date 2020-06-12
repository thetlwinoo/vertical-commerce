package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Photos;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhotosExtendRepository extends PhotosRepository {
    Optional<Photos> findByStockItemIdAndDefaultIndIsTrue(Long stockItemId);

    List<Photos> findAllByStockItemId(Long stockItemId);

    void deletePhotosByBlobId(String id);

    @Modifying
    @Query(value = "delete from Photos p where p.id=:id", nativeQuery = true)
    void deletePhotos(@Param("id") Long id);
}
