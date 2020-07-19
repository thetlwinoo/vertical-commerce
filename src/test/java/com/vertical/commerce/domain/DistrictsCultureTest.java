package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class DistrictsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictsCulture.class);
        DistrictsCulture districtsCulture1 = new DistrictsCulture();
        districtsCulture1.setId(1L);
        DistrictsCulture districtsCulture2 = new DistrictsCulture();
        districtsCulture2.setId(districtsCulture1.getId());
        assertThat(districtsCulture1).isEqualTo(districtsCulture2);
        districtsCulture2.setId(2L);
        assertThat(districtsCulture1).isNotEqualTo(districtsCulture2);
        districtsCulture1.setId(null);
        assertThat(districtsCulture1).isNotEqualTo(districtsCulture2);
    }
}
