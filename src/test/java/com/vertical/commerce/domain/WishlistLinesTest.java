package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class WishlistLinesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WishlistLines.class);
        WishlistLines wishlistLines1 = new WishlistLines();
        wishlistLines1.setId(1L);
        WishlistLines wishlistLines2 = new WishlistLines();
        wishlistLines2.setId(wishlistLines1.getId());
        assertThat(wishlistLines1).isEqualTo(wishlistLines2);
        wishlistLines2.setId(2L);
        assertThat(wishlistLines1).isNotEqualTo(wishlistLines2);
        wishlistLines1.setId(null);
        assertThat(wishlistLines1).isNotEqualTo(wishlistLines2);
    }
}
