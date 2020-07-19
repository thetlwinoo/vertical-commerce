package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownshipsCulture.class);
        TownshipsCulture townshipsCulture1 = new TownshipsCulture();
        townshipsCulture1.setId(1L);
        TownshipsCulture townshipsCulture2 = new TownshipsCulture();
        townshipsCulture2.setId(townshipsCulture1.getId());
        assertThat(townshipsCulture1).isEqualTo(townshipsCulture2);
        townshipsCulture2.setId(2L);
        assertThat(townshipsCulture1).isNotEqualTo(townshipsCulture2);
        townshipsCulture1.setId(null);
        assertThat(townshipsCulture1).isNotEqualTo(townshipsCulture2);
    }
}
