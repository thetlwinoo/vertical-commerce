package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TaxClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxClassDTO.class);
        TaxClassDTO taxClassDTO1 = new TaxClassDTO();
        taxClassDTO1.setId(1L);
        TaxClassDTO taxClassDTO2 = new TaxClassDTO();
        assertThat(taxClassDTO1).isNotEqualTo(taxClassDTO2);
        taxClassDTO2.setId(taxClassDTO1.getId());
        assertThat(taxClassDTO1).isEqualTo(taxClassDTO2);
        taxClassDTO2.setId(2L);
        assertThat(taxClassDTO1).isNotEqualTo(taxClassDTO2);
        taxClassDTO1.setId(null);
        assertThat(taxClassDTO1).isNotEqualTo(taxClassDTO2);
    }
}
