package org.example.exambyte.application.service.serviceTest;

import jakarta.transaction.Transactional;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.domain.model.ModelMapperConfig;
import org.example.exambyte.domain.model.Question;
import org.example.exambyte.domain.model.Test;
import org.example.exambyte.domain.repository.TestRepository;
//import org.example.exambyte.infrasructure.repo.UserRepository;
import org.example.exambyte.infrasructure.repositoryImp.question.QuestionRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImp implements ServiceInterface {

    @Autowired
    private ModelMapperConfig modelMapper;

//    private final UserRepository userRepository;

    private final TestRepository testRepository;
    @Autowired
    private QuestionRepositoryImp questionRepositoryImp;


    public ServiceImp( ModelMapperConfig modelMapper, TestRepository testRepository) {
//        this.userRepository = userRepository;
        this.testRepository = testRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean checkIfAdmin(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin;
    }

    @Override
    public boolean checkIfCorrector(Authentication auth) {
        boolean isCorrector = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_CORRECTOR") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isCorrector;
    }

    @Override
    public boolean checkIfUser(Authentication auth) {

        boolean isUser = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_USER") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isUser;
    }



    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);

    }

    @Override
    public Test findTestById(Long testId) {
        Test test = testRepository.findById(testId);
        return mapToTestDto(test);
    }

    @Override
    public void saveTest(TestsDto testsDto) {
        Test test = mapToTest(testsDto);
        testRepository.save(test);
    }


    public String getGithubUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String githubUsername = (String) oauth2User.getAttributes().get("login");
        if (githubUsername == null) {
            throw new IllegalStateException("GitHub username is not available");
        }
        return githubUsername;
    }

    @Override
    public void updateTest(TestsDto testDto) {
        Test test = mapToTest(testDto);
//        testsRepository.save(test);
        testRepository.save(test);
    }


    @Override
    @Transactional
    public void updateTestFromDto(Long testId, TestsDto testsDto) {
        Test test = testRepository.findById(testId);

        test.setTestName(testsDto.getTestName());
        test.setStartTime(testsDto.getStartTime());
        test.setEndTime(testsDto.getEndTime());
        test.setResultPublicationTime(testsDto.getResultPublicationTime());

        testRepository.save(test);
    }





    private Test mapToTest(TestsDto testsDto) {

        return Test.builder()
                .id(testsDto.getId())
                .testName(testsDto.getTestName())
                .createdBy(testsDto.getCreatedBy())
                .startTime(testsDto.getStartTime())
                .endTime(testsDto.getEndTime())
                .resultPublicationTime(testsDto.getResultPublicationTime())
                .build();

    }


    private Test mapToTestDto(Test test) {

        return Test.builder()
                .id(test.getId())
                .testName(test.getTestName())
                .createdBy(test.getCreatedBy())
                .startTime(test.getStartTime())
                .endTime(test.getEndTime())
                .resultPublicationTime(test.getResultPublicationTime())
                .build();

    }


}



















