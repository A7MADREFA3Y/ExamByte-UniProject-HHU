package org.example.exambyte.application.service.answerService;

import org.example.exambyte.application.dto.AnswerDto;
import org.example.exambyte.domain.model.Answer;
import org.example.exambyte.domain.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImp implements AnswerServiceInterface{

    private final AnswerRepository answerRepository;

    public AnswerServiceImp(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    public void saveAnswer(AnswerDto answerDto) {
        Answer answer = mapToAnswer(answerDto);
        answerRepository.saveAnswer(answer);
    }


    private Answer mapToAnswer(AnswerDto answerDto) {
        return Answer.builder()
                .testId(answerDto.getTestId())
                .questionId(answerDto.getQuestionId())
                .answerText(answerDto.getAnswerText())
                .takenBy(answerDto.getTakenBy())
                .correctedAnswer(answerDto.getCorrectedAnswer())
                .build();
    }


    @Override
    public void updateAnswer(AnswerDto answerDto) {
        Answer answerByTestIdAndQuestion = answerRepository.findAnswerByTestIdAndQuestion(answerDto.getTestId(), answerDto.getQuestionId());

        answerByTestIdAndQuestion.setId(answerByTestIdAndQuestion.getId());
        answerByTestIdAndQuestion.setQuestionId(answerDto.getQuestionId());
        answerByTestIdAndQuestion.setTestId(answerDto.getTestId());
        answerByTestIdAndQuestion.setAnswerText(answerDto.getAnswerText());
        answerByTestIdAndQuestion.setTakenBy(answerDto.getTakenBy());
        answerByTestIdAndQuestion.setCorrectedAnswer(answerDto.getCorrectedAnswer());

        answerRepository.saveAnswer(answerByTestIdAndQuestion);
    }




    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDto(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.getAllAnswersByTestIdAndUsername(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
    }

    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.findAllAnswersForFreeText(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
    }

    @Override
    public boolean answerHaveBeenNOTSubmittedBefore(String takenBy, Long questionId) {
        return answerRepository.findAnyAnswerFromAnswerIdAndQuestionId(takenBy, questionId);
    }

    private List<AnswerDto> mapAnswerToAnswerDto(List<Answer> answers) {
        List<AnswerDto> answerDtos = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerDto answerDto = mapAnswerToDto(answer);
            answerDtos.add(answerDto);
        }
        return answerDtos;
    }

    private AnswerDto mapAnswerToDto(Answer answer) {
        return AnswerDto.builder()
                .testId(answer.getTestId())
                .questionId(answer.getQuestionId())
                .answerText(answer.getAnswerText())
                .takenBy(answer.getTakenBy())
                .correctedAnswer(answer.getCorrectedAnswer())
                .build();
    }
}
