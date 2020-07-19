package com.vertical.commerce.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vertical.commerce.domain.ProductDocuments} entity.
 */
public class ProductDocumentsDTO implements Serializable {
    
    private Long id;

    private String videoUrl;

    @Lob
    private String cultureDetails;

    @Lob
    private String highlights;

    @Lob
    private String longDescription;

    @Lob
    private String shortDescription;

    @Lob
    private String whatInTheBox;

    @Lob
    private String careInstructions;

    private String productType;

    private String modelName;

    private String modelNumber;

    private String fabricType;

    @Lob
    private String specialFeatures;

    private String productComplianceCertificate;

    private Boolean genuineAndLegal;

    private String countryOfOrigin;

    @Lob
    private String usageAndSideEffects;

    @Lob
    private String safetyWarnning;

    private String warrantyPeriod;

    private String warrantyPolicy;

    private String dangerousGoods;

    @NotNull
    private String lastEditedBy;

    @NotNull
    private Instant lastEditedWhen;


    private Long warrantyTypeId;

    private String warrantyTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getFabricType() {
        return fabricType;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getUsageAndSideEffects() {
        return usageAndSideEffects;
    }

    public void setUsageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
    }

    public String getSafetyWarnning() {
        return safetyWarnning;
    }

    public void setSafetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public String getDangerousGoods() {
        return dangerousGoods;
    }

    public void setDangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
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

    public Long getWarrantyTypeId() {
        return warrantyTypeId;
    }

    public void setWarrantyTypeId(Long warrantyTypesId) {
        this.warrantyTypeId = warrantyTypesId;
    }

    public String getWarrantyTypeName() {
        return warrantyTypeName;
    }

    public void setWarrantyTypeName(String warrantyTypesName) {
        this.warrantyTypeName = warrantyTypesName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDocumentsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDocumentsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDocumentsDTO{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", highlights='" + getHighlights() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", whatInTheBox='" + getWhatInTheBox() + "'" +
            ", careInstructions='" + getCareInstructions() + "'" +
            ", productType='" + getProductType() + "'" +
            ", modelName='" + getModelName() + "'" +
            ", modelNumber='" + getModelNumber() + "'" +
            ", fabricType='" + getFabricType() + "'" +
            ", specialFeatures='" + getSpecialFeatures() + "'" +
            ", productComplianceCertificate='" + getProductComplianceCertificate() + "'" +
            ", genuineAndLegal='" + isGenuineAndLegal() + "'" +
            ", countryOfOrigin='" + getCountryOfOrigin() + "'" +
            ", usageAndSideEffects='" + getUsageAndSideEffects() + "'" +
            ", safetyWarnning='" + getSafetyWarnning() + "'" +
            ", warrantyPeriod='" + getWarrantyPeriod() + "'" +
            ", warrantyPolicy='" + getWarrantyPolicy() + "'" +
            ", dangerousGoods='" + getDangerousGoods() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", warrantyTypeId=" + getWarrantyTypeId() +
            ", warrantyTypeName='" + getWarrantyTypeName() + "'" +
            "}";
    }
}
