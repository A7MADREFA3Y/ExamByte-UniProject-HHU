package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.example.exambyte.repo.TestsRepository;
import org.example.exambyte.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public TestsDto findTestById(Long testId) {
        Test test = testsRepository.findById(testId).get();
        return mapToTestDto(test);
    }

    @Override
    public void saveTest(TestsDto testsDto) {
        Test test = mapToTest(testsDto);
        testsRepository.save(test);

    }

    public TestsDto mapToTestDto(Test test) {
        return modelMapper.map(test, TestsDto.class);
    }

    public Test mapToTest(TestsDto testsDto) {
        return modelMapper.map(testsDto, Test.class);
    }


}



















