package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.StockItems} entity.
 */
public class StockItemsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String handle;

    private String vendorCode;

    private String vendorSKU;

    private String generatedSKU;

    private String barcode;

    private BigDecimal taxRate;

    @NotNull
    private BigDecimal unitPrice;

    private BigDecimal recommendedRetailPrice;

    private BigDecimal typicalWeightPerUnit;

    @NotNull
    private Integer quantityOnHand;

    private String shelf;

    private String bin;

    private Integer lastStockTakeQuantity;

    @NotNull
    private BigDecimal lastCostPrice;

    private Integer reorderLevel;

    private Integer targetStockLevel;

    private Integer leadTimeDays;

    private Integer quantityPerOuter;

    @NotNull
    private Boolean isChillerStock;

    private Integer itemLength;

    private Integer itemWidth;

    private Integer itemHeight;

    private BigDecimal itemWeight;

    private Integer itemPackageLength;

    private Integer itemPackageWidth;

    private Integer itemPackageHeight;

    private BigDecimal itemPackageWeight;

    private Integer noOfPieces;

    private Integer noOfItems;

    private String manufacture;

    private String marketingComments;

    private String internalComments;

    private Instant sellStartDate;

    private Instant sellEndDate;

    private Integer sellCount;

    private String tags;

    private String searchDetails;

    @Lob
    private String customFields;

    private String thumbnailPhoto;

    @NotNull
    private Boolean liveInd;

    @NotNull
    private Boolean cashOnDeliveryInd;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;

    @NotNull
    private Boolean activeFlag;

    @Lob
    private String localization;

    @NotNull
    private Instant validFrom;

    private Instant validTo;


    private Long supplierId;

    private String supplierName;

    private Long itemLengthUnitId;

    private String itemLengthUnitCode;

    private Long itemWidthUnitId;

    private String itemWidthUnitCode;

    private Long itemHeightUnitId;

    private String itemHeightUnitCode;

    private Long packageLengthUnitId;

    private String packageLengthUnitCode;

    private Long packageWidthUnitId;

    private String packageWidthUnitCode;

    private Long packageHeightUnitId;

    private String packageHeightUnitCode;

    private Long itemPackageWeightUnitId;

    private String itemPackageWeightUnitCode;

    private Long productAttributeId;

    private String productAttributeValue;

    private Long productOptionId;

    private String productOptionValue;

    private Long materialId;

    private String materialName;

    private Long currencyId;

    private String currencyCode;

    private Long barcodeTypeId;

    private String barcodeTypeName;

    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorSKU() {
        return vendorSKU;
    }

    public void setVendorSKU(String vendorSKU) {
        this.vendorSKU = vendorSKU;
    }

    public String getGeneratedSKU() {
        return generatedSKU;
    }

    public void setGeneratedSKU(String generatedSKU) {
        this.generatedSKU = generatedSKU;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getRecommendedRetailPrice() {
        return recommendedRetailPrice;
    }

    public void setRecommendedRetailPrice(BigDecimal recommendedRetailPrice) {
        this.recommendedRetailPrice = recommendedRetailPrice;
    }

    public BigDecimal getTypicalWeightPerUnit() {
        return typicalWeightPerUnit;
    }

    public void setTypicalWeightPerUnit(BigDecimal typicalWeightPerUnit) {
        this.typicalWeightPerUnit = typicalWeightPerUnit;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Integer getLastStockTakeQuantity() {
        return lastStockTakeQuantity;
    }

    public void setLastStockTakeQuantity(Integer lastStockTakeQuantity) {
        this.lastStockTakeQuantity = lastStockTakeQuantity;
    }

    public BigDecimal getLastCostPrice() {
        return lastCostPrice;
    }

    public void setLastCostPrice(BigDecimal lastCostPrice) {
        this.lastCostPrice = lastCostPrice;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getTargetStockLevel() {
        return targetStockLevel;
    }

    public void setTargetStockLevel(Integer targetStockLevel) {
        this.targetStockLevel = targetStockLevel;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public Integer getQuantityPerOuter() {
        return quantityPerOuter;
    }

    public void setQuantityPerOuter(Integer quantityPerOuter) {
        this.quantityPerOuter = quantityPerOuter;
    }

    public Boolean isIsChillerStock() {
        return isChillerStock;
    }

    public void setIsChillerStock(Boolean isChillerStock) {
        this.isChillerStock = isChillerStock;
    }

    public Integer getItemLength() {
        return itemLength;
    }

    public void setItemLength(Integer itemLength) {
        this.itemLength = itemLength;
    }

    public Integer getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(Integer itemWidth) {
        this.itemWidth = itemWidth;
    }

    public Integer getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(Integer itemHeight) {
        this.itemHeight = itemHeight;
    }

    public BigDecimal getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(BigDecimal itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Integer getItemPackageLength() {
        return itemPackageLength;
    }

    public void setItemPackageLength(Integer itemPackageLength) {
        this.itemPackageLength = itemPackageLength;
    }

    public Integer getItemPackageWidth() {
        return itemPackageWidth;
    }

    public void setItemPackageWidth(Integer itemPackageWidth) {
        this.itemPackageWidth = itemPackageWidth;
    }

    public Integer getItemPackageHeight() {
        return itemPackageHeight;
    }

    public void setItemPackageHeight(Integer itemPackageHeight) {
        this.itemPackageHeight = itemPackageHeight;
    }

    public BigDecimal getItemPackageWeight() {
        return itemPackageWeight;
    }

    public void setItemPackageWeight(BigDecimal itemPackageWeight) {
        this.itemPackageWeight = itemPackageWeight;
    }

    public Integer getNoOfPieces() {
        return noOfPieces;
    }

    public void setNoOfPieces(Integer noOfPieces) {
        this.noOfPieces = noOfPieces;
    }

    public Integer getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(Integer noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getMarketingComments() {
        return marketingComments;
    }

    public void setMarketingComments(String marketingComments) {
        this.marketingComments = marketingComments;
    }

    public String getInternalComments() {
        return internalComments;
    }

    public void setInternalComments(String internalComments) {
        this.internalComments = internalComments;
    }

    public Instant getSellStartDate() {
        return sellStartDate;
    }

    public void setSellStartDate(Instant sellStartDate) {
        this.sellStartDate = sellStartDate;
    }

    public Instant getSellEndDate() {
        return sellEndDate;
    }

    public void setSellEndDate(Instant sellEndDate) {
        this.sellEndDate = sellEndDate;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getCustomFields() {
        return customFields;
    }

    public void setCustomFields(String customFields) {
        this.customFields = customFields;
    }

    public String getThumbnailPhoto() {
        return thumbnailPhoto;
    }

    public void setThumbnailPhoto(String thumbnailPhoto) {
        this.thumbnailPhoto = thumbnailPhoto;
    }

    public Boolean isLiveInd() {
        return liveInd;
    }

    public void setLiveInd(Boolean liveInd) {
        this.liveInd = liveInd;
    }

    public Boolean isCashOnDeliveryInd() {
        return cashOnDeliveryInd;
    }

    public void setCashOnDeliveryInd(Boolean cashOnDeliveryInd) {
        this.cashOnDeliveryInd = cashOnDeliveryInd;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long suppliersId) {
        this.supplierId = suppliersId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String suppliersName) {
        this.supplierName = suppliersName;
    }

    public Long getItemLengthUnitId() {
        return itemLengthUnitId;
    }

    public void setItemLengthUnitId(Long unitMeasureId) {
        this.itemLengthUnitId = unitMeasureId;
    }

    public String getItemLengthUnitCode() {
        return itemLengthUnitCode;
    }

    public void setItemLengthUnitCode(String unitMeasureCode) {
        this.itemLengthUnitCode = unitMeasureCode;
    }

    public Long getItemWidthUnitId() {
        return itemWidthUnitId;
    }

    public void setItemWidthUnitId(Long unitMeasureId) {
        this.itemWidthUnitId = unitMeasureId;
    }

    public String getItemWidthUnitCode() {
        return itemWidthUnitCode;
    }

    public void setItemWidthUnitCode(String unitMeasureCode) {
        this.itemWidthUnitCode = unitMeasureCode;
    }

    public Long getItemHeightUnitId() {
        return itemHeightUnitId;
    }

    public void setItemHeightUnitId(Long unitMeasureId) {
        this.itemHeightUnitId = unitMeasureId;
    }

    public String getItemHeightUnitCode() {
        return itemHeightUnitCode;
    }

    public void setItemHeightUnitCode(String unitMeasureCode) {
        this.itemHeightUnitCode = unitMeasureCode;
    }

    public Long getPackageLengthUnitId() {
        return packageLengthUnitId;
    }

    public void setPackageLengthUnitId(Long unitMeasureId) {
        this.packageLengthUnitId = unitMeasureId;
    }

    public String getPackageLengthUnitCode() {
        return packageLengthUnitCode;
    }

    public void setPackageLengthUnitCode(String unitMeasureCode) {
        this.packageLengthUnitCode = unitMeasureCode;
    }

    public Long getPackageWidthUnitId() {
        return packageWidthUnitId;
    }

    public void setPackageWidthUnitId(Long unitMeasureId) {
        this.packageWidthUnitId = unitMeasureId;
    }

    public String getPackageWidthUnitCode() {
        return packageWidthUnitCode;
    }

    public void setPackageWidthUnitCode(String unitMeasureCode) {
        this.packageWidthUnitCode = unitMeasureCode;
    }

    public Long getPackageHeightUnitId() {
        return packageHeightUnitId;
    }

    public void setPackageHeightUnitId(Long unitMeasureId) {
        this.packageHeightUnitId = unitMeasureId;
    }

    public String getPackageHeightUnitCode() {
        return packageHeightUnitCode;
    }

    public void setPackageHeightUnitCode(String unitMeasureCode) {
        this.packageHeightUnitCode = unitMeasureCode;
    }

    public Long getItemPackageWeightUnitId() {
        return itemPackageWeightUnitId;
    }

    public void setItemPackageWeightUnitId(Long unitMeasureId) {
        this.itemPackageWeightUnitId = unitMeasureId;
    }

    public String getItemPackageWeightUnitCode() {
        return itemPackageWeightUnitCode;
    }

    public void setItemPackageWeightUnitCode(String unitMeasureCode) {
        this.itemPackageWeightUnitCode = unitMeasureCode;
    }

    public Long getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Long productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductAttributeValue() {
        return productAttributeValue;
    }

    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(String productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialsId) {
        this.materialId = materialsId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialsName) {
        this.materialName = materialsName;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getBarcodeTypeId() {
        return barcodeTypeId;
    }

    public void setBarcodeTypeId(Long barcodeTypesId) {
        this.barcodeTypeId = barcodeTypesId;
    }

    public String getBarcodeTypeName() {
        return barcodeTypeName;
    }

    public void setBarcodeTypeName(String barcodeTypesName) {
        this.barcodeTypeName = barcodeTypesName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productsId) {
        this.productId = productsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItemsDTO)) {
            return false;
        }

        return id != null && id.equals(((StockItemsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockItemsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            ", vendorCode='" + getVendorCode() + "'" +
            ", vendorSKU='" + getVendorSKU() + "'" +
            ", generatedSKU='" + getGeneratedSKU() + "'" +
            ", barcode='" + getBarcode() + "'" +
            ", taxRate=" + getTaxRate() +
            ", unitPrice=" + getUnitPrice() +
            ", recommendedRetailPrice=" + getRecommendedRetailPrice() +
            ", typicalWeightPerUnit=" + getTypicalWeightPerUnit() +
            ", quantityOnHand=" + getQuantityOnHand() +
            ", shelf='" + getShelf() + "'" +
            ", bin='" + getBin() + "'" +
            ", lastStockTakeQuantity=" + getLastStockTakeQuantity() +
            ", lastCostPrice=" + getLastCostPrice() +
            ", reorderLevel=" + getReorderLevel() +
            ", targetStockLevel=" + getTargetStockLevel() +
            ", leadTimeDays=" + getLeadTimeDays() +
            ", quantityPerOuter=" + getQuantityPerOuter() +
            ", isChillerStock='" + isIsChillerStock() + "'" +
            ", itemLength=" + getItemLength() +
            ", itemWidth=" + getItemWidth() +
            ", itemHeight=" + getItemHeight() +
            ", itemWeight=" + getItemWeight() +
            ", itemPackageLength=" + getItemPackageLength() +
            ", itemPackageWidth=" + getItemPackageWidth() +
            ", itemPackageHeight=" + getItemPackageHeight() +
            ", itemPackageWeight=" + getItemPackageWeight() +
            ", noOfPieces=" + getNoOfPieces() +
            ", noOfItems=" + getNoOfItems() +
            ", manufacture='" + getManufacture() + "'" +
            ", marketingComments='" + getMarketingComments() + "'" +
            ", internalComments='" + getInternalComments() + "'" +
            ", sellStartDate='" + getSellStartDate() + "'" +
            ", sellEndDate='" + getSellEndDate() + "'" +
            ", sellCount=" + getSellCount() +
            ", tags='" + getTags() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", customFields='" + getCustomFields() + "'" +
            ", thumbnailPhoto='" + getThumbnailPhoto() + "'" +
            ", liveInd='" + isLiveInd() + "'" +
            ", cashOnDeliveryInd='" + isCashOnDeliveryInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", supplierId=" + getSupplierId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", itemLengthUnitId=" + getItemLengthUnitId() +
            ", itemLengthUnitCode='" + getItemLengthUnitCode() + "'" +
            ", itemWidthUnitId=" + getItemWidthUnitId() +
            ", itemWidthUnitCode='" + getItemWidthUnitCode() + "'" +
            ", itemHeightUnitId=" + getItemHeightUnitId() +
            ", itemHeightUnitCode='" + getItemHeightUnitCode() + "'" +
            ", packageLengthUnitId=" + getPackageLengthUnitId() +
            ", packageLengthUnitCode='" + getPackageLengthUnitCode() + "'" +
            ", packageWidthUnitId=" + getPackageWidthUnitId() +
            ", packageWidthUnitCode='" + getPackageWidthUnitCode() + "'" +
            ", packageHeightUnitId=" + getPackageHeightUnitId() +
            ", packageHeightUnitCode='" + getPackageHeightUnitCode() + "'" +
            ", itemPackageWeightUnitId=" + getItemPackageWeightUnitId() +
            ", itemPackageWeightUnitCode='" + getItemPackageWeightUnitCode() + "'" +
            ", productAttributeId=" + getProductAttributeId() +
            ", productAttributeValue='" + getProductAttributeValue() + "'" +
            ", productOptionId=" + getProductOptionId() +
            ", productOptionValue='" + getProductOptionValue() + "'" +
            ", materialId=" + getMaterialId() +
            ", materialName='" + getMaterialName() + "'" +
            ", currencyId=" + getCurrencyId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", barcodeTypeId=" + getBarcodeTypeId() +
            ", barcodeTypeName='" + getBarcodeTypeName() + "'" +
            ", productId=" + getProductId() +
            "}";
    }
}
