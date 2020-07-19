package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeliveryMethods.
 */
@Entity
@Table(name = "delivery_methods")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryMethods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "third_party_name")
    private String thirdPartyName;

    @Column(name = "expected_min_arrival_days")
    private Integer expectedMinArrivalDays;

    @Column(name = "expected_max_arrival_days")
    private Integer expectedMaxArrivalDays;

    @Column(name = "default_ind")
    private Boolean defaultInd;

    @Column(name = "delivery_note")
    private String deliveryNote;

    @NotNull
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @ManyToMany(mappedBy = "deliveryMethods")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Suppliers> suppliers = new HashSet<>();

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

    public DeliveryMethods name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public DeliveryMethods thirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
        return this;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public Integer getExpectedMinArrivalDays() {
        return expectedMinArrivalDays;
    }

    public DeliveryMethods expectedMinArrivalDays(Integer expectedMinArrivalDays) {
        this.expectedMinArrivalDays = expectedMinArrivalDays;
        return this;
    }

    public void setExpectedMinArrivalDays(Integer expectedMinArrivalDays) {
        this.expectedMinArrivalDays = expectedMinArrivalDays;
    }

    public Integer getExpectedMaxArrivalDays() {
        return expectedMaxArrivalDays;
    }

    public DeliveryMethods expectedMaxArrivalDays(Integer expectedMaxArrivalDays) {
        this.expectedMaxArrivalDays = expectedMaxArrivalDays;
        return this;
    }

    public void setExpectedMaxArrivalDays(Integer expectedMaxArrivalDays) {
        this.expectedMaxArrivalDays = expectedMaxArrivalDays;
    }

    public Boolean isDefaultInd() {
        return defaultInd;
    }

    public DeliveryMethods defaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
        return this;
    }

    public void setDefaultInd(Boolean defaultInd) {
        this.defaultInd = defaultInd;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public DeliveryMethods deliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
        return this;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public DeliveryMethods validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public DeliveryMethods validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Set<Suppliers> getSuppliers() {
        return suppliers;
    }

    public DeliveryMethods suppliers(Set<Suppliers> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public DeliveryMethods addSupplier(Suppliers suppliers) {
        this.suppliers.add(suppliers);
        suppliers.getDeliveryMethods().add(this);
        return this;
    }

    public DeliveryMethods removeSupplier(Suppliers suppliers) {
        this.suppliers.remove(suppliers);
        suppliers.getDeliveryMethods().remove(this);
        return this;
    }

    public void setSuppliers(Set<Suppliers> suppliers) {
        this.suppliers = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryMethods)) {
            return false;
        }
        return id != null && id.equals(((DeliveryMethods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryMethods{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thirdPartyName='" + getThirdPartyName() + "'" +
            ", expectedMinArrivalDays=" + getExpectedMinArrivalDays() +
            ", expectedMaxArrivalDays=" + getExpectedMaxArrivalDays() +
            ", defaultInd='" + isDefaultInd() + "'" +
            ", deliveryNote='" + getDeliveryNote() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            "}";
    }
}
