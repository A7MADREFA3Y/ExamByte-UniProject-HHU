package org.example.exambyte.application.service;

import org.example.exambyte.ExambyteApplication;
import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.application.service.serviceQuestion.ServiceQuestionsImp;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.example.exambyte.domain.model.QuestionType.MCQ;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = ExambyteApplication.class)
public class QuestionServiceTest {


    @InjectMocks
    private ServiceQuestionsImp service; // The service being tested

    @Mock
    private QuestionRepository repo; // Mocked dependency

    @Test
    @DisplayName("Testing findQuestionById Methode to find qurstions by Test Id")
    public void testFindQuestionById(){

        Long questionId = 1L;
        Question question = Question.builder()
                .id(1L)
                .questionText("Question Text")
                .questionType(MCQ)
                .option1("Option 1")
                .option2("Option 2")
                .correctAnswer("A")
                .build();

        when(repo.findById(questionId)).thenReturn(question);

        Question questionById = service.findQuestionById(1L);

        assertThat(questionById.getId()).isEqualTo(question.getId());
    }

    @Test
    @DisplayName("saveQuestion is void methode to save questions")
    public void testSaveQuestion(){

        QuestionDto questionDto = new QuestionDto();
        Question expectedQuestion = new Question();

        service.saveQuestion(questionDto);

        repo.save(expectedQuestion);

        verify(repo, times(1)).save(expectedQuestion);
    }

    @Test
    @DisplayName("getAllQuestionByTestId return List form questions that have the same test ID")
    public void testingGetAllQuestionByTestId(){
        Long testId = 121L;


        Question question = Question.builder()
                .id(1L)
                .testId(testId)
                .questionText("Question Text")
                .questionType(MCQ)
                .option1("Option 1")
                .option2("Option 2")
                .correctAnswer("A")
                .build();

        Question question2 = Question.builder()
                .id(2L)
                .testId(testId)
                .questionText("Question Text")
                .questionType(MCQ)
                .option1("Option 1")
                .option2("Option 2")
                .correctAnswer("A")
                .build();

        List<Question> questionsList = List.of(question , question2);

        when(repo.findByTestId(testId)).thenReturn(questionsList);

        List<Question> allQuestionByTestId = service.getAllQuestionByTestId(testId);

        assertThat(allQuestionByTestId).isEqualTo(questionsList);
    }


}
