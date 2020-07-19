package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductDocumentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentsDTO.class);
        ProductDocumentsDTO productDocumentsDTO1 = new ProductDocumentsDTO();
        productDocumentsDTO1.setId(1L);
        ProductDocumentsDTO productDocumentsDTO2 = new ProductDocumentsDTO();
        assertThat(productDocumentsDTO1).isNotEqualTo(productDocumentsDTO2);
        productDocumentsDTO2.setId(productDocumentsDTO1.getId());
        assertThat(productDocumentsDTO1).isEqualTo(productDocumentsDTO2);
        productDocumentsDTO2.setId(2L);
        assertThat(productDocumentsDTO1).isNotEqualTo(productDocumentsDTO2);
        productDocumentsDTO1.setId(null);
        assertThat(productDocumentsDTO1).isNotEqualTo(productDocumentsDTO2);
    }
}
