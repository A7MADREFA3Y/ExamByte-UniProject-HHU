package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Tests;
import org.springframework.security.core.Authentication;

public interface ServiceInterface{

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfKorrektor(Authentication auth);

    boolean checkIfUser(Authentication auth);

    Tests saveTest(TestsDto testsDto);

}
