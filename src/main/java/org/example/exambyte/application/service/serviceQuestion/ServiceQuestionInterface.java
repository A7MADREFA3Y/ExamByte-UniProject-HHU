package org.example.exambyte.application.service.serviceQuestion;

import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.domain.model.Question;

public interface ServiceQuestionInterface {

    Question findQuestionById(Long id);

    void saveQuestion(QuestionDto questionDto);
}
