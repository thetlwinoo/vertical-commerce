package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Compares.
 */
@Entity
@Table(name = "compares")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Compares implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private People compareUser;

    @OneToMany(mappedBy = "compare")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CompareLines> compareLineLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public People getCompareUser() {
        return compareUser;
    }

    public Compares compareUser(People people) {
        this.compareUser = people;
        return this;
    }

    public void setCompareUser(People people) {
        this.compareUser = people;
    }

    public Set<CompareLines> getCompareLineLists() {
        return compareLineLists;
    }

    public Compares compareLineLists(Set<CompareLines> compareLines) {
        this.compareLineLists = compareLines;
        return this;
    }

    public Compares addCompareLineList(CompareLines compareLines) {
        this.compareLineLists.add(compareLines);
        compareLines.setCompare(this);
        return this;
    }

    public Compares removeCompareLineList(CompareLines compareLines) {
        this.compareLineLists.remove(compareLines);
        compareLines.setCompare(null);
        return this;
    }

    public void setCompareLineLists(Set<CompareLines> compareLines) {
        this.compareLineLists = compareLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compares)) {
            return false;
        }
        return id != null && id.equals(((Compares) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compares{" +
            "id=" + getId() +
            "}";
    }
}
