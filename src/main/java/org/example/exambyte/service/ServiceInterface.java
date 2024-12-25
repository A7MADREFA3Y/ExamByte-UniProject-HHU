package org.example.exambyte.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

public interface ServiceInterface{

    boolean checkIfAdmin(Authentication auth);

}
