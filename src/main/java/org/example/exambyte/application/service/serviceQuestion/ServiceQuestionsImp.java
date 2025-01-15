package org.example.exambyte.application.service.serviceQuestion;

import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.repository.QuestionRepository;
import org.example.exambyte.infrasructure.repositoryImp.question.JpaQuestionRepository;
import org.springframework.stereotype.Service;


@Service
public class ServiceQuestionsImp implements ServiceQuestionInterface{

    private final QuestionRepository questionRepository;

    public ServiceQuestionsImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question findQuestionById(Long id) {
        Question question = questionRepository.findById(id);

        return null;
    }
}
