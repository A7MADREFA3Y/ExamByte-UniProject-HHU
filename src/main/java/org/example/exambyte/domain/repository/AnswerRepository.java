package org.example.exambyte.domain.repository;

import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository {

    void saveAnswer(Answer answer);

    void saveAll(List<Answer> answers);

    List<Answer> getAllAnswersByUsername(String username);

    List<Answer> getAllAnswersByTestIdAndUsername(Long testId, String username);

    List<Answer> findAllAnswersForFreeText(Long testId, String username);

    Answer findAnswerByTestIdAndQuestion(Long testId, Long questionId);
}
