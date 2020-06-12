package com.vertical.commerce.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vertical.commerce.web.rest.TestUtil;

public class QuestionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsDTO.class);
        QuestionsDTO questionsDTO1 = new QuestionsDTO();
        questionsDTO1.setId(1L);
        QuestionsDTO questionsDTO2 = new QuestionsDTO();
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO2.setId(questionsDTO1.getId());
        assertThat(questionsDTO1).isEqualTo(questionsDTO2);
        questionsDTO2.setId(2L);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO1.setId(null);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
    }
}
