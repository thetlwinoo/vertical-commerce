package com.vertical.commerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TownsLocalize.
 */
@Entity
@Table(name = "towns_localize")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TownsLocalize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = "townsLocalizes", allowSetters = true)
    private Culture culture;

    @ManyToOne
    @JsonIgnoreProperties(value = "townsLocalizes", allowSetters = true)
    private Towns town;

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

    public TownsLocalize name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Culture getCulture() {
        return culture;
    }

    public TownsLocalize culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public Towns getTown() {
        return town;
    }

    public TownsLocalize town(Towns towns) {
        this.town = towns;
        return this;
    }

    public void setTown(Towns towns) {
        this.town = towns;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownsLocalize)) {
            return false;
        }
        return id != null && id.equals(((TownsLocalize) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownsLocalize{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
