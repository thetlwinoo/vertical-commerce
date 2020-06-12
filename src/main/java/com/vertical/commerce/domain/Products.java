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

    @Column(name = "handle")
    private String handle;

    @Column(name = "product_number")
    private String productNumber;

    @Column(name = "search_details")
    private String searchDetails;

    @Column(name = "sell_count")
    private Integer sellCount;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "stock_item_string")
    private String stockItemString;

    @Column(name = "total_wishlist")
    private Integer totalWishlist;

    @Column(name = "total_stars")
    private Integer totalStars;

    @Column(name = "discounted_percentage")
    private Integer discountedPercentage;

    @Column(name = "preferred_ind")
    private Boolean preferredInd;

    @Column(name = "available_delivery_ind")
    private Boolean availableDeliveryInd;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @Column(name = "questions_about_product_ind")
    private Boolean questionsAboutProductInd;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

    @NotNull
    @Column(name = "release_date", nullable = false)
    private Instant releaseDate;

    @NotNull
    @Column(name = "available_date", nullable = false)
    private Instant availableDate;

    @OneToOne
    @JoinColumn(unique = true)
    private ProductDocument productDocument;

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

    public String getStockItemString() {
        return stockItemString;
    }

    public Products stockItemString(String stockItemString) {
        this.stockItemString = stockItemString;
        return this;
    }

    public void setStockItemString(String stockItemString) {
        this.stockItemString = stockItemString;
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

    public Integer getTotalStars() {
        return totalStars;
    }

    public Products totalStars(Integer totalStars) {
        this.totalStars = totalStars;
        return this;
    }

    public void setTotalStars(Integer totalStars) {
        this.totalStars = totalStars;
    }

    public Integer getDiscountedPercentage() {
        return discountedPercentage;
    }

    public Products discountedPercentage(Integer discountedPercentage) {
        this.discountedPercentage = discountedPercentage;
        return this;
    }

    public void setDiscountedPercentage(Integer discountedPercentage) {
        this.discountedPercentage = discountedPercentage;
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

    public Boolean isAvailableDeliveryInd() {
        return availableDeliveryInd;
    }

    public Products availableDeliveryInd(Boolean availableDeliveryInd) {
        this.availableDeliveryInd = availableDeliveryInd;
        return this;
    }

    public void setAvailableDeliveryInd(Boolean availableDeliveryInd) {
        this.availableDeliveryInd = availableDeliveryInd;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public Products activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public ProductDocument getProductDocument() {
        return productDocument;
    }

    public Products productDocument(ProductDocument productDocument) {
        this.productDocument = productDocument;
        return this;
    }

    public void setProductDocument(ProductDocument productDocument) {
        this.productDocument = productDocument;
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
            ", handle='" + getHandle() + "'" +
            ", productNumber='" + getProductNumber() + "'" +
            ", searchDetails='" + getSearchDetails() + "'" +
            ", sellCount=" + getSellCount() +
            ", stockItemString='" + getStockItemString() + "'" +
            ", totalWishlist=" + getTotalWishlist() +
            ", totalStars=" + getTotalStars() +
            ", discountedPercentage=" + getDiscountedPercentage() +
            ", preferredInd='" + isPreferredInd() + "'" +
            ", availableDeliveryInd='" + isAvailableDeliveryInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", questionsAboutProductInd='" + isQuestionsAboutProductInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", availableDate='" + getAvailableDate() + "'" +
            "}";
    }
}
