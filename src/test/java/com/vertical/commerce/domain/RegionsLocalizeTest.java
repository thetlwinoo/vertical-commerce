package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class RegionsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionsLocalize.class);
        RegionsLocalize regionsLocalize1 = new RegionsLocalize();
        regionsLocalize1.setId(1L);
        RegionsLocalize regionsLocalize2 = new RegionsLocalize();
        regionsLocalize2.setId(regionsLocalize1.getId());
        assertThat(regionsLocalize1).isEqualTo(regionsLocalize2);
        regionsLocalize2.setId(2L);
        assertThat(regionsLocalize1).isNotEqualTo(regionsLocalize2);
        regionsLocalize1.setId(null);
        assertThat(regionsLocalize1).isNotEqualTo(regionsLocalize2);
    }
}
