package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Logistics.
 */
@Entity
@Table(name = "logistics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Logistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "active_ind", nullable = false)
    private Boolean activeInd;

    @NotNull
    @Column(name = "last_edited_by", nullable = false)
    private String lastEditedBy;

    @NotNull
    @Column(name = "last_edited_when", nullable = false)
    private Instant lastEditedWhen;

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

    public Logistics name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActiveInd() {
        return activeInd;
    }

    public Logistics activeInd(Boolean activeInd) {
        this.activeInd = activeInd;
        return this;
    }

    public void setActiveInd(Boolean activeInd) {
        this.activeInd = activeInd;
    }

    public String getLastEditedBy() {
        return lastEditedBy;
    }

    public Logistics lastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
        return this;
    }

    public void setLastEditedBy(String lastEditedBy) {
        this.lastEditedBy = lastEditedBy;
    }

    public Instant getLastEditedWhen() {
        return lastEditedWhen;
    }

    public Logistics lastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
        return this;
    }

    public void setLastEditedWhen(Instant lastEditedWhen) {
        this.lastEditedWhen = lastEditedWhen;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Logistics)) {
            return false;
        }
        return id != null && id.equals(((Logistics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Logistics{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", activeInd='" + isActiveInd() + "'" +
            ", lastEditedBy='" + getLastEditedBy() + "'" +
            ", lastEditedWhen='" + getLastEditedWhen() + "'" +
            "}";
    }
}
