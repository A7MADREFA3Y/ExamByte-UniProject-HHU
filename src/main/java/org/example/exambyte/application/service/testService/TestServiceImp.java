package org.example.exambyte.application.service.testService;

import jakarta.transaction.Transactional;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.domain.model.Test;
import org.example.exambyte.domain.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImp implements TestServiceInterface{

    private final TestRepository testRepository;

    public TestServiceImp(TestRepository testRepository) {
        this.testRepository = testRepository;
    }


    @Override
    public void saveTest(TestsDto testsDto) {
        Test test = mapToTest(testsDto);
        testRepository.saveTest(test);

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

    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public void deleteTest(Long testId) {
        testRepository.deleteById(testId);

    }


    //    it works but no but still without testing
    @Override
    public Test findTestById(Long testId) {
        return testRepository.findById(testId);
    }

    @Override
    @Transactional
    public void updateTestFromDto(Long testId, TestsDto testsDto) {
        Test test = testRepository.findById(testId);

        test.setTestName(testsDto.getTestName());
        test.setStartTime(testsDto.getStartTime());
        test.setEndTime(testsDto.getEndTime());
        test.setResultPublicationTime(testsDto.getResultPublicationTime());

        testRepository.saveTest(test);
    }


}
