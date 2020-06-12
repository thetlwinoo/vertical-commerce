package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentDTO.class);
        ProductDocumentDTO productDocumentDTO1 = new ProductDocumentDTO();
        productDocumentDTO1.setId(1L);
        ProductDocumentDTO productDocumentDTO2 = new ProductDocumentDTO();
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(productDocumentDTO1.getId());
        assertThat(productDocumentDTO1).isEqualTo(productDocumentDTO2);
        productDocumentDTO2.setId(2L);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
        productDocumentDTO1.setId(null);
        assertThat(productDocumentDTO1).isNotEqualTo(productDocumentDTO2);
    }
}
