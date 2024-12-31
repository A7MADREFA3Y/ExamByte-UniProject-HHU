package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.example.exambyte.model.User;
import org.springframework.security.core.Authentication;

public interface ServiceInterface{

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

}
