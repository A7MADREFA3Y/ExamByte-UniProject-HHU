package org.example.exambyte.infrasructure.repositoryImp.question;


import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.repository.QuestionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionRepositoryImp implements QuestionRepository {

    private final JpaQuestionRepository jpaQuestionRepository;

    public QuestionRepositoryImp(JpaQuestionRepository jpaQuestionRepository) {
        this.jpaQuestionRepository = jpaQuestionRepository;
    }

    @Override
    public Question findById(Long id) {
        Optional<Question> byId = jpaQuestionRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public void save(Question question) {
        jpaQuestionRepository.save(question);
    }

    @Override
    public List<Question> findByTestId(Long testId) {
        return jpaQuestionRepository.findByTestId(testId);
    }


}
