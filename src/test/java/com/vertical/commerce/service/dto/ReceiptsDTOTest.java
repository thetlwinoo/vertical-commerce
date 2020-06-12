package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class ReceiptsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptsDTO.class);
        ReceiptsDTO receiptsDTO1 = new ReceiptsDTO();
        receiptsDTO1.setId(1L);
        ReceiptsDTO receiptsDTO2 = new ReceiptsDTO();
        assertThat(receiptsDTO1).isNotEqualTo(receiptsDTO2);
        receiptsDTO2.setId(receiptsDTO1.getId());
        assertThat(receiptsDTO1).isEqualTo(receiptsDTO2);
        receiptsDTO2.setId(2L);
        assertThat(receiptsDTO1).isNotEqualTo(receiptsDTO2);
        receiptsDTO1.setId(null);
        assertThat(receiptsDTO1).isNotEqualTo(receiptsDTO2);
    }
}
