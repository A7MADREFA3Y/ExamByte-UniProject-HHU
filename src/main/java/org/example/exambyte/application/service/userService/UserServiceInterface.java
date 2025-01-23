package org.example.exambyte.application.service.userService;

import org.springframework.security.core.Authentication;

public interface UserServiceInterface {

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfCorrector(Authentication auth);

    boolean checkIfUser(Authentication auth);

    String getGithubUsername();

}
