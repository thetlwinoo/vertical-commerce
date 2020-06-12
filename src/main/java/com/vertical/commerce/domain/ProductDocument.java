package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProductDocument.
 */
@Entity
@Table(name = "product_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "highlights")
    private String highlights;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "long_description")
    private String longDescription;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "what_in_the_box")
    private String whatInTheBox;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "care_instructions")
    private String careInstructions;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(name = "fabric_type")
    private String fabricType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "special_features")
    private String specialFeatures;

    @Column(name = "product_compliance_certificate")
    private String productComplianceCertificate;

    @Column(name = "genuine_and_legal")
    private Boolean genuineAndLegal;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "usage_and_side_effects")
    private String usageAndSideEffects;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "safety_warnning")
    private String safetyWarnning;

    @Column(name = "warranty_period")
    private String warrantyPeriod;

    @Column(name = "warranty_policy")
    private String warrantyPolicy;

    @Column(name = "dangerous_goods")
    private String dangerousGoods;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @ManyToOne
    @JsonIgnoreProperties(value = "productDocuments", allowSetters = true)
    private WarrantyTypes warrantyType;

    @ManyToOne
    @JsonIgnoreProperties(value = "productDocuments", allowSetters = true)
    private Culture culture;

    @OneToOne(mappedBy = "productDocument")
    @JsonIgnore
    private Products product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public ProductDocument videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getHighlights() {
        return highlights;
    }

    public ProductDocument highlights(String highlights) {
        this.highlights = highlights;
        return this;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public ProductDocument longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public ProductDocument shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getWhatInTheBox() {
        return whatInTheBox;
    }

    public ProductDocument whatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
        return this;
    }

    public void setWhatInTheBox(String whatInTheBox) {
        this.whatInTheBox = whatInTheBox;
    }

    public String getCareInstructions() {
        return careInstructions;
    }

    public ProductDocument careInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
        return this;
    }

    public void setCareInstructions(String careInstructions) {
        this.careInstructions = careInstructions;
    }

    public String getProductType() {
        return productType;
    }

    public ProductDocument productType(String productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getModelName() {
        return modelName;
    }

    public ProductDocument modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public ProductDocument modelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
        return this;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getFabricType() {
        return fabricType;
    }

    public ProductDocument fabricType(String fabricType) {
        this.fabricType = fabricType;
        return this;
    }

    public void setFabricType(String fabricType) {
        this.fabricType = fabricType;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public ProductDocument specialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
        return this;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getProductComplianceCertificate() {
        return productComplianceCertificate;
    }

    public ProductDocument productComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
        return this;
    }

    public void setProductComplianceCertificate(String productComplianceCertificate) {
        this.productComplianceCertificate = productComplianceCertificate;
    }

    public Boolean isGenuineAndLegal() {
        return genuineAndLegal;
    }

    public ProductDocument genuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
        return this;
    }

    public void setGenuineAndLegal(Boolean genuineAndLegal) {
        this.genuineAndLegal = genuineAndLegal;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public ProductDocument countryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
        return this;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getUsageAndSideEffects() {
        return usageAndSideEffects;
    }

    public ProductDocument usageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
        return this;
    }

    public void setUsageAndSideEffects(String usageAndSideEffects) {
        this.usageAndSideEffects = usageAndSideEffects;
    }

    public String getSafetyWarnning() {
        return safetyWarnning;
    }

    public ProductDocument safetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
        return this;
    }

    public void setSafetyWarnning(String safetyWarnning) {
        this.safetyWarnning = safetyWarnning;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public ProductDocument warrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
        return this;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPolicy() {
        return warrantyPolicy;
    }

    public ProductDocument warrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
        return this;
    }

    public void setWarrantyPolicy(String warrantyPolicy) {
        this.warrantyPolicy = warrantyPolicy;
    }

    public String getDangerousGoods() {
        return dangerousGoods;
    }

    public ProductDocument dangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
        return this;
    }

    public void setDangerousGoods(String dangerousGoods) {
        this.dangerousGoods = dangerousGoods;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public ProductDocument lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public ProductDocument lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public WarrantyTypes getWarrantyType() {
        return warrantyType;
    }

    public ProductDocument warrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
        return this;
    }

    public void setWarrantyType(WarrantyTypes warrantyTypes) {
        this.warrantyType = warrantyTypes;
    }

    public Culture getCulture() {
        return culture;
    }

    public ProductDocument culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public Products getProduct() {
        return product;
    }

    public ProductDocument product(Products products) {
        this.product = products;
        return this;
    }

    public void setProduct(Products products) {
        this.product = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDocument)) {
            return false;
        }
        return id != null && id.equals(((ProductDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDocument{" +
            "id=" + getId() +
            ", videoUrl='" + getVideoUrl() + "'" +
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
            "}";
    }
}
