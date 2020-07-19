package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Products.
 */
@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "culture_details")
    private String cultureDetails;

    @Column(name = "handle")
    private String handle;

    @Column(name = "search_details")
    private String searchDetails;

    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "product_details")
    private String productDetails;

    @Column(name = "total_wishlist")
    private Integer totalWishlist;

    @Column(name = "overall_rating")
    private Integer overallRating;

    @Column(name = "preferred_ind")
    private Boolean preferredInd;

    @Column(name = "free_shipping_ind")
    private Boolean freeShippingInd;

    @Column(name = "made_in_myanmar_ind")
    private Boolean madeInMyanmarInd;

    @Column(name = "questions_about_product_ind")
    private Boolean questionsAboutProductInd;

    @NotNull
    @Column(name = "release_date", nullable = false)
    private Instant releaseDate;

    @NotNull
    @Column(name = "available_date", nullable = false)
    private Instant availableDate;

    @NotNull
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @OneToOne
    @JoinColumn(unique = true)
    private ProductDocuments productDocument;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<StockItems> stockItemLists = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Suppliers supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private ProductBrand productBrand;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Products name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCultureDetails() {
        return cultureDetails;
    }

    public Products cultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
        return this;
    }

    public void setCultureDetails(String cultureDetails) {
        this.cultureDetails = cultureDetails;
    }

    public String getHandle() {
        return handle;
    }

    public Products handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getSearchDetails() {
        return searchDetails;
    }

    public Products searchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
        return this;
    }

    public void setSearchDetails(String searchDetails) {
        this.searchDetails = searchDetails;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public Products productNumber(String productNumber) {
        this.productNumber = productNumber;
        return this;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public Products sellCount(Integer sellCount) {
        this.sellCount = sellCount;
        return this;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public Products productDetails(String productDetails) {
        this.productDetails = productDetails;
        return this;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public Integer getTotalWishlist() {
        return totalWishlist;
    }

    public Products totalWishlist(Integer totalWishlist) {
        this.totalWishlist = totalWishlist;
        return this;
    }

    public void setTotalWishlist(Integer totalWishlist) {
        this.totalWishlist = totalWishlist;
    }

    public Integer getOverallRating() {
        return overallRating;
    }

    public Products overallRating(Integer overallRating) {
        this.overallRating = overallRating;
        return this;
    }

    public void setOverallRating(Integer overallRating) {
        this.overallRating = overallRating;
    }

    public Boolean isPreferredInd() {
        return preferredInd;
    }

    public Products preferredInd(Boolean preferredInd) {
        this.preferredInd = preferredInd;
        return this;
    }

    public void setPreferredInd(Boolean preferredInd) {
        this.preferredInd = preferredInd;
    }

    public Boolean isFreeShippingInd() {
        return freeShippingInd;
    }

    public Products freeShippingInd(Boolean freeShippingInd) {
        this.freeShippingInd = freeShippingInd;
        return this;
    }

    public void setFreeShippingInd(Boolean freeShippingInd) {
        this.freeShippingInd = freeShippingInd;
    }

    public Boolean isMadeInMyanmarInd() {
        return madeInMyanmarInd;
    }

    public Products madeInMyanmarInd(Boolean madeInMyanmarInd) {
        this.madeInMyanmarInd = madeInMyanmarInd;
        return this;
    }

    public void setMadeInMyanmarInd(Boolean madeInMyanmarInd) {
        this.madeInMyanmarInd = madeInMyanmarInd;
    }

    public Boolean isQuestionsAboutProductInd() {
        return questionsAboutProductInd;
    }

    public Products questionsAboutProductInd(Boolean questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
        return this;
    }

    public void setQuestionsAboutProductInd(Boolean questionsAboutProductInd) {
        this.questionsAboutProductInd = questionsAboutProductInd;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }

    public Products releaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Instant getAvailableDate() {
        return availableDate;
    }

    public Products availableDate(Instant availableDate) {
        this.availableDate = availableDate;
        return this;
    }

    public void setAvailableDate(Instant availableDate) {
        this.availableDate = availableDate;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public Products activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Products lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Products lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Products validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public Products validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public ProductDocuments getProductDocument() {
        return productDocument;
    }

    public Products productDocument(ProductDocuments productDocuments) {
        this.productDocument = productDocuments;
        return this;
    }

    public void setProductDocument(ProductDocuments productDocuments) {
        this.productDocument = productDocuments;
    }

    public Set<StockItems> getStockItemLists() {
        return stockItemLists;
    }

    public Products stockItemLists(Set<StockItems> stockItems) {
        this.stockItemLists = stockItems;
        return this;
    }

    public Products addStockItemList(StockItems stockItems) {
        this.stockItemLists.add(stockItems);
        stockItems.setProduct(this);
        return this;
    }

    public Products removeStockItemList(StockItems stockItems) {
        this.stockItemLists.remove(stockItems);
        stockItems.setProduct(null);
        return this;
    }

    public void setStockItemLists(Set<StockItems> stockItems) {
        this.stockItemLists = stockItems;
    }

    public Suppliers getSupplier() {
        return supplier;
    }

    public Products supplier(Suppliers suppliers) {
        this.supplier = suppliers;
        return this;
    }

    public void setSupplier(Suppliers suppliers) {
        this.supplier = suppliers;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public Products productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public Products productBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
        return this;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Products)) {
            return false;
        }
        return id != null && id.equals(((Products) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Products{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cultureDetails='" + getCultureDetails() + "'" +
            ", handle='" + getHandle() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", sellCount=" + getSellCount() +
            ", productDetails='" + getProductDetails() + "'" +
            ", totalWishlist=" + getTotalWishlist() +
            ", overallRating=" + getOverallRating() +
            ", preferredInd='" + isPreferredInd() + "'" +
            ", freeShippingInd='" + isFreeShippingInd() + "'" +
            ", madeInMyanmarInd='" + isMadeInMyanmarInd() + "'" +
            ", questionsAboutProductInd='" + isQuestionsAboutProductInd() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
