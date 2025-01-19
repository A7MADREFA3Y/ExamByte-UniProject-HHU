package org.example.exambyte.domain.repository;

import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.model.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository {

    void saveAnswer(Answer answer);

    void saveAll(List<Answer> answers);

    List<Answer> getAllAnswersByUsername(String username);
}
