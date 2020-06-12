package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class BusinessEntityContactDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessEntityContactDTO.class);
        BusinessEntityContactDTO businessEntityContactDTO1 = new BusinessEntityContactDTO();
        businessEntityContactDTO1.setId(1L);
        BusinessEntityContactDTO businessEntityContactDTO2 = new BusinessEntityContactDTO();
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(businessEntityContactDTO1.getId());
        assertThat(businessEntityContactDTO1).isEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO2.setId(2L);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
        businessEntityContactDTO1.setId(null);
        assertThat(businessEntityContactDTO1).isNotEqualTo(businessEntityContactDTO2);
    }
}
