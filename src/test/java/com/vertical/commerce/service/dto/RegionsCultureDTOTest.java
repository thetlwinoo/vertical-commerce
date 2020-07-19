package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class RegionsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionsCultureDTO.class);
        RegionsCultureDTO regionsCultureDTO1 = new RegionsCultureDTO();
        regionsCultureDTO1.setId(1L);
        RegionsCultureDTO regionsCultureDTO2 = new RegionsCultureDTO();
        assertThat(regionsCultureDTO1).isNotEqualTo(regionsCultureDTO2);
        regionsCultureDTO2.setId(regionsCultureDTO1.getId());
        assertThat(regionsCultureDTO1).isEqualTo(regionsCultureDTO2);
        regionsCultureDTO2.setId(2L);
        assertThat(regionsCultureDTO1).isNotEqualTo(regionsCultureDTO2);
        regionsCultureDTO1.setId(null);
        assertThat(regionsCultureDTO1).isNotEqualTo(regionsCultureDTO2);
    }
}
