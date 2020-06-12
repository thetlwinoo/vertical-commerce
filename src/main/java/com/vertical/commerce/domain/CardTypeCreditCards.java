package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CardTypeCreditCards.
 */
@Entity
@Table(name = "card_type_credit_cards")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CardTypeCreditCards implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_number")
    private Integer startNumber;

    @Column(name = "end_number")
    private Integer endNumber;

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

    public CardTypeCreditCards name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public CardTypeCreditCards startNumber(Integer startNumber) {
        this.startNumber = startNumber;
        return this;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public CardTypeCreditCards endNumber(Integer endNumber) {
        this.endNumber = endNumber;
        return this;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public CardTypeCreditCards modifiedDate(Instant modifiedDate) {
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
        if (!(o instanceof CardTypeCreditCards)) {
            return false;
        }
        return id != null && id.equals(((CardTypeCreditCards) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardTypeCreditCards{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startNumber=" + getStartNumber() +
            ", endNumber=" + getEndNumber() +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
