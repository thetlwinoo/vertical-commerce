package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DistrictsCultureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictsCultureDTO.class);
        DistrictsCultureDTO districtsCultureDTO1 = new DistrictsCultureDTO();
        districtsCultureDTO1.setId(1L);
        DistrictsCultureDTO districtsCultureDTO2 = new DistrictsCultureDTO();
        assertThat(districtsCultureDTO1).isNotEqualTo(districtsCultureDTO2);
        districtsCultureDTO2.setId(districtsCultureDTO1.getId());
        assertThat(districtsCultureDTO1).isEqualTo(districtsCultureDTO2);
        districtsCultureDTO2.setId(2L);
        assertThat(districtsCultureDTO1).isNotEqualTo(districtsCultureDTO2);
        districtsCultureDTO1.setId(null);
        assertThat(districtsCultureDTO1).isNotEqualTo(districtsCultureDTO2);
    }
}
