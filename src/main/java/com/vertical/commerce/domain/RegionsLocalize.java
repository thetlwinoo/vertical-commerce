package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RegionsLocalize.
 */
@Entity
@Table(name = "regions_localize")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegionsLocalize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "regionsLocalizes", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "regionsLocalizes", allowSetters = true)
    private Regions region;

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

    public RegionsLocalize name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public RegionsLocalize culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public Regions getRegion() {
        return region;
    }

    public RegionsLocalize region(Regions regions) {
        this.region = regions;
        return this;
    }

    public void setRegion(Regions regions) {
        this.region = regions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionsLocalize)) {
            return false;
        }
        return id != null && id.equals(((RegionsLocalize) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegionsLocalize{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
