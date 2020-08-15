package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownshipsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownshipsLocalize.class);
        TownshipsLocalize townshipsLocalize1 = new TownshipsLocalize();
        townshipsLocalize1.setId(1L);
        TownshipsLocalize townshipsLocalize2 = new TownshipsLocalize();
        townshipsLocalize2.setId(townshipsLocalize1.getId());
        assertThat(townshipsLocalize1).isEqualTo(townshipsLocalize2);
        townshipsLocalize2.setId(2L);
        assertThat(townshipsLocalize1).isNotEqualTo(townshipsLocalize2);
        townshipsLocalize1.setId(null);
        assertThat(townshipsLocalize1).isNotEqualTo(townshipsLocalize2);
    }
}
