package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ServiceInterface {

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

    void saveTest(TestsDto test);

    List<Test> getAllTests();
}
