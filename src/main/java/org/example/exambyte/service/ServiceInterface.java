package org.example.exambyte.service;

import org.springframework.security.core.Authentication;

public interface ServiceInterface{

    boolean checkIfAdmin(Authentication auth);

    boolean checkIfKorrektor(Authentication auth);

    boolean checkIfUser(Authentication auth);

}
