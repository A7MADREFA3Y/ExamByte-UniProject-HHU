package org.example.exambyte.infrasructure.repositoryImp.answer;

import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerRepositoryImp implements AnswerRepository {

    private final JpaAnswerRepository jpaAnswerRepository;

    public AnswerRepositoryImp(JpaAnswerRepository jpaAnswerRepository) {
        this.jpaAnswerRepository = jpaAnswerRepository;
    }

    @Override
    public void saveAnswer(Answer answer) {
        jpaAnswerRepository.save(answer);
    }

    @Override
    public void saveAll(List<Answer> answers) {
        jpaAnswerRepository.saveAll(answers);
    }

    @Override
    public List<Answer> getAllAnswersByUsername(String username) {
        return jpaAnswerRepository.findAllAnswersByUserName(username);
    }

    @Override
    public List<Answer> getAllAnswersByTestIdAndUsername(Long testId, String username) {
        return jpaAnswerRepository.findAllAnswersByTestIdWithUsername(testId, username);
    }

    @Override
    public List<Answer> findAllAnswersForFreeText(Long testId, String username) {
        return jpaAnswerRepository.getAllAnswersForFreeText(testId, username);
    }

}
