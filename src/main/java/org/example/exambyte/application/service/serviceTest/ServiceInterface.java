package org.example.exambyte.application.service.serviceTest;

import jakarta.validation.Valid;
import org.example.exambyte.application.dto.TestsDto;
import org.example.exambyte.domain.model.Test;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ServiceInterface {

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

    void saveTest(TestsDto test);

    List<Test> getAllTests();

    void deleteTest(Long testId);

    Test findTestById(Long testId);

    String GetGithubAdminUsername();

    void updateTest(@Valid TestsDto testDto);

    void updateTestFromDto(Long testId, @Valid TestsDto testsDto);

}
