package org.example.exambyte.service.userService;

import org.example.exambyte.model.Test;
import org.springframework.security.core.Authentication;

public interface ServiceInterface {

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

}
