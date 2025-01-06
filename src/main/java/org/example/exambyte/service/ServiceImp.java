package org.example.exambyte.service;

import jakarta.transaction.Transactional;
import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.example.exambyte.repo.TestsRepository;
import org.example.exambyte.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImp implements ServiceInterface {

    @Autowired
    private ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final TestsRepository testsRepository;

    public ServiceImp(UserRepository userRepository, TestsRepository testsRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.testsRepository = testsRepository;
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
        return testsRepository.findAll();
    }

    @Override
    public void delete(Long testId) {
        testsRepository.deleteById(testId);

    }

    @Override
    public Test findTestById(Long testId) {
        Test test = testsRepository.findById(testId).get();
        return mapToTestDto(test);
    }

    @Override
    public void saveTest(TestsDto testsDto) {

        Test test = mapToTest(testsDto);

        testsRepository.save(test);

    }


    public String GetGithubAdminUsername() {
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
        testsRepository.save(test);
    }


    @Override
    @Transactional
    public void updateTestFromDto(Long testId, TestsDto testsDto) {
        Test test = testsRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        test.setTestName(testsDto.getTestName());
        test.setStartTime(testsDto.getStartTime());
        test.setEndTime(testsDto.getEndTime());
        test.setResultPublicationTime(testsDto.getResultPublicationTime());

        testsRepository.save(test);
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



















