package com.vertical.commerce.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionsMapperTest {

    private QuestionsMapper questionsMapper;

    @BeforeEach
    public void setUp() {
        questionsMapper = new QuestionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(questionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(questionsMapper.fromId(null)).isNull();
    }
}
