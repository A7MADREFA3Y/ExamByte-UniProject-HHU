package org.example.exambyte.application.service.serviceQuestion;

import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.domain.model.Question;

import java.util.List;

public interface ServiceQuestionInterface {

    Question findQuestionById(Long id);

    void saveQuestion(QuestionDto questionDto);

    List<Question> getAllQuestionByTestId(Long testId);

    List<Question> getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(Long testId);
}
