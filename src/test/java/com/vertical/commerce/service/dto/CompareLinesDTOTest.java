package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class CompareLinesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompareLinesDTO.class);
        CompareLinesDTO compareLinesDTO1 = new CompareLinesDTO();
        compareLinesDTO1.setId(1L);
        CompareLinesDTO compareLinesDTO2 = new CompareLinesDTO();
        assertThat(compareLinesDTO1).isNotEqualTo(compareLinesDTO2);
        compareLinesDTO2.setId(compareLinesDTO1.getId());
        assertThat(compareLinesDTO1).isEqualTo(compareLinesDTO2);
        compareLinesDTO2.setId(2L);
        assertThat(compareLinesDTO1).isNotEqualTo(compareLinesDTO2);
        compareLinesDTO1.setId(null);
        assertThat(compareLinesDTO1).isNotEqualTo(compareLinesDTO2);
    }
}
