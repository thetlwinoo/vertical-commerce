package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsCultureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownsCulture.class);
        TownsCulture townsCulture1 = new TownsCulture();
        townsCulture1.setId(1L);
        TownsCulture townsCulture2 = new TownsCulture();
        townsCulture2.setId(townsCulture1.getId());
        assertThat(townsCulture1).isEqualTo(townsCulture2);
        townsCulture2.setId(2L);
        assertThat(townsCulture1).isNotEqualTo(townsCulture2);
        townsCulture1.setId(null);
        assertThat(townsCulture1).isNotEqualTo(townsCulture2);
    }
}
