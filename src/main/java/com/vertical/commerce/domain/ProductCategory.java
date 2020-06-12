package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

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

    @Column(name = "short_label")
    private String shortLabel;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "icon_font")
    private String iconFont;

    @Column(name = "just_for_you_ind")
    private Boolean justForYouInd;

    @Column(name = "show_in_nav_ind")
    private Boolean showInNavInd;

    @Column(name = "active_ind")
    private Boolean activeInd;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategories", allowSetters = true)
    private ProductCategory parent;

    @ManyToOne
    @JsonIgnoreProperties(value = "productCategories", allowSetters = true)
    private Photos icon;

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

    public Boolean isShowInNavInd() {
        return showInNavInd;
    }

    public ProductCategory showInNavInd(Boolean showInNavInd) {
        this.showInNavInd = showInNavInd;
        return this;
    }

    public void setShowInNavInd(Boolean showInNavInd) {
        this.showInNavInd = showInNavInd;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public ProductCategory activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public Photos getIcon() {
        return icon;
    }

    public ProductCategory icon(Photos photos) {
        this.icon = photos;
        return this;
    }

    public void setIcon(Photos photos) {
        this.icon = photos;
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
            ", shortLabel='" + getShortLabel() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", justForYouInd='" + isJustForYouInd() + "'" +
            ", showInNavInd='" + isShowInNavInd() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            "}";
    }
}
