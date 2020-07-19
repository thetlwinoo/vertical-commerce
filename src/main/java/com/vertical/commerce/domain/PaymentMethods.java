package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PaymentMethods.
 */
@Entity
@Table(name = "payment_methods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PaymentMethods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "icon_font")
    private String iconFont;

    @Column(name = "icon_photo")
    private String iconPhoto;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

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

    public PaymentMethods name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public PaymentMethods code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public PaymentMethods disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public PaymentMethods sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIconFont() {
        return iconFont;
    }

    public PaymentMethods iconFont(String iconFont) {
        this.iconFont = iconFont;
        return this;
    }

    public void setIconFont(String iconFont) {
        this.iconFont = iconFont;
    }

    public String getIconPhoto() {
        return iconPhoto;
    }

    public PaymentMethods iconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
        return this;
    }

    public void setIconPhoto(String iconPhoto) {
        this.iconPhoto = iconPhoto;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public PaymentMethods validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public PaymentMethods validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentMethods)) {
            return false;
        }
        return id != null && id.equals(((PaymentMethods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentMethods{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", disabled='" + isDisabled() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            ", iconPhoto='" + getIconPhoto() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
