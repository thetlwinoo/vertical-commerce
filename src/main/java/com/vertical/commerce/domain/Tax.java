package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tax.
 */
@Entity
@Table(name = "tax")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Double rate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "taxes", allowSetters = true)
    private TaxClass taxClass;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Tax code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Tax name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public Tax rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public Tax modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public TaxClass getTaxClass() {
        return taxClass;
    }

    public Tax taxClass(TaxClass taxClass) {
        this.taxClass = taxClass;
        return this;
    }

    public void setTaxClass(TaxClass taxClass) {
        this.taxClass = taxClass;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tax)) {
            return false;
        }
        return id != null && id.equals(((Tax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tax{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", rate=" + getRate() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
