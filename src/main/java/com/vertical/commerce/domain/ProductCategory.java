package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ProductCategory.
 */
@Entity
@Table(name = "product_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory implements Serializable {

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

    @Column(name = "short_label")
    private String shortLabel;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "icon_font")
    private String iconFont;

    @Column(name = "icon_photo")
    private String iconPhoto;

    @Column(name = "just_for_you_ind")
    private Boolean justForYouInd;

    @Column(name = "parent_cascade")
    private String parentCascade;

    @NotNull
    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "localization")
    private String localization;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategories", allowSetters = true)
    private ProductCategory parent;

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

    public ProductCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public ProductCategory handle(String handle) {
        this.handle = handle;
        return this;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public ProductCategory shortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public ProductCategory sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIconFont() {
        return iconFont;
    }

    public ProductCategory iconFont(String iconFont) {
        this.iconFont = iconFont;
        return this;
    }

    public void setIconFont(String iconFont) {
        this.iconFont = iconFont;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public ProductCategory iconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
        return this;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public Boolean isJustForYouInd() {
        return justForYouInd;
    }

    public ProductCategory justForYouInd(Boolean justForYouInd) {
        this.justForYouInd = justForYouInd;
        return this;
    }

    public void setJustForYouInd(Boolean justForYouInd) {
        this.justForYouInd = justForYouInd;
    }

    public String getParentCascade() {
        return parentCascade;
    }

    public ProductCategory parentCascade(String parentCascade) {
        this.parentCascade = parentCascade;
        return this;
    }

    public void setParentCascade(String parentCascade) {
        this.parentCascade = parentCascade;
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public ProductCategory activeFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLocalization() {
        return localization;
    }

    public ProductCategory localization(String localization) {
        this.localization = localization;
        return this;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public ProductCategory validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public ProductCategory validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public ProductCategory parent(ProductCategory productCategory) {
        this.parent = productCategory;
        return this;
    }

    public void setParent(ProductCategory productCategory) {
        this.parent = productCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategory)) {
            return false;
        }
        return id != null && id.equals(((ProductCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", handle='" + getHandle() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", iconPhoto='" + getIconPhoto() + "'" +
            ", justForYouInd='" + isJustForYouInd() + "'" +
            ", parentCascade='" + getParentCascade() + "'" +
            ", activeFlag='" + isActiveFlag() + "'" +
            ", localization='" + getLocalization() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
