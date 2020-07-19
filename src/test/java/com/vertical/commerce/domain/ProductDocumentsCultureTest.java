package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ProductDocumentsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDocumentsCulture.class);
        ProductDocumentsCulture productDocumentsCulture1 = new ProductDocumentsCulture();
        productDocumentsCulture1.setId(1L);
        ProductDocumentsCulture productDocumentsCulture2 = new ProductDocumentsCulture();
        productDocumentsCulture2.setId(productDocumentsCulture1.getId());
        assertThat(productDocumentsCulture1).isEqualTo(productDocumentsCulture2);
        productDocumentsCulture2.setId(2L);
        assertThat(productDocumentsCulture1).isNotEqualTo(productDocumentsCulture2);
        productDocumentsCulture1.setId(null);
        assertThat(productDocumentsCulture1).isNotEqualTo(productDocumentsCulture2);
    }
}
