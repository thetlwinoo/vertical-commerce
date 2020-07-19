package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class TownsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TownsDTO.class);
        TownsDTO townsDTO1 = new TownsDTO();
        townsDTO1.setId(1L);
        TownsDTO townsDTO2 = new TownsDTO();
        assertThat(townsDTO1).isNotEqualTo(townsDTO2);
        townsDTO2.setId(townsDTO1.getId());
        assertThat(townsDTO1).isEqualTo(townsDTO2);
        townsDTO2.setId(2L);
        assertThat(townsDTO1).isNotEqualTo(townsDTO2);
        townsDTO1.setId(null);
        assertThat(townsDTO1).isNotEqualTo(townsDTO2);
    }
}
