package org.example.exambyte.service;

import org.example.exambyte.dto.TestsDto;
import org.example.exambyte.model.Test;
import org.example.exambyte.repo.TestsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ServiceImp implements ServiceInterface {

    private final TestsRepository repo;

    public ServiceImp(TestsRepository repo) {
        this.repo = repo;
    }

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
                        a.getAuthority().equals("ROLE_KORREKTOR") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isKorrektor;
    }

    @Override
    public boolean checkIfUser(Authentication auth) {

        boolean isUser = auth.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_USER") ||
                        a.getAuthority().equals("ROLE_ADMIN"));

        return isUser;
    }

}



















