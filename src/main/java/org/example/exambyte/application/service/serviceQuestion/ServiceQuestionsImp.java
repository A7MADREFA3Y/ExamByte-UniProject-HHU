package org.example.exambyte.application.service.serviceQuestion;

import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.QuestionType;
import org.example.exambyte.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceQuestionsImp implements ServiceQuestionInterface{

    private final QuestionRepository questionRepository;

    public ServiceQuestionsImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question findQuestionById(Long id) {
        Question question = questionRepository.findById(id);
        return question;
    }

    @Override
    public void saveQuestion(QuestionDto questionDto) {
        Question question = mapToQuestion(questionDto);
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestionByTestId(Long testId) {
        return questionRepository.findByTestId(testId);
    }

    @Override
    public List<Question> getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(Long testId) {
        return questionRepository.findByTestIdAndHaveTypeAsFREE_TEXT(testId);

    }


    private Question mapToQuestion(QuestionDto questionDto) {
        return Question.builder()
                .id(questionDto.getId())
                .questionText(questionDto.getQuestionText())
                .questionType(questionDto.getQuestionType())
                .testId(questionDto.getTestId())
                .option1(questionDto.getOption1())
                .option2(questionDto.getOption2())
                .option3(questionDto.getOption3())
                .option4(questionDto.getOption4())
                .correctAnswer(questionDto.getCorrectAnswer())
                .build();
    }
}
