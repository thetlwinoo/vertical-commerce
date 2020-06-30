package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.CurrencyRepository;
import com.vertical.commerce.repository.ProductsRepository;
import com.vertical.commerce.repository.StockItemsExtendRepository;
import com.vertical.commerce.repository.StockItemsRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.ProductsExtendService;
import com.vertical.commerce.service.StockItemsExtendService;
import com.vertical.commerce.service.StockItemsQueryService;
import com.vertical.commerce.service.dto.StockItemsCriteria;
import com.vertical.commerce.service.dto.StockItemsDTO;
import com.vertical.commerce.service.mapper.StockItemsMapper;
import io.github.jhipster.service.filter.LongFilter;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class StockItemsExtendServiceImpl implements StockItemsExtendService {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendServiceImpl.class);
    private final StockItemsRepository stockItemsRepository;
    private final StockItemsExtendRepository stockItemsExtendRepository;
    private final StockItemsMapper stockItemsMapper;
    private final ProductsRepository productsRepository;
    private final CurrencyRepository currencyRepository;
    private final ProductsExtendService productsExtendService;
    private final StockItemsQueryService stockItemsQueryService;
    private final CommonService commonService;

    public StockItemsExtendServiceImpl(StockItemsRepository stockItemsRepository, StockItemsExtendRepository stockItemsExtendRepository, StockItemsMapper stockItemsMapper, ProductsRepository productsRepository, CurrencyRepository currencyRepository, ProductsExtendService productsExtendService, StockItemsQueryService stockItemsQueryService, CommonService commonService) {
        this.stockItemsRepository = stockItemsRepository;
        this.stockItemsExtendRepository = stockItemsExtendRepository;
        this.stockItemsMapper = stockItemsMapper;
        this.productsRepository = productsRepository;
        this.currencyRepository = currencyRepository;
        this.productsExtendService = productsExtendService;
        this.stockItemsQueryService = stockItemsQueryService;
        this.commonService = commonService;
    }

    @Override
    public StockItemsDTO importStockItems(StockItemsDTO stockItemsDTO, Principal principal){
        try{
            People people = commonService.getPeopleByPrincipal(principal);

            StockItems saveStockItems = new StockItems();
            saveStockItems.setId(stockItemsDTO.getId());
            saveStockItems.setName(stockItemsDTO.getName());
            saveStockItems.setVendorCode(stockItemsDTO.getVendorCode());
            saveStockItems.setVendorSKU(stockItemsDTO.getVendorSKU());
            saveStockItems.setBarcode(stockItemsDTO.getBarcode());
            BarcodeTypes barcodeTypes = commonService.getBarCodesTypeEntity(stockItemsDTO.getBarcodeTypeId(),stockItemsDTO.getBarcodeTypeName());
            saveStockItems.setBarcodeType(barcodeTypes);
            saveStockItems.setUnitPrice(stockItemsDTO.getUnitPrice());
            saveStockItems.setRecommendedRetailPrice(stockItemsDTO.getRecommendedRetailPrice());
            ProductAttribute productAttribute =commonService.getProductAttributeEntity(stockItemsDTO.getProductAttributeId(),stockItemsDTO.getProductAttributeValue());
            saveStockItems.setProductAttribute(productAttribute);
            ProductOption productOption = commonService.getProductOptionEntity(stockItemsDTO.getProductOptionId(),stockItemsDTO.getProductOptionValue());
            saveStockItems.setProductOption(productOption);
            saveStockItems.setQuantityOnHand(stockItemsDTO.getQuantityOnHand());
            saveStockItems.setItemLength(stockItemsDTO.getItemLength());
            UnitMeasure itemLengthUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getItemLengthUnitId(),stockItemsDTO.getItemLengthUnitCode());
            saveStockItems.setItemLengthUnit(itemLengthUnit);
            saveStockItems.setItemWidth(stockItemsDTO.getItemWidth());
            UnitMeasure itemWidthUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getItemWidthUnitId(),stockItemsDTO.getItemWidthUnitCode());
            saveStockItems.setItemWidthUnit(itemWidthUnit);
            saveStockItems.setItemHeight(stockItemsDTO.getItemHeight());
            UnitMeasure itemHeightUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getItemHeightUnitId(),stockItemsDTO.getItemHeightUnitCode());
            saveStockItems.setItemHeightUnit(itemHeightUnit);
            saveStockItems.setItemWeight(stockItemsDTO.getItemWeight());
            UnitMeasure itemPackageWeightUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getItemPackageWeightUnitId(),stockItemsDTO.getItemPackageWeightUnitCode());
            saveStockItems.setItemPackageWeightUnit(itemPackageWeightUnit);
            saveStockItems.setItemPackageLength(stockItemsDTO.getItemPackageLength());
            saveStockItems.setItemPackageWidth(stockItemsDTO.getItemPackageWidth());
            saveStockItems.setItemPackageHeight(stockItemsDTO.getItemPackageHeight());
            UnitMeasure packageLengthUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getPackageLengthUnitId(),stockItemsDTO.getPackageLengthUnitCode());
            saveStockItems.setPackageLengthUnit(packageLengthUnit);
            UnitMeasure packageWidthUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getPackageWidthUnitId(),stockItemsDTO.getPackageWidthUnitCode());
            saveStockItems.setPackageWidthUnit(packageWidthUnit);
            UnitMeasure packageHeightUnit = commonService.getUnitMeasureEntity(stockItemsDTO.getPackageHeightUnitId(),stockItemsDTO.getPackageHeightUnitCode());
            saveStockItems.setPackageHeightUnit(packageHeightUnit);
            saveStockItems.setItemPackageWeight(stockItemsDTO.getItemPackageWeight());
            saveStockItems.setNoOfPieces(stockItemsDTO.getNoOfPieces());
            saveStockItems.setNoOfItems(stockItemsDTO.getNoOfItems());
            saveStockItems.setManufacture(stockItemsDTO.getManufacture());
            saveStockItems.setMarketingComments(stockItemsDTO.getMarketingComments());
            saveStockItems.setInternalComments(stockItemsDTO.getInternalComments());
            saveStockItems.setSellStartDate(stockItemsDTO.getSellStartDate());
            saveStockItems.setSellEndDate(stockItemsDTO.getSellEndDate());
            saveStockItems.setCustomFields(stockItemsDTO.getCustomFields());
            saveStockItems.setActiveInd(false);
            saveStockItems.setLiveInd(true);
            saveStockItems.setSellCount(0);
            saveStockItems.setProduct(productsRepository.getOne(stockItemsDTO.getProductId()));
            Materials materials = commonService.getMaterialsEntity(stockItemsDTO.getMaterialId(),stockItemsDTO.getMaterialName());
            saveStockItems.setMaterial(materials);
            saveStockItems.setQuantityOnHand(stockItemsDTO.getQuantityOnHand());
            saveStockItems.setLastCostPrice(stockItemsDTO.getLastCostPrice());
            saveStockItems.setBin(stockItemsDTO.getBin());
            saveStockItems.setShelf(stockItemsDTO.getShelf());
            saveStockItems.setCashOnDeliveryInd(false);
            saveStockItems.setReorderLevel(stockItemsDTO.getReorderLevel());
            saveStockItems.setLastStockTakeQuantity(stockItemsDTO.getLastStockTakeQuantity());
            saveStockItems.targetStockLevel(stockItemsDTO.getTargetStockLevel());
            saveStockItems.setSearchDetails(stockItemsDTO.getSearchDetails());
            saveStockItems.setLastEditedBy(people.getFullName());
            saveStockItems.setLastEditedWhen(Instant.now());
            saveStockItems.setIsChillerStock(stockItemsDTO.isIsChillerStock());
            saveStockItems.setTaxRate(BigDecimal.valueOf(commonService.getTaxByCode("ECT").getRate()));
            Currency currency = commonService.getCurrencyEntity(stockItemsDTO.getCurrencyId(),stockItemsDTO.getCurrencyCode());
            saveStockItems.setCurrency(currency);

            Random random= new Random();
            int pnum = random.nextInt(10000);
            String prefixSKU = "ZZW" + pnum;
            saveStockItems.setGeneratedSKU(prefixSKU);

            saveStockItems = stockItemsRepository.save(saveStockItems);
            return stockItemsMapper.toDto(saveStockItems);
        }
        catch(Exception ex){
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public Page<StockItemsDTO> getAllStockItems(StockItemsCriteria criteria, Pageable pageable, Principal principal){
        Suppliers suppliers = commonService.getSupplierByPrincipal(principal);
        List<Long> productIds = productsExtendService.getProductIdsBySupplier(suppliers.getId());
        if (productIds.size() > 0) {
            LongFilter productIdsFilter = new LongFilter();
            productIdsFilter.setIn(productIds);
            criteria.setProductId(productIdsFilter);
        }

        return stockItemsQueryService.findByCriteria(criteria, pageable);
    }

    @Override
    public JSONObject getStatistics(Principal principal){
        Suppliers suppliers = commonService.getSupplierByPrincipal(principal);
        List<Object[]> statistics = stockItemsExtendRepository.getStatistics(suppliers.getId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("all",statistics.get(0)[0]);
        jsonObject.put("active",statistics.get(0)[1]);
        jsonObject.put("inactive",statistics.get(0)[2]);
        jsonObject.put("soldout",statistics.get(0)[3]);

        return jsonObject;
    }
}
