package com.vertical.commerce.service;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.security.Principal;

public interface CommonService {
    ProductAttributeDTO getProductAttribute(String filter);

    ProductAttribute getProductAttributeEntity(Long id, String filter);

    ProductOptionDTO getProductOption(String filter);

    ProductOption getProductOptionEntity(Long id, String filter);

    MaterialsDTO getMaterials(String filter);

    Materials getMaterialsEntity(Long id, String filter);

    ProductBrandDTO getProductBrands(String filter);

    ProductBrand getProductBrandsEntity(Long id, String filter);

    WarrantyTypesDTO getWarrantyTypes(String filter);

    WarrantyTypes getWarrantyTypesEntity(Long id, String filter);

    BarcodeTypesDTO getBarCodesType(String filter);

    BarcodeTypes getBarCodesTypeEntity(Long id,String filter);

    ProductCategoryDTO getProductCategory(String filter);

    ProductCategory getProductCategoryEntity(Long id,String filter);

    CultureDTO getCulture(String filter);

    Culture getCultureEntity(Long id,String filter);

    UnitMeasureDTO getUnitMeasure(String filter);

    UnitMeasure getUnitMeasureEntity(Long id,String filter);

    CurrencyDTO getCurrency(String filter);

    Currency getCurrencyEntity(Long id,String filter);

    ProductTagsDTO getProductTags(String filter);

    ProductTags getProductTagsEntity(Long id,String filter);

    People getPeopleByPrincipal(Principal principal);

    Suppliers getSupplierByPrincipal(Principal principal);

    Customers getCustomerByPrincipal(Principal principal);

    JSONArray getProductDetails(Products product);

    JSONArray getCartDetails(ShoppingCarts cart);

    JSONObject getOrderDetails(Orders order);

    DeliveryMethodsDTO getDeliveryMethods(String filter);

    DeliveryMethods getDeliveryMethodsEntity(Long id,String filter);

    PaymentMethodsDTO getPaymentMethods(String filter);

    PaymentMethods getPaymentMethodsEntity(Long id,String filter);

    TaxDTO getTaxByCode(String filter);

    Tax getTaxByCodeEntity(Long id,String filter);

    ZoneDTO getZone(String filter);

    Zone getZoneEntity(Long id,String filter);

    CitiesDTO getCities(String filter);

    Cities getCitiesEntity(Long id,String filter);
}
