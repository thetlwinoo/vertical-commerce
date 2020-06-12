package com.vertical.commerce.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.vertical.commerce.domain.Wishlists} entity.
 */
public class WishlistsDTO implements Serializable {
    
    private Long id;


    private Long wishlistUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWishlistUserId() {
        return wishlistUserId;
    }

    public void setWishlistUserId(Long peopleId) {
        this.wishlistUserId = peopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WishlistsDTO)) {
            return false;
        }

        return id != null && id.equals(((WishlistsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WishlistsDTO{" +
            "id=" + getId() +
            ", wishlistUserId=" + getWishlistUserId() +
            "}";
    }
}
