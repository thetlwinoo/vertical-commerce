package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TaxClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxClass.class);
        TaxClass taxClass1 = new TaxClass();
        taxClass1.setId(1L);
        TaxClass taxClass2 = new TaxClass();
        taxClass2.setId(taxClass1.getId());
        assertThat(taxClass1).isEqualTo(taxClass2);
        taxClass2.setId(2L);
        assertThat(taxClass1).isNotEqualTo(taxClass2);
        taxClass1.setId(null);
        assertThat(taxClass1).isNotEqualTo(taxClass2);
    }
}
