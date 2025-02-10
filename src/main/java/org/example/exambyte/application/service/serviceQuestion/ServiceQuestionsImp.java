package org.example.exambyte.application.service.serviceQuestion;

import org.example.exambyte.application.dto.QuestionDto;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceQuestionsImp implements ServiceQuestionInterface{

    private final QuestionRepository questionRepository;

    public ServiceQuestionsImp(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /***
     *
     * @param id takes the Question ID to search for specific Question
     * @return the Question that have that ID
     */

    @Override
    public Question findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    /***
     *
     * @param questionDto takes Dto and map it to Entity using mapToQuestion methode then save it
     */

    @Override
    public void saveQuestion(QuestionDto questionDto) {
        Question question = mapToQuestion(questionDto);
        questionRepository.save(question);
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

    /***
     *
     * @param testId takes the Test ID to search for all the Question that have the same Test ID
     * @return a List of Questions
     */

    @Override
    public List<Question> getAllQuestionByTestId(Long testId) {
        return questionRepository.findByTestId(testId);
    }

    /***
     *
     * @param testId takes the Test ID to search for all the Question that have the same Test ID,
     *               and they have Type as FREE TEXT
     * @return a List of Questions that are Free Text
     */

    @Override
    public List<Question> getAllQuestionByTestIdAndHaveTypeAsFREE_TEXT(Long testId) {
        return questionRepository.findByTestIdAndHaveTypeAsFREE_TEXT(testId);

    }

    /***
     *
     * @param questionId takes the Question ID to Delete the Question
     */

    @Override
    public void deleteQuestionById(Long questionId) {
        questionRepository.deleteQuestionById(questionId);
    }



}
