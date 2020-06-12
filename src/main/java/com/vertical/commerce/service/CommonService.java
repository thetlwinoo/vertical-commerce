package com.vertical.commerce.service;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.service.dto.*;

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

    String getCartString(ShoppingCarts cart);

    String getOrderLineString(Orders order,OrderLines orderLines);
}
