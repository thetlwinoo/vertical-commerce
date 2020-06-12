package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CardTypes.
 */
@Entity
@Table(name = "card_types")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CardTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "issuer_id")
    private Long issuerId;

    @Column(name = "modified_date")
    private Instant modifiedDate;

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

    public CardTypes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIssuerId() {
        return issuerId;
    }

    public CardTypes issuerId(Long issuerId) {
        this.issuerId = issuerId;
        return this;
    }

    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public CardTypes modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardTypes)) {
            return false;
        }
        return id != null && id.equals(((CardTypes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", issuerId=" + getIssuerId() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
