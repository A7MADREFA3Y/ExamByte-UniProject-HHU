package org.example.exambyte.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ServiceImp implements ServiceInterface {


    @Override
    public boolean checkIfAdmin(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin;
    }

    @Override
    public boolean checkIfKorrektor(Authentication auth) {
        boolean isKorrektor = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_Korrektor") || a.getAuthority().equals("ROLE_ADMIN"));
        return isKorrektor;
    }
}