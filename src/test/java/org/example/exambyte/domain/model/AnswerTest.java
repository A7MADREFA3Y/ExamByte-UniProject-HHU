package org.example.exambyte.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    @Test
    @DisplayName("Asnwers must have an Id")
    public void answerMustHaveAnId() {
        Answer answer = new Answer();

        answer.setId(123L);

        assertThat(answer.getId()).isEqualTo(123L);
    }

//    @Test
//    @DisplayName("answer must have an question_Id")
//    public void answerMustHaveAnQuestionId() {
//        Answer answer = new Answer();
//
//        Question question = new Question();
//        question.setId(123L);
//
//        answer.setQuestion(question);
//
//        assertThat(answer.getQuestion()).isEqualTo(question);
//    }

    @Test
    @DisplayName("answer must have an answer text")
    public void answerMustHaveAnAnswerText() {
        Answer answer = new Answer();

        String answerText = "answer";

        answer.setAnswerText(answerText);
        assertThat(answer.getAnswerText()).isEqualTo(answerText);
    }






















}
