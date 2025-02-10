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


    /***
     *
     * @param answerDto takes the AnswerDto form the controller then map it to entity then save
     *
     */

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


    /***
     *
     * @param answerDto takes the AnswerDto from controller and call the Entity from Repository then write
     *                  the new infos on it then save it again
     */

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


    /***
     *
     * @param testId  to search for the Answers Submitted in this test
     * @param username to search for the Answers Submitted in the test
     * @return Using mapAnswerToAnswerDto and mapAnswerToDto it return the All Answers as Dto
     */

    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDto(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.getAllAnswersByTestIdAndUsername(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
    }


    /***
     *
     * @param testId  to search for the Answers Submitted in this test
     * @param username to search for the Answers Submitted in the test
     * @return Using mapAnswerToAnswerDto and mapAnswerToDto it return the All Answers that are Free Text as Dto
     */

    @Override
    public List<AnswerDto> getAllAnswersWithTestIdAndUsernameAsDtoAndFREETEXT(Long testId, String username) {
        List<Answer> allAnswersByTestIdAndUsername = answerRepository.findAllAnswersForFreeText(testId, username);
        return mapAnswerToAnswerDto(allAnswersByTestIdAndUsername);
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

    /***
     *
     * @param takenBy takes the Username to search for specific Answer that is submitted
     * @param questionId takes the Question ID to check specific Answer that is submitted
     * @return boolean if answer Submitted return ture if not then false
     */

    @Override
    public boolean answerHaveBeenNOTSubmittedBefore(String takenBy, Long questionId) {
        return answerRepository.findAnyAnswerFromAnswerIdAndQuestionId(takenBy, questionId);
    }

    /***
     *
     * @param testId to search for the Answer
     * @param githubUsername to search for the Answer
     * @return A List of Answers 
     */

    @Override
    public List<Answer> getAllAnswersWithTestIdAndUsername(Long testId, String githubUsername) {
        return answerRepository.getAllAnswersByTestIdAndUsername(testId, githubUsername);
    }

}
