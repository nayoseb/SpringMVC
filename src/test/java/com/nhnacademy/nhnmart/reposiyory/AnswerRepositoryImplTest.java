package com.nhnacademy.nhnmart.reposiyory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.nhnmart.domain.Answer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnswerRepositoryImplTest {

    private AnswerRepositoryImpl answerRepository;

    @BeforeEach
    void setUp() {
        answerRepository = new AnswerRepositoryImpl();
    }

    @Test
    void testSaveAndFindById() {
        Answer answer = Answer.builder()
                .inquiryId(1L)
                .content("Test Answer")
                .responseDate(LocalDateTime.now())
                .csRepresentativeId("cs_rep")
                .build();

        Answer savedAnswer = answerRepository.save(answer);

        Answer foundAnswer = answerRepository.findById(savedAnswer.getId());

        assertThat(foundAnswer).isNotNull();
        assertThat(foundAnswer.getContent()).isEqualTo("Test Answer");
        assertThat(foundAnswer.getCsRepresentativeId()).isEqualTo("cs_rep");
    }

}
