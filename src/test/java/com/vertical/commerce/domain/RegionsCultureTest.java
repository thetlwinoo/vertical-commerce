package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class RegionsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionsCulture.class);
        RegionsCulture regionsCulture1 = new RegionsCulture();
        regionsCulture1.setId(1L);
        RegionsCulture regionsCulture2 = new RegionsCulture();
        regionsCulture2.setId(regionsCulture1.getId());
        assertThat(regionsCulture1).isEqualTo(regionsCulture2);
        regionsCulture2.setId(2L);
        assertThat(regionsCulture1).isNotEqualTo(regionsCulture2);
        regionsCulture1.setId(null);
        assertThat(regionsCulture1).isNotEqualTo(regionsCulture2);
    }
}
