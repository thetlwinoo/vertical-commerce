package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

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

    @Column(name = "active_ind")
    private Boolean activeInd;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "icon_font")
    private String iconFont;

    @ManyToOne
    @JsonIgnoreProperties(value = "paymentMethods", allowSetters = true)
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

    public Boolean isActiveInd() {
        return activeInd;
    }

    public PaymentMethods activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
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

    public Photos getIcon() {
        return icon;
    }

    public PaymentMethods icon(Photos photos) {
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
            ", activeInd='" + isActiveInd() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", iconFont='" + getIconFont() + "'" +
            "}";
    }
}
