package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductDocumentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocuments.class);
        ProductDocuments productDocuments1 = new ProductDocuments();
        productDocuments1.setId(1L);
        ProductDocuments productDocuments2 = new ProductDocuments();
        productDocuments2.setId(productDocuments1.getId());
        assertThat(productDocuments1).isEqualTo(productDocuments2);
        productDocuments2.setId(2L);
        assertThat(productDocuments1).isNotEqualTo(productDocuments2);
        productDocuments1.setId(null);
        assertThat(productDocuments1).isNotEqualTo(productDocuments2);
    }
}
