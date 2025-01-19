package org.example.exambyte.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestResultTest {

    @Test
    @DisplayName("TestResult must have an id")
    public void testMustHaveAnId() {
        TestResult testResult = new TestResult();
        testResult.setId(11L);

        assertThat(testResult.getId()).isEqualTo(11L);

    }


    @Test
    @DisplayName("TestResult Cant have an id as null")
    public void testCantHaveAnIdAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setId(null);
        }catch (NullPointerException e) {}

        assertThat(testResult.getId()).isNull();

    }

    @Test
    @DisplayName("TestResult must have an test id")
    public void testResultMustHaveAnTestId() {
        TestResult testResult = new TestResult();
        testResult.setTestId(121L);

        assertThat(testResult.getTestId()).isEqualTo(121L);

    }


    @Test
    @DisplayName("TestResult Cant have an test id as null")
    public void testResultCantHaveAnTestIdAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setTestId(null);
        }catch (NullPointerException e) {}

        assertThat(testResult.getTestId()).isNull();
    }

    @Test
    @DisplayName("TestResult must have an takenBy")
    public void testMustHaveAnTakenBy() {
        TestResult testResult = new TestResult();

        testResult.setTakenBy("ahmad");

        assertThat(testResult.getTakenBy()).isEqualTo("ahmad");

    }

    @Test
    @DisplayName("TestResult cant have an takenBy As null")
    public void testCantHaveAnTakenByAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setTakenBy(null);
        }catch (NullPointerException e) {}

        assertThat(testResult.getTakenBy()).isNull();
    }

    @Test
    @DisplayName("TestResult must have an answers")
    public void testMustHaveAnswers() {
        TestResult testResult = new TestResult();

        List<Answer> answers = testResult.getAnswers();

        testResult.setAnswers(answers);

        assertThat(testResult.getAnswers()).isEqualTo(answers);

    }

    @Test
    @DisplayName("TestResult can have an answers As null")
    public void testCanHaveAnswersAsNull() {
        TestResult testResult = new TestResult();

        List<Answer> answers = testResult.getAnswers();

        testResult.setAnswers(answers);

        assertThat(testResult.getAnswers()).isNull();
    }


    @Test
    @DisplayName("TestResult must have an submitDate")
    public void testMustHaveSubmitDate() {
        TestResult testResult = new TestResult();

        LocalDateTime submitDate = LocalDateTime.now();

        testResult.setSubmitDate(submitDate);

        assertThat(testResult.getSubmitDate()).isEqualTo(submitDate);
    }

    @Test
    @DisplayName("TestResult cant have an submitDate as null")
    public void testCantHaveSubmitDateAsNull() {
        TestResult testResult = new TestResult();


        try {
            testResult.setSubmitDate(null);
        }catch (NullPointerException e) {}


        assertThat(testResult.getSubmitDate()).isNull();
    }

    @Test
    @DisplayName("Test result can have grade")
    public void testResultCanHaveGrade() {
        TestResult testResult = new TestResult();

        testResult.setGrade(3.5);

        assertThat(testResult.getGrade()).isEqualTo(3.5);

    }

    @Test
    @DisplayName("Test result can have grade As null ")
    public void testResultCanHaveGradeAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setGrade(null);
        }catch (NullPointerException e) {}

        assertThat(testResult.getGrade()).isNull();

    }

    @Test
    @DisplayName("Test result can have grade")
    public void testResultCanHavePassed() {
        TestResult testResult = new TestResult();

        try {
            testResult.setPassed(true);
        }catch (NullPointerException e) {}

        assertThat(testResult.getPassed()).isEqualTo(true);

    }


    @Test
    @DisplayName("Test result can have grade As null")
    public void testResultCanHavePassedAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setPassed(null);
        }catch (NullPointerException e) {}

        assertThat(testResult.getGrade()).isNull();

    }


    @Test
    @DisplayName("Test result have graded false as Default")
    public void testResultHasGradedFalseAsDefault() {
        TestResult testResult = new TestResult();

        assertThat(testResult.getGraded()).isFalse();
    }



    @Test
    @DisplayName("Test result cant graded as null")
    public void testResultCantGradedAsNull() {
        TestResult testResult = new TestResult();

        try {
            testResult.setGraded(null);
        }catch (NullPointerException e) {}


        assertThat(testResult.getGraded()).isNull();
    }

    @Test
    @DisplayName("Test Result can have username when test corrected")
    public void testResultCanHaveUsernameWhenCorrected() {
        TestResult testResult = new TestResult();

        testResult.setCorrectedBy("ahmad");

        assertThat(testResult.getCorrectedBy()).isEqualTo("ahmad");

    }





}
