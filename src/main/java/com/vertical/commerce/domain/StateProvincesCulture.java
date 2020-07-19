package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StateProvincesCulture.
 */
@Entity
@Table(name = "state_provinces_culture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StateProvincesCulture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "stateProvincesCultures", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "stateProvincesCultures", allowSetters = true)
    private StateProvinces stateProvince;

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

    public StateProvincesCulture name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public StateProvincesCulture culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public StateProvinces getStateProvince() {
        return stateProvince;
    }

    public StateProvincesCulture stateProvince(StateProvinces stateProvinces) {
        this.stateProvince = stateProvinces;
        return this;
    }

    public void setStateProvince(StateProvinces stateProvinces) {
        this.stateProvince = stateProvinces;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StateProvincesCulture)) {
            return false;
        }
        return id != null && id.equals(((StateProvincesCulture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvincesCulture{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
