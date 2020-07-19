package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsCultureDTO.class);
        ProductsCultureDTO productsCultureDTO1 = new ProductsCultureDTO();
        productsCultureDTO1.setId(1L);
        ProductsCultureDTO productsCultureDTO2 = new ProductsCultureDTO();
        assertThat(productsCultureDTO1).isNotEqualTo(productsCultureDTO2);
        productsCultureDTO2.setId(productsCultureDTO1.getId());
        assertThat(productsCultureDTO1).isEqualTo(productsCultureDTO2);
        productsCultureDTO2.setId(2L);
        assertThat(productsCultureDTO1).isNotEqualTo(productsCultureDTO2);
        productsCultureDTO1.setId(null);
        assertThat(productsCultureDTO1).isNotEqualTo(productsCultureDTO2);
    }
}
