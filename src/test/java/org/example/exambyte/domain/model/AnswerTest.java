package org.example.exambyte.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnswerTest {

    @Test
    @DisplayName("Answers must have an Id")
    public void answerMustHaveAnId() {
        Answer answer = new Answer();

        answer.setId(123L);

        assertThat(answer.getId()).isEqualTo(123L);
    }

    @Test
    @DisplayName("Answers can't have an null as Id")
    public void answerMustHaveAnIdThatIsNull() {
        Answer answer = new Answer();

        try {
            answer.setId(null);
        }catch (NullPointerException e) {}

        assertThat(answer.getId()).isNull();
    }


    @Test
    @DisplayName("answer must have an question_Id")
    public void answerMustHaveAnQuestionId() {
        Answer answer = new Answer();


        answer.setQuestionId(14L);

        assertThat(answer.getQuestionId()).isEqualTo(14L);
    }

    @Test
    @DisplayName("answer must have an question_Id otherwise throws exceptions")
    public void answerMustHaveAnQuestionIdNullException() {
        Answer answer = new Answer();


        try {
            answer.setQuestionId(null);
        }catch (NullPointerException e) {}

        assertThat(answer.getQuestionId()).isNull();
    }


    @Test
    @DisplayName("answer can have an answer text")
    public void answerMustHaveAnAnswerText() {
        Answer answer = new Answer();

        String answerText = "answer";

        answer.setAnswerText(answerText);
        assertThat(answer.getAnswerText()).isEqualTo(answerText);
    }


    @Test
    @DisplayName("answer can have an answer as null")
    public void answerMustHaveAnAnswerTextIsNull() {
        Answer answer = new Answer();


        assertThat(answer.getAnswerText()).isNull();
    }


    @Test
    @DisplayName("answer must have an UserName")
    public void answerMustHaveAnTakenByUser() {
        Answer answer = new Answer();

        String username = "ahmad";

        answer.setAnswerText(username);
        assertThat(answer.getAnswerText()).isEqualTo(username);
    }

    @Test
    @DisplayName("answer must have an UserName can't be null" )
    public void answerMustHaveAnTakenByUserNotNull() {
        Answer answer = new Answer();

        try {
            answer.setTakenBy(null);
        }catch (NullPointerException e){}

        assertThat(answer.getTakenBy()).isNull();

    }


    @Test
    @DisplayName("answer must have an Test id can't be null" )
    public void answerMustHaveAnTestIdNotNull() {
        Answer answer = new Answer();

        answer.setTestId(11L);

        assertThat(answer.getTestId()).isEqualTo(11L);

    }

    @Test
    @DisplayName("answer must have an Test id can't be null" )
    public void answerMustHaveAnTestIdNull() {
        Answer answer = new Answer();

        try {
            answer.setTestId(null);
        }catch (NullPointerException e){}

        assertThat(answer.getTestId()).isNull();

    }






















}
