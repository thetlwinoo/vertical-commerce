package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class MaterialsLocalizeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsLocalizeDTO.class);
        MaterialsLocalizeDTO materialsLocalizeDTO1 = new MaterialsLocalizeDTO();
        materialsLocalizeDTO1.setId(1L);
        MaterialsLocalizeDTO materialsLocalizeDTO2 = new MaterialsLocalizeDTO();
        assertThat(materialsLocalizeDTO1).isNotEqualTo(materialsLocalizeDTO2);
        materialsLocalizeDTO2.setId(materialsLocalizeDTO1.getId());
        assertThat(materialsLocalizeDTO1).isEqualTo(materialsLocalizeDTO2);
        materialsLocalizeDTO2.setId(2L);
        assertThat(materialsLocalizeDTO1).isNotEqualTo(materialsLocalizeDTO2);
        materialsLocalizeDTO1.setId(null);
        assertThat(materialsLocalizeDTO1).isNotEqualTo(materialsLocalizeDTO2);
    }
}
