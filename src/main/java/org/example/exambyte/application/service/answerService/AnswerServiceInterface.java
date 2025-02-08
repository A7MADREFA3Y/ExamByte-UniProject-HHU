package org.example.exambyte.application.service.answerService;

import org.example.exambyte.application.dto.AnswerDto;

import java.util.List;

public interface AnswerServiceInterface {

    void saveAnswer(AnswerDto answerDto);
//    need to be tested
    void updateAnswer(AnswerDto answerDto);

    List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDto(Long testId, String username);

    List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(Long testId, String username);
//    need to be tested
    boolean answerHaveBeenNOTSubmittedBefore(String takenBy, Long questionId);
}
