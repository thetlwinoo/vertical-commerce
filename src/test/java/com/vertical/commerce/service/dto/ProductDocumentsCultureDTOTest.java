package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductDocumentsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentsCultureDTO.class);
        ProductDocumentsCultureDTO productDocumentsCultureDTO1 = new ProductDocumentsCultureDTO();
        productDocumentsCultureDTO1.setId(1L);
        ProductDocumentsCultureDTO productDocumentsCultureDTO2 = new ProductDocumentsCultureDTO();
        assertThat(productDocumentsCultureDTO1).isNotEqualTo(productDocumentsCultureDTO2);
        productDocumentsCultureDTO2.setId(productDocumentsCultureDTO1.getId());
        assertThat(productDocumentsCultureDTO1).isEqualTo(productDocumentsCultureDTO2);
        productDocumentsCultureDTO2.setId(2L);
        assertThat(productDocumentsCultureDTO1).isNotEqualTo(productDocumentsCultureDTO2);
        productDocumentsCultureDTO1.setId(null);
        assertThat(productDocumentsCultureDTO1).isNotEqualTo(productDocumentsCultureDTO2);
    }
}
