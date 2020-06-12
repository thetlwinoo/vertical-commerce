package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class InvoicesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoicesDTO.class);
        InvoicesDTO invoicesDTO1 = new InvoicesDTO();
        invoicesDTO1.setId(1L);
        InvoicesDTO invoicesDTO2 = new InvoicesDTO();
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO2.setId(invoicesDTO1.getId());
        assertThat(invoicesDTO1).isEqualTo(invoicesDTO2);
        invoicesDTO2.setId(2L);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
        invoicesDTO1.setId(null);
        assertThat(invoicesDTO1).isNotEqualTo(invoicesDTO2);
    }
}
