package org.example.exambyte.domain.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.exambyte.domain.model.QuestionType.MCQ;

public class QuestionTest {

    @Test
    @DisplayName("Question must have an Id")
    public void questionMustHaveAnId() {
        Question question = new Question();

        question.setId(123L);

        assertThat(question.getId()).isEqualTo(123L);
    }

    @Test
    @DisplayName("Question must have and question Test")
    public void questionMustHaveAndQuestionTest() {
        Question question = new Question();

        question.setQuestionText("who is the GOAT");
//        answer : Cristiano ( CR7 )

        assertThat(question.getQuestionText()).isEqualTo("who is the GOAT");

    }

    @Test
    @DisplayName("Question Must have an enum")
    public void questionMustHaveEnum() {
        Question question = new Question();

        question.setQuestionType(MCQ);

        assertThat(question.getQuestionType()).isEqualTo(MCQ);

    }

    @Test
    @DisplayName("Question must have an contionces with a Test (Test_Id)")
    public void questionMustHaveContioncesTest() {
        Question question = new Question();

        question.setTestId(123L);

        assertThat(question.getTestId()).isEqualTo(123L);
    }

    @Test
    @DisplayName("Test can have a Questions options")
    public void questionMustHaveOptions() {

        Question question = new Question();

        List<String> optionsMC = new ArrayList<>();
        optionsMC.add("option1");
        optionsMC.add("option2");
        optionsMC.add("option3");
        optionsMC.add("option4");

        question.setOptions(optionsMC);

    assertThat(question.getOptions()).isEqualTo(optionsMC);

    }

    @Test
    @DisplayName("Question must have an correct answer")
    public void questionMustHaveCorrectAnswer() {
        Question question = new Question();

        question.setCorrectAnswer("Berlin");

        assertThat(question.getCorrectAnswer()).isEqualTo("Berlin");
    }


    @Test
    @DisplayName("Question can have all the attributes")
    public void questionMustHaveAllAttributes() {

        List<String> optionsMC = new ArrayList<>();
        optionsMC.add("option1");
        optionsMC.add("option2");
        optionsMC.add("option3");
        optionsMC.add("option4");

        Question question = new Question();

        question.setId(15L);
        question.setQuestionText("what is ....");
        question.setQuestionType(MCQ);
        question.setTestId(123L);
        question.setOptions(optionsMC);
        question.setCorrectAnswer("Berlin");

        assertThat(question.getId()).isEqualTo(15L);
        assertThat(question.getQuestionText()).isEqualTo("what is ....");
        assertThat(question.getQuestionType()).isEqualTo(MCQ);
        assertThat(question.getTestId()).isEqualTo(123L);
        assertThat(question.getOptions()).isEqualTo(optionsMC);
        assertThat(question.getCorrectAnswer()).isEqualTo("Berlin");



    }












}


















