package org.example.exambyte.domain.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.example.exambyte.domain.model.QuestionType.FREE_TEXT;
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
    @DisplayName("Question can't have an Id as null")
    public void questionCantHaveAnIdAsNull() {
        Question question = new Question();

        try {
            question.setId(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("id is Null");
        }


        assertThat(question.getId()).isNull();
    }



    @Test
    @DisplayName("Question must have and question Text")
    public void questionMustHaveAndQuestionText() {
        Question question = new Question();

        question.setQuestionText("who is the GOAT");
//        answer : Cristiano ( CR7 )

        assertThat(question.getQuestionText()).isEqualTo("who is the GOAT");

    }


    @Test
    @DisplayName("Question must have and question Test can't be null ")
    public void questionCantHaveAndQuestionTextAsNull() {
        Question question = new Question();

        try {
            question.setQuestionText(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("question text is Null");

        }

        assertThat(question.getQuestionText()).isNull();

    }



    @Test
    @DisplayName("Question can have an enum Type free text")
    public void questionMustHaveEnum() {
        Question question = new Question();

        question.setQuestionType(MCQ);

        assertThat(question.getQuestionType()).isEqualTo(MCQ);

    }

    @Test
    @DisplayName("Question can have an enum Type free text")
    public void questionMustHaveEnum2() {
        Question question = new Question();

        question.setQuestionType(FREE_TEXT);

        assertThat(question.getQuestionType()).isEqualTo(FREE_TEXT);

    }

    @Test
    @DisplayName("Question Must have an enum Type not null")
    public void questionMustHaveEnumNotNull() {
        Question question = new Question();

        try {
            question.setQuestionType(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("question type is Null");

        }

        assertThat(question.getQuestionType()).isNull();

    }

    @Test
    @DisplayName("Question must have an  TestID (Test_Id)")
    public void questionMustHaveTestId() {
        Question question = new Question();

        question.setTestId(123L);

        assertThat(question.getTestId()).isEqualTo(123L);
    }



    @Test
    @DisplayName("Question cant have an TestID (Test_Id) as null")
    public void questionCantHaveTestIdAsNull() {
        Question question = new Question();

        try {
            question.setTestId(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("test id is Null");
        }

        assertThat(question.getTestId()).isNull();
    }

    @Test
    @DisplayName("Test can have a min 2 Questions options")
    public void questionMustHaveMin2Options() {

        Question question = new Question();

        question.setOption2("ahmad");

        try {
            question.setOption1(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("question option 1 is Null");
        }

        assertThat(question.getTestId()).isNull();


        assertThat(question.getOption1()).isNull();
        assertThat(question.getOption2()).isEqualTo("ahmad");

    }


    @Test
    @DisplayName("Test can have a max of 4 Questions options")
    public void questionCanHave4Options() {

        Question question = new Question();

        question.setOption1("option1");
        question.setOption2("option2");
        question.setOption3("option3");
        question.setOption4("option4");

        assertThat(question.getOption1()).isEqualTo("option1");
        assertThat(question.getOption2()).isEqualTo("option2");
        assertThat(question.getOption3()).isEqualTo("option3");
        assertThat(question.getOption4()).isEqualTo("option4");

    }

    @Test
    @DisplayName("Question must have an correct answer")
    public void questionMustHaveCorrectAnswer() {
        Question question = new Question();

        question.setCorrectAnswer("Berlin");

        assertThat(question.getCorrectAnswer()).isEqualTo("Berlin");
    }

    @Test
    @DisplayName("Question can't have an correct answer as null")
    public void questionCantHaveCorrectAnswerAsNull() {
        Question question = new Question();

        try {
            question.setCorrectAnswer(null);
        }catch (NullPointerException e) {
            throw new RuntimeException("correct Answer is Null");
        }

        assertThat(question.getCorrectAnswer()).isNull();
    }


    @Test
    @DisplayName("Question can have all the attributes")
    public void questionMustHaveAllAttributes() {
        Question question = new Question();

        question.setId(15L);
        question.setQuestionText("what is ....");
        question.setQuestionType(MCQ);
        question.setTestId(123L);
        question.setOption1("option1");
        question.setOption2("option2");
        question.setOption3("option3");
        question.setOption4("option4");
        question.setCorrectAnswer("Berlin");

        assertThat(question.getId()).isEqualTo(15L);
        assertThat(question.getQuestionText()).isEqualTo("what is ....");
        assertThat(question.getQuestionType()).isEqualTo(MCQ);
        assertThat(question.getTestId()).isEqualTo(123L);
        assertThat(question.getOption1()).isEqualTo("option1");
        assertThat(question.getOption2()).isEqualTo("option2");
        assertThat(question.getOption3()).isEqualTo("option3");
        assertThat(question.getOption4()).isEqualTo("option4");
        assertThat(question.getCorrectAnswer()).isEqualTo("Berlin");

    }












}


















