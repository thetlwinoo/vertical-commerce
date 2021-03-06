package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.service.dto.ProductCategoryDTO;
import com.vertical.commerce.service.dto.ProductsCriteria;
import com.vertical.commerce.service.dto.ProductsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProductsExtendService {
    Optional<ProductsDTO> findOne(Long id);

    List<ProductsDTO> findAllByProductCategory(Pageable pageable, Long productSubCategoryId);

    List<ProductsDTO> findTop18ByOrderByLastEditedWhenDesc();

    List<ProductsDTO> findTop12ByOrderBySellCountDesc();

    List<ProductsDTO> findTop12ByOrderBySellCountDescCacheRefresh();

    List<ProductsDTO> getRelatedProducts(Long productSubCategoryId, Long id);

    List<ProductsDTO> searchProducts(String keyword, Integer page, Integer size);

    Page<ProductsDTO> searchProductsWithPaging(ProductsCriteria criteria, Pageable pageable, String colors);

    List<ProductsDTO> searchProductsAll(String keyword);

    List<ProductsDTO> searchKeywords(String keyword);

    List<Long> getSubCategoryList(Long categoryId);

    List<ProductCategoryDTO> getRelatedCategories(String keyword, Long category);

    List<String> getRelatedColors(String keyword, Long category);

    Object getRelatedPriceRange(String keyword, Long category);

    List<String> getRelatedBrands(String keyword, Long category);

    ProductsDTO saveProducts(Products products, String serverUrl);

    ProductsDTO importProducts(ProductsDTO productsDTO, Principal principal);

    List<Long> getProductIdsBySupplier(Long supplierId);

    String getProductDetails(Long productId);

    String getProductDetailsShort(Long productId);

    String getFilterProducts(Long categoryId,Long brandId,Long supplierId,String brandIdList,String tag,String attributes,String options,String priceRange,Integer rating,Integer page,Integer limit);

    String getFilterControllers(Long categoryId,Long brandId,Long supplierId,String tag);

    void productDetailsUpdate(Long id) throws JsonProcessingException;

    void productDetailsBatchUpdate() throws JsonProcessingException;

    List<Long> getProductsIdsByOrder(Long productId);

    void updateProductDetailsByOrder(Long orderId) throws JsonProcessingException;

    String getProductsHome();

    List<String> getTags(String filter);
}
