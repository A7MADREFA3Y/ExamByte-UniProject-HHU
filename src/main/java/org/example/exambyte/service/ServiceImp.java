package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.example.exambyte.repo.TestsRepository;
import org.example.exambyte.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImp implements ServiceInterface {

    private final UserRepository userRepository;

    private final TestsRepository testsRepository;

    public ServiceImp(UserRepository userRepository, TestsRepository testsRepository) {
        this.userRepository = userRepository;
        this.testsRepository = testsRepository;
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
    public void saveTest(TestsDto testsDto) {
        Test test = mapToTest(testsDto);
        testsRepository.save(test);

    }

    public Test mapToTest(TestsDto testsDto) {
        return Test.builder()
                .testName(testsDto.getTestName())
                .startTime(testsDto.getStartTime())
                .endTime(testsDto.getEndTime())
                .resultPublicationTime(testsDto.getResultPublicationTime())
                .createdBy(testsDto.getCreatedBy())
                .build();
    }
}



















