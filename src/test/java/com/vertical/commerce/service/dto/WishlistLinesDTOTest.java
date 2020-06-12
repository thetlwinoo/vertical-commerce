package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class WishlistLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WishlistLinesDTO.class);
        WishlistLinesDTO wishlistLinesDTO1 = new WishlistLinesDTO();
        wishlistLinesDTO1.setId(1L);
        WishlistLinesDTO wishlistLinesDTO2 = new WishlistLinesDTO();
        assertThat(wishlistLinesDTO1).isNotEqualTo(wishlistLinesDTO2);
        wishlistLinesDTO2.setId(wishlistLinesDTO1.getId());
        assertThat(wishlistLinesDTO1).isEqualTo(wishlistLinesDTO2);
        wishlistLinesDTO2.setId(2L);
        assertThat(wishlistLinesDTO1).isNotEqualTo(wishlistLinesDTO2);
        wishlistLinesDTO1.setId(null);
        assertThat(wishlistLinesDTO1).isNotEqualTo(wishlistLinesDTO2);
    }
}
