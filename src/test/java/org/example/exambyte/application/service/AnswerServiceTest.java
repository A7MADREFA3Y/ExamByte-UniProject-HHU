package org.example.exambyte.application.service;


import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.application.service.answerService.AnswerServiceImp;
import org.example.exambyte.application.service.serviceTest.ServiceImp;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AnswerServiceTest {

    @InjectMocks
    AnswerServiceImp answerService;

    @InjectMocks
    ServiceImp service;

    @Mock
    AnswerRepository answerRepo;

    @Test
    @DisplayName(" saveAnswer takes AnswerDto and maped it to Answer To save it")
    public void Methode_SaveAnswer_AnswerDto_AnswerToSave() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuestionId(2L);
        answerDto.setTestId(14L);
        answerDto.setAnswerText("what is ...");
        answerDto.setTakenBy("ahmad");
        answerDto.setCorrectedAnswer("6");

        Answer answer = new Answer();

        answerService.saveAnswer(answerDto);

        answerRepo.saveAnswer(answer);

        verify(answerRepo, times(1)).saveAnswer(answer);
    }


    @Test
    @DisplayName("checkIfAllradySubmettBefore Checks if the user submitted the test before Return is true")
    void Methode_CheckIfAllradySubmettBeforeReturnTrue() {
        String username = "ahmad";

        List<Answer> answerList = new ArrayList<>();
        Answer answer = new Answer();
        answer.setTestId(2L);
        answerList.add(answer);
        Answer answer2 = new Answer();
        answer2.setTestId(2L);
        answerList.add(answer2);

        when(answerRepo.getAllAnswersByUsername(username)).thenReturn(answerList);

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(2L);

        boolean submettBefore = service.checkIfAllradySubmettBefore(username, test);

        assertThat(submettBefore).isEqualTo(true);


    }

        @Test
    @DisplayName("checkIfAllradySubmettBefore Checks if the user submitted the test before Return is False")
    void Methode_CheckIfAllradySubmettBeforeReturnFalse() {
        String username = "ahmad";

        List<Answer> answerList = new ArrayList<>();
        Answer answer = new Answer();
        answer.setTestId(2L);
        answerList.add(answer);
        Answer answer2 = new Answer();
        answer2.setTestId(3L);
        answerList.add(answer2);

        when(answerRepo.getAllAnswersByUsername(username)).thenReturn(answerList);

        org.example.exambyte.domain.model.Test test = new org.example.exambyte.domain.model.Test();
        test.setId(15L);

        boolean submettBefore = service.checkIfAllradySubmettBefore(username, test);

        assertThat(submettBefore).isEqualTo(false);

    }


    @Test
    @DisplayName("updateAnswer takes AnswerDto Mappe it to the same Answer then Save it back in the Repo")
    public void Methode_UpdateAnswerDto_AnswerToSave() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setQuestionId(2L);
        answerDto.setTestId(14L);

        Answer answer = new Answer();

        when(answerRepo.findAnswerByTestIdAndQuestion(answerDto.getTestId(), answerDto.getQuestionId())).thenReturn(answer);

        answerService.updateAnswer(answerDto);

        answerRepo.saveAnswer(answer);

        verify(answerRepo, times(2)).saveAnswer(answer);
        assertThat(answer.getQuestionId()).isEqualTo(2L);
        assertThat(answer.getTestId()).isEqualTo(14L);
    }

    @Test
    @DisplayName("getAllAnswersWithTestIdAndUsernameAsDto Get all the Answers using test Id and Username")
    public void Methode_GetAllAnswersWithTestIdAndUsernameAsDto() {
        String username = "ahmad";
        Long testId = 2L;

        List<Answer> answerList = new ArrayList<>();
        Answer answer = new Answer();
        answer.setTestId(testId);
        answer.setTakenBy(username);
        answerList.add(answer);

        Answer answer2 = new Answer();
        answer2.setTestId(testId);
        answer2.setTakenBy(username);
        answerList.add(answer2);

        when(answerRepo.getAllAnswersByTestIdAndUsername(testId, username)).thenReturn(answerList);

        List<AnswerDto> answerDtosList = new ArrayList<>();

        AnswerDto answerDto = AnswerDto.builder()
                .testId(testId)
                .takenBy(username)
                .build();

        answerDtosList.add(answerDto);

        AnswerDto answerDto2 = AnswerDto.builder()
                .testId(testId)
                .takenBy(username)
                .build();

        answerDtosList.add(answerDto2);

        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDto = answerService.getAllAnswersWithTestIdAndUsernameAsDto(testId, username);

        assertThat(allAnswersWithTestIdAndUsernameAsDto).isEqualTo(answerDtosList);

    }

    @Test
    @DisplayName("getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT")
    public void Methode_GetAllAnswersWithTestIdAndUsernameAsDtoAndFreeText() {
        String username = "ahmad";
        Long testId = 2L;

        List<Answer> answerList = new ArrayList<>();

        Answer answer = new Answer();
        answer.setId(4L);
        answer.setQuestionId(1L);
        answer.setTestId(testId);
        answer.setTakenBy(username);

        answer.setAnswerText("the answer is 1");
        answerList.add(answer);

        Answer answer2 = new Answer();
        answer2.setId(5L);
        answer2.setQuestionId(2L);
        answer2.setTestId(testId);
        answer2.setTakenBy(username);
        answer.setAnswerText("the answer is 2");
        answerList.add(answer2);

        when(answerRepo.findAllAnswersForFreeText(testId, username)).thenReturn(answerList);

        List<AnswerDto> allAnswersWithTestIdAndUsernameAsDtoAndFREETEXT = answerService.getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(testId, username);

        assertThat(allAnswersWithTestIdAndUsernameAsDtoAndFREETEXT.size()).isEqualTo(answerList.size());
        assertThat(allAnswersWithTestIdAndUsernameAsDtoAndFREETEXT.getFirst().getTestId()).isEqualTo(answerList.getFirst().getTestId());
        assertThat(allAnswersWithTestIdAndUsernameAsDtoAndFREETEXT.getFirst().getTakenBy()).isEqualTo(answerList.getFirst().getTakenBy());
        assertThat(allAnswersWithTestIdAndUsernameAsDtoAndFREETEXT.getFirst().getQuestionId()).isEqualTo(answerList.getFirst().getQuestionId());
    }

}
