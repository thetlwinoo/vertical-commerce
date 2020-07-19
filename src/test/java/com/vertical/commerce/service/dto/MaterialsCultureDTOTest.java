package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class MaterialsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsCultureDTO.class);
        MaterialsCultureDTO materialsCultureDTO1 = new MaterialsCultureDTO();
        materialsCultureDTO1.setId(1L);
        MaterialsCultureDTO materialsCultureDTO2 = new MaterialsCultureDTO();
        assertThat(materialsCultureDTO1).isNotEqualTo(materialsCultureDTO2);
        materialsCultureDTO2.setId(materialsCultureDTO1.getId());
        assertThat(materialsCultureDTO1).isEqualTo(materialsCultureDTO2);
        materialsCultureDTO2.setId(2L);
        assertThat(materialsCultureDTO1).isNotEqualTo(materialsCultureDTO2);
        materialsCultureDTO1.setId(null);
        assertThat(materialsCultureDTO1).isNotEqualTo(materialsCultureDTO2);
    }
}
