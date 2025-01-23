package org.example.exambyte.application.service.testService;

import jakarta.validation.Valid;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.domain.model.Test;

import java.util.List;

public interface TestServiceInterface {

    void saveTest(TestsDto test);

    List<Test> getAllTests();

    void deleteTest(Long testId);

    Test findTestById(Long testId);

    void updateTestFromDto(Long testId, @Valid TestsDto testsDto);



}
