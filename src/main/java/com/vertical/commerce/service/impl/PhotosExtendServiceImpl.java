package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Photos;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.repository.PhotosExtendRepository;
import com.vertical.commerce.repository.PhotosRepository;
import com.vertical.commerce.repository.ProductsRepository;
import com.vertical.commerce.repository.StockItemsRepository;
import com.vertical.commerce.service.PhotosExtendService;
import com.vertical.commerce.service.dto.PhotosDTO;
import com.vertical.commerce.service.mapper.PhotosMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotosExtendServiceImpl implements PhotosExtendService {

    private final Logger log = LoggerFactory.getLogger(PhotosExtendServiceImpl.class);
    private final PhotosRepository photosRepository;
    private final PhotosExtendRepository photosExtendRepository;
    private final StockItemsRepository stockItemsRepository;
    private final ProductsRepository productsRepository;
    private final PhotosMapper photosMapper;

    public PhotosExtendServiceImpl(PhotosRepository photosRepository, PhotosExtendRepository photosExtendRepository, StockItemsRepository stockItemsRepository, ProductsRepository productsRepository, PhotosMapper photosMapper) {
        this.photosRepository = photosRepository;
        this.photosExtendRepository = photosExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.productsRepository = productsRepository;
        this.photosMapper = photosMapper;
    }

    @Override
    public PhotosDTO save(PhotosDTO photosDTO) {
        log.debug("Request to save Photos : {}", photosDTO);
        Photos photos = photosMapper.toEntity(photosDTO);
        StockItems stockItems = stockItemsRepository.getOne(photos.getStockItem().getId());
        if(stockItems.getThumbnailUrl() == null){
            photos.setDefaultInd(true);
            photos = photosRepository.save(photos);
            stockItems.setThumbnailUrl(photos.getThumbnailUrl());
            stockItemsRepository.save(stockItems);

            Products products = productsRepository.getOne(stockItems.getProduct().getId());
            products.setStockItemString(getStockItemString(products));

            productsRepository.save(products);
        }else{
            photos = photosRepository.save(photos);
        }

        return photosMapper.toDto(photos);
    }

    @Override
    public Optional<PhotosDTO> setDefault(Long photoId) {
        Optional<Photos> stockItemPhoto = photosRepository.findById(photoId);
        List<Photos> photosList = new ArrayList<>();

        if (stockItemPhoto.isPresent()) {
            Long stockItemId = stockItemPhoto.get().getStockItem().getId();
            photosList = photosExtendRepository.findAllByStockItemId(stockItemId);
        } else {
            throw new IllegalArgumentException("product photo not found");
        }

        for (Photos i : photosList) {
            if (i.getId() == photoId) {
                i.setDefaultInd(true);
                photosRepository.save(i);
                StockItems stockItems = stockItemsRepository.getOne(i.getStockItem().getId());
                stockItems.setThumbnailUrl(i.getThumbnailUrl());
                stockItemsRepository.save(stockItems);

                Products products = productsRepository.getOne(stockItems.getProduct().getId());
                products.setStockItemString(getStockItemString(products));
                productsRepository.save(products);
            } else {
                if (i.isDefaultInd()) {
                    i.setDefaultInd(false);
                    photosRepository.save(i);
                }
            }
        }

        return stockItemPhoto.map(photosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotosDTO> findOne(Long id) {
        log.debug("Request to get Photos : {}", id);
        return photosRepository.findById(id)
            .map(photosMapper::toDto);
    }

    @Override
    public void deleteByBlobId(String id){
        photosExtendRepository.deletePhotosByBlobId(id);
    }

    private String getStockItemString(Products product){
        List<String> stockItemList= new ArrayList<>();
        for (StockItems i : product.getStockItemLists()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stockItemId",i.getId());
            jsonObject.put("thumbnail",i.getThumbnailUrl());
            jsonObject.put("unitPrice",i.getUnitPrice());
            jsonObject.put("recommendedRetailPrice",i.getRecommendedRetailPrice());
            String jsonString = jsonObject.toJSONString();
            stockItemList.add(jsonString);
        }

        return String.join(";",stockItemList);
    }
}
