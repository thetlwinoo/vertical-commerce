package com.vertical.commerce.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wishlists.
 */
@Entity
@Table(name = "wishlists")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wishlists implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private People wishlistUser;

    @OneToMany(mappedBy = "wishlist",cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<WishlistLines> wishlistLineLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public People getWishlistUser() {
        return wishlistUser;
    }

    public Wishlists wishlistUser(People people) {
        this.wishlistUser = people;
        return this;
    }

    public void setWishlistUser(People people) {
        this.wishlistUser = people;
    }

    public Set<WishlistLines> getWishlistLineLists() {
        return wishlistLineLists;
    }

    public Wishlists wishlistLineLists(Set<WishlistLines> wishlistLines) {
        this.wishlistLineLists = wishlistLines;
        return this;
    }

    public Wishlists addWishlistLineList(WishlistLines wishlistLines) {
        this.wishlistLineLists.add(wishlistLines);
        wishlistLines.setWishlist(this);
        return this;
    }

    public Wishlists removeWishlistLineList(WishlistLines wishlistLines) {
        this.wishlistLineLists.remove(wishlistLines);
        wishlistLines.setWishlist(null);
        return this;
    }

    public void setWishlistLineLists(Set<WishlistLines> wishlistLines) {
        this.wishlistLineLists = wishlistLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wishlists)) {
            return false;
        }
        return id != null && id.equals(((Wishlists) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wishlists{" +
            "id=" + getId() +
            "}";
    }
}
