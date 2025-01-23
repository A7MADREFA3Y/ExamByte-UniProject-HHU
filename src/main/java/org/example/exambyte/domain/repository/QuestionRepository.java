package org.example.exambyte.domain.repository;


import org.example.exambyte.domain.model.Question;

import java.util.List;

public interface QuestionRepository  {

    Question findById(Long id);

    void save(Question question);

    List<Question> findByTestId(Long testId);

    List<Question> findByTestIdAndHaveTypeAsFREE_TEXT(Long testId);
}
