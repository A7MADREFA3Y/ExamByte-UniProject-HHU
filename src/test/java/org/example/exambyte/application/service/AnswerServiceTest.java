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
    
    
    
    
    

}
