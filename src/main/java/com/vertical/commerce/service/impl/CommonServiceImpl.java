package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.repository.CustomersExtendRepository;
import com.vertical.commerce.repository.PeopleExtendRepository;
import com.vertical.commerce.repository.SuppliersExtendRepository;
import com.vertical.commerce.repository.UserRepository;
import com.vertical.commerce.service.*;
import com.vertical.commerce.service.dto.*;
import com.vertical.commerce.service.mapper.*;
import io.github.jhipster.service.filter.StringFilter;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    private final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final UserRepository userRepository;
    private final SuppliersExtendRepository suppliersExtendRepository;
    private final SuppliersMapper suppliersMapper;
    private final BarcodeTypesService barcodeTypesService;
    private final BarcodeTypesQueryService barcodeTypesQueryService;
    private final BarcodeTypesMapper barcodeTypesMapper;
    private final ProductAttributeService productAttributeService;
    private final ProductAttributeQueryService productAttributeQueryService;
    private final ProductAttributeMapper productAttributeMapper;
    private final ProductOptionService productOptionService;
    private final ProductOptionQueryService productOptionQueryService;
    private final ProductOptionMapper productOptionMapper;
    private final MaterialsService materialsService;
    private final MaterialsQueryService materialsQueryService;
    private final MaterialsMapper materialsMapper;
    private final ProductBrandService productBrandService;
    private final ProductBrandQueryService productBrandQueryService;
    private final ProductBrandMapper productBrandMapper;
    private final WarrantyTypesService warrantyTypesService;
    private final WarrantyTypesQueryService warrantyTypesQueryService;
    private final WarrantyTypesMapper warrantyTypesMapper;
    private final ProductCategoryQueryService productCategoryQueryService;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryMapper productCategoryMapper;
    private final CultureService cultureService;
    private final CultureQueryService cultureQueryService;
    private final CultureMapper cultureMapper;
    private final UnitMeasureService unitMeasureService;
    private final UnitMeasureQueryService unitMeasureQueryService;
    private final UnitMeasureMapper unitMeasureMapper;
    private final CurrencyService currencyService;
    private final CurrencyQueryService currencyQueryService;
    private final CurrencyMapper currencyMapper;
    private final ProductTagsService productTagsService;
    private final ProductTagsQueryService productTagsQueryService;
    private final ProductTagsMapper productTagsMapper;
    private final CustomersExtendRepository customersExtendRepository;
    private final DeliveryMethodsService deliveryMethodsService;
    private final DeliveryMethodsMapper deliveryMethodsMapper;
    private final DeliveryMethodsQueryService deliveryMethodsQueryService;
    private final PaymentMethodsService paymentMethodsService;
    private final PaymentMethodsMapper paymentMethodsMapper;
    private final PaymentMethodsQueryService paymentMethodsQueryService;
    private final TaxService taxService;
    private final TaxMapper taxMapper;
    private final TaxQueryService taxQueryService;
    private final OrderPackagesService orderPackagesService;
    private final OrderPackagesMapper orderPackagesMapper;
    private final OrderPackagesQueryService orderPackagesQueryService;
    private final ZoneService zoneService;
    private final ZoneMapper zoneMapper;
    private final ZoneQueryService zoneQueryService;
    private final CitiesService citiesService;
    private final CitiesMapper citiesMapper;
    private final CitiesQueryService citiesQueryService;

    public CommonServiceImpl(PeopleExtendRepository peopleExtendRepository, UserRepository userRepository, SuppliersExtendRepository suppliersExtendRepository, SuppliersMapper suppliersMapper, BarcodeTypesService barcodeTypesService, BarcodeTypesQueryService barcodeTypesQueryService, BarcodeTypesMapper barcodeTypesMapper, ProductAttributeService productAttributeService, ProductAttributeQueryService productAttributeQueryService, ProductAttributeMapper productAttributeMapper, ProductOptionService productOptionService, ProductOptionQueryService productOptionQueryService, ProductOptionMapper productOptionMapper, MaterialsService materialsService, MaterialsQueryService materialsQueryService, MaterialsMapper materialsMapper, ProductBrandService productBrandService, ProductBrandQueryService productBrandQueryService, ProductBrandMapper productBrandMapper, WarrantyTypesService warrantyTypesService, WarrantyTypesQueryService warrantyTypesQueryService, WarrantyTypesMapper warrantyTypesMapper, ProductCategoryQueryService productCategoryQueryService, ProductCategoryService productCategoryService, ProductCategoryMapper productCategoryMapper, CultureService cultureService, CultureQueryService cultureQueryService, CultureMapper cultureMapper, UnitMeasureService unitMeasureService, UnitMeasureQueryService unitMeasureQueryService, UnitMeasureMapper unitMeasureMapper, CurrencyService currencyService, CurrencyQueryService currencyQueryService, CurrencyMapper currencyMapper, ProductTagsService productTagsService, ProductTagsQueryService productTagsQueryService, ProductTagsMapper productTagsMapper, CustomersExtendRepository customersExtendRepository, DeliveryMethodsService deliveryMethodsService, DeliveryMethodsMapper deliveryMethodsMapper, DeliveryMethodsQueryService deliveryMethodsQueryService, PaymentMethodsService paymentMethodsService, PaymentMethodsMapper paymentMethodsMapper, PaymentMethodsQueryService paymentMethodsQueryService, TaxService taxService, TaxMapper taxMapper, TaxQueryService taxQueryService, OrderPackagesService orderPackagesService, OrderPackagesMapper orderPackagesMapper, OrderPackagesQueryService orderPackagesQueryService, ZoneService zoneService, ZoneMapper zoneMapper, ZoneQueryService zoneQueryService, CitiesService citiesService, CitiesMapper citiesMapper, CitiesQueryService citiesQueryService) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.userRepository = userRepository;
        this.suppliersExtendRepository = suppliersExtendRepository;
        this.suppliersMapper = suppliersMapper;
        this.barcodeTypesService = barcodeTypesService;
        this.barcodeTypesQueryService = barcodeTypesQueryService;
        this.barcodeTypesMapper = barcodeTypesMapper;
        this.productAttributeService = productAttributeService;
        this.productAttributeQueryService = productAttributeQueryService;
        this.productAttributeMapper = productAttributeMapper;
        this.productOptionService = productOptionService;
        this.productOptionQueryService = productOptionQueryService;
        this.productOptionMapper = productOptionMapper;
        this.materialsService = materialsService;
        this.materialsQueryService = materialsQueryService;
        this.materialsMapper = materialsMapper;
        this.productBrandService = productBrandService;
        this.productBrandQueryService = productBrandQueryService;
        this.productBrandMapper = productBrandMapper;
        this.warrantyTypesService = warrantyTypesService;
        this.warrantyTypesQueryService = warrantyTypesQueryService;
        this.warrantyTypesMapper = warrantyTypesMapper;
        this.productCategoryQueryService = productCategoryQueryService;
        this.productCategoryService = productCategoryService;
        this.productCategoryMapper = productCategoryMapper;
        this.cultureService = cultureService;
        this.cultureQueryService = cultureQueryService;
        this.cultureMapper = cultureMapper;
        this.unitMeasureService = unitMeasureService;
        this.unitMeasureQueryService = unitMeasureQueryService;
        this.unitMeasureMapper = unitMeasureMapper;
        this.currencyService = currencyService;
        this.currencyQueryService = currencyQueryService;
        this.currencyMapper = currencyMapper;
        this.productTagsService = productTagsService;
        this.productTagsQueryService = productTagsQueryService;
        this.productTagsMapper = productTagsMapper;
        this.customersExtendRepository = customersExtendRepository;
        this.deliveryMethodsService = deliveryMethodsService;
        this.deliveryMethodsMapper = deliveryMethodsMapper;
        this.deliveryMethodsQueryService = deliveryMethodsQueryService;
        this.paymentMethodsService = paymentMethodsService;
        this.paymentMethodsMapper = paymentMethodsMapper;
        this.paymentMethodsQueryService = paymentMethodsQueryService;
        this.taxService = taxService;
        this.taxMapper = taxMapper;
        this.taxQueryService = taxQueryService;
        this.orderPackagesService = orderPackagesService;
        this.orderPackagesMapper = orderPackagesMapper;
        this.orderPackagesQueryService = orderPackagesQueryService;
        this.zoneService = zoneService;
        this.zoneMapper = zoneMapper;
        this.zoneQueryService = zoneQueryService;
        this.citiesService = citiesService;
        this.citiesMapper = citiesMapper;
        this.citiesQueryService = citiesQueryService;
    }

    @Override
    public BarcodeTypesDTO getBarCodesType(String filter) {
        try {
            BarcodeTypesCriteria barcodeTypesCriteria = new BarcodeTypesCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            barcodeTypesCriteria.setName(stringFilter);
            return barcodeTypesQueryService.findByCriteria(barcodeTypesCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public BarcodeTypes getBarCodesTypeEntity(Long id, String filter) {
        try {
            BarcodeTypesDTO barcodeTypesDTO = id != null ? barcodeTypesService.findOne(id).get():getBarCodesType(filter);
            return barcodeTypesMapper.toEntity(barcodeTypesDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductAttributeDTO getProductAttribute(String filter) {
        try {
            ProductAttributeCriteria productAttributeCriteria = new ProductAttributeCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productAttributeCriteria.setValue(stringFilter);
            return productAttributeQueryService.findByCriteria(productAttributeCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductAttribute getProductAttributeEntity(Long id, String filter) {
        try {
            ProductAttributeDTO productAttributeDTO = id != null ? productAttributeService.findOne(id).get():getProductAttribute(filter);
            return productAttributeMapper.toEntity(productAttributeDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductOptionDTO getProductOption(String filter) {
        try {
            ProductOptionCriteria productOptionCriteria = new ProductOptionCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productOptionCriteria.setValue(stringFilter);
            return productOptionQueryService.findByCriteria(productOptionCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductOption getProductOptionEntity(Long id, String filter) {
        try {
            ProductOptionDTO productOptionDTO = id != null ? productOptionService.findOne(id).get():getProductOption(filter);
            return productOptionMapper.toEntity(productOptionDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public MaterialsDTO getMaterials(String filter) {
        try {
            MaterialsCriteria materialsCriteria = new MaterialsCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            materialsCriteria.setName(stringFilter);
            return materialsQueryService.findByCriteria(materialsCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Materials getMaterialsEntity(Long id, String filter) {
        try {
            MaterialsDTO materialsDTO = id != null ? materialsService.findOne(id).get():getMaterials(filter);
            return materialsMapper.toEntity(materialsDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductBrandDTO getProductBrands(String filter) {
        try {
            ProductBrandCriteria productBrandCriteria = new ProductBrandCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productBrandCriteria.setName(stringFilter);
            return productBrandQueryService.findByCriteria(productBrandCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductBrand getProductBrandsEntity(Long id, String filter) {
        try {
            ProductBrandDTO productBrandDTO = id != null ? productBrandService.findOne(id).get():getProductBrands(filter);
            return productBrandMapper.toEntity(productBrandDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public WarrantyTypesDTO getWarrantyTypes(String filter) {
        try {
            WarrantyTypesCriteria warrantyTypesCriteria = new WarrantyTypesCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            warrantyTypesCriteria.setName(stringFilter);
            return warrantyTypesQueryService.findByCriteria(warrantyTypesCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public WarrantyTypes getWarrantyTypesEntity(Long id,String filter) {
        try {
            WarrantyTypesDTO warrantyTypesDTO = id != null ? warrantyTypesService.findOne(id).get():getWarrantyTypes(filter);
            return warrantyTypesMapper.toEntity(warrantyTypesDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductCategoryDTO getProductCategory(String filter) {
        try {
            ProductCategoryCriteria productCategoryCriteria = new ProductCategoryCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            productCategoryCriteria.setName(stringFilter);
            return productCategoryQueryService.findByCriteria(productCategoryCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductCategory getProductCategoryEntity(Long id,String filter) {
        try {
            ProductCategoryDTO  productCategoryDTO = id != null ? productCategoryService.findOne(id).get():getProductCategory(filter);
            return productCategoryMapper.toEntity(productCategoryDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public CultureDTO getCulture(String filter) {
        try {
            CultureCriteria cultureCriteria = new CultureCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            cultureCriteria.setName(stringFilter);
            return cultureQueryService.findByCriteria(cultureCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Culture getCultureEntity(Long id,String filter) {
        try {
            CultureDTO cultureDTO = id != null ? cultureService.findOne(id).get():getCulture(filter);
            return cultureMapper.toEntity(cultureDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public UnitMeasureDTO getUnitMeasure(String filter) {
        try {
            UnitMeasureCriteria unitMeasureCriteria = new UnitMeasureCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            unitMeasureCriteria.setName(stringFilter);
            return unitMeasureQueryService.findByCriteria(unitMeasureCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public UnitMeasure getUnitMeasureEntity(Long id,String filter) {
        try {
            UnitMeasureDTO unitMeasureDTO = id != null ? unitMeasureService.findOne(id).get():getUnitMeasure(filter);
            return unitMeasureMapper.toEntity(unitMeasureDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public CurrencyDTO getCurrency(String filter) {
        try {
            CurrencyCriteria currencyCriteria = new CurrencyCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setContains(filter.trim());
            currencyCriteria.setCode(stringFilter);
            return currencyQueryService.findByCriteria(currencyCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Currency getCurrencyEntity(Long id,String filter) {
        try {
            CurrencyDTO currencyDTO = id != null ? currencyService.findOne(id).get():getCurrency(filter);
            return currencyMapper.toEntity(currencyDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductTagsDTO getProductTags(String filter) {
        try {
            ProductTagsCriteria productTagsCriteria = new ProductTagsCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            productTagsCriteria.setName(stringFilter);
            return productTagsQueryService.findByCriteria(productTagsCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ProductTags getProductTagsEntity(Long id,String filter) {
        try {
            ProductTagsDTO productTagsDTO = id != null ? productTagsService.findOne(id).get():getProductTags(filter);
            return productTagsMapper.toEntity(productTagsDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public DeliveryMethodsDTO getDeliveryMethods(String filter) {
        try {
            DeliveryMethodsCriteria deliveryMethodsCriteria = new DeliveryMethodsCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            deliveryMethodsCriteria.setName(stringFilter);
            return deliveryMethodsQueryService.findByCriteria(deliveryMethodsCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public DeliveryMethods getDeliveryMethodsEntity(Long id,String filter) {
        try {
            DeliveryMethodsDTO deliveryMethodsDTO = id != null ? deliveryMethodsService.findOne(id).get():getDeliveryMethods(filter);
            return deliveryMethodsMapper.toEntity(deliveryMethodsDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public PaymentMethodsDTO getPaymentMethods(String filter) {
        try {
            PaymentMethodsCriteria paymentMethodsCriteria = new PaymentMethodsCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            paymentMethodsCriteria.setName(stringFilter);
            return paymentMethodsQueryService.findByCriteria(paymentMethodsCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public PaymentMethods getPaymentMethodsEntity(Long id,String filter) {
        try {
            PaymentMethodsDTO paymentMethodsDTO = id != null ? paymentMethodsService.findOne(id).get():getPaymentMethods(filter);
            return paymentMethodsMapper.toEntity(paymentMethodsDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public TaxDTO getTaxByCode(String filter) {
        try {
            TaxCriteria taxCriteria = new TaxCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            taxCriteria.setCode(stringFilter);
            return taxQueryService.findByCriteria(taxCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Tax getTaxByCodeEntity(Long id,String filter) {
        try {
            TaxDTO taxDTO = id != null ? taxService.findOne(id).get():getTaxByCode(filter);
            return taxMapper.toEntity(taxDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ZoneDTO getZone(String filter) {
        try {
            ZoneCriteria zoneCriteria = new ZoneCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            zoneCriteria.setCode(stringFilter);
            return zoneQueryService.findByCriteria(zoneCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Zone getZoneEntity(Long id,String filter) {
        try {
            ZoneDTO zoneDTO = id != null ? zoneService.findOne(id).get():getZone(filter);
            return zoneMapper.toEntity(zoneDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public CitiesDTO getCities(String filter) {
        try {
            CitiesCriteria citiesCriteria = new CitiesCriteria();
            StringFilter stringFilter = new StringFilter();
            stringFilter.setEquals(filter.trim());
            citiesCriteria.setName(stringFilter);
            return citiesQueryService.findByCriteria(citiesCriteria).get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Cities getCitiesEntity(Long id,String filter) {
        try {
            CitiesDTO citiesDTO = id != null ? citiesService.findOne(id).get():getCities(filter);
            return citiesMapper.toEntity(citiesDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public People getPeopleByPrincipal(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByUserId(principal.getName());
        if (!people.isPresent()) {
//            throw new IllegalArgumentException("People not found");
            return null;
        }
        return people.get();
    }

    @Override
    public Suppliers getSupplierByPrincipal(Principal principal) {
        People people = getPeopleByPrincipal(principal);

//        return suppliersExtendRepository.findSuppliersByPeopleId(people.getId());
        return (Suppliers)people.getSuppliers().toArray()[0];
    }

    @Override
    public Customers getCustomerByPrincipal(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        People people = getPeopleByPrincipal(principal);

        Customers customer = customersExtendRepository.findCustomersByPeopleId(people.getId());

        return customer;
    }

//    @Override
//    public JSONArray getProductDetails(Products product){
//        JSONArray stockItemArray = new JSONArray();
//
//        for (StockItems i : product.getStockItemLists()) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("stockItemId",i.getId());
//            jsonObject.put("thumbnail",i.getThumbnailUrl());
//            jsonObject.put("unitPrice",i.getUnitPrice());
//            jsonObject.put("recommendedRetailPrice",i.getRecommendedRetailPrice());
//            stockItemArray.add(jsonObject);
//        }
//        return stockItemArray;
//    }

    @Override
    public JSONArray getCartDetails(ShoppingCarts cart){
        JSONArray cartItemList = new JSONArray();
        for (ShoppingCartItems i : cart.getCartItemLists()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cartItemId",i.getId());
            jsonObject.put("selectOrder",i.isSelectOrder());
            jsonObject.put("stockItemId",i.getStockItem().getId());
            jsonObject.put("stockItemName",i.getStockItem().getName());
            jsonObject.put("productId",i.getStockItem().getProduct().getId());
            jsonObject.put("productName",i.getStockItem().getProduct().getName());
            jsonObject.put("supplierId",i.getStockItem().getProduct().getSupplier().getId());
            jsonObject.put("supplierName",i.getStockItem().getProduct().getSupplier().getName());
            jsonObject.put("quantity",i.getQuantity());
            jsonObject.put("unitPrice",i.getStockItem().getUnitPrice());
            jsonObject.put("quantityOnHand",i.getStockItem().getQuantityOnHand());
            jsonObject.put("photo",i.getStockItem().getThumbnailPhoto());
            cartItemList.add(jsonObject);
        }
        return cartItemList;
    }

    @Override
    public JSONObject getOrderDetails(Orders order){
        JSONObject orderObject = new JSONObject();
        JSONArray orderPackageArray = new JSONArray();
        JSONArray orderLineArray;

        orderObject.put("orderId", order.getId());
        orderObject.put("orderDate", order.getOrderDate());
        orderObject.put("orderStatus", order.getStatus());

        for(OrderPackages i:order.getOrderPackageLists()){
            JSONObject orderPackageObject = new JSONObject();

            orderLineArray = new JSONArray();
            for(OrderLines j:i.getOrderLineLists()){
                JSONObject orderLineObject = new JSONObject();
                orderLineObject.put("orderLineId",j.getId());
                orderLineObject.put("stockItemName",j.getStockItem().getName());
                orderLineObject.put("imageBlob",j.getStockItem().getThumbnailPhoto());
                orderLineObject.put("quantity",j.getQuantity());
                orderLineObject.put("unitPrice",j.getStockItem().getUnitPrice());
                orderLineObject.put("reviewPhoto",j.getReviewPhoto());
//                if(j.getReviewImage() != null){
//                    orderLineObject.put("reviewImageId",j.getReviewImage().getId());
//                    orderLineObject.put("reviewImageThumbnailUrl",j.getReviewImage().getThumbnailUrl());
//                }

                if(j.getLineRating() != null){
                    orderLineObject.put("lineRating",j.getLineRating());
                }

                if(j.getLineReview() != null){
                    orderLineObject.put("lineReview",j.getLineReview());
                }

                if(j.getCustomerReviewedOn() != null){
                    orderLineObject.put("customerReviewedOn",j.getCustomerReviewedOn().toString());
                }

                orderLineArray.add(orderLineObject);
            }

            orderPackageObject.put("supplierId",i.getSupplier().getId());
            orderPackageObject.put("supplierName",i.getSupplier().getName());
            orderPackageObject.put("customerReviewedOn",i.getCustomerReviewedOn().toString());
            orderPackageObject.put("sellerRating",i.getSellerRating());
            orderPackageObject.put("sellerReview",i.getSellerReview());
            orderPackageObject.put("deliveryRating",i.getDeliveryRating());
            orderPackageObject.put("deliveryReview",i.getDeliveryReview());
            orderPackageObject.put("reviewAsAnonymous",i.isReviewAsAnonymous());
            orderPackageObject.put("completedReview",i.isCompletedReview());

            orderPackageObject.put("children",orderLineArray);
            orderPackageArray.add(orderPackageObject);
        }

        return orderObject;
    }
}
