package com.vertical.commerce.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsLocalizeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownsLocalize.class);
        TownsLocalize townsLocalize1 = new TownsLocalize();
        townsLocalize1.setId(1L);
        TownsLocalize townsLocalize2 = new TownsLocalize();
        townsLocalize2.setId(townsLocalize1.getId());
        assertThat(townsLocalize1).isEqualTo(townsLocalize2);
        townsLocalize2.setId(2L);
        assertThat(townsLocalize1).isNotEqualTo(townsLocalize2);
        townsLocalize1.setId(null);
        assertThat(townsLocalize1).isNotEqualTo(townsLocalize2);
    }
}
