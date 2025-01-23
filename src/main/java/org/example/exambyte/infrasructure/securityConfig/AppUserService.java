package org.example.exambyte.infrasructure.securityConfig;

import org.example.exambyte.domain.model.Role;
import org.example.exambyte.domain.model.User;

import org.example.exambyte.domain.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class  AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final UserRepository userRepository;

    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String githubId = oAuth2User.getAttribute("id").toString();
        String githubUsername = oAuth2User.getAttribute("login");


        User user = userRepository.findByGithubId(githubId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .githubId(githubId)
                            .githubUsername(githubUsername)
                            .role(Role.USER)
                            .build();
                    return userRepository.save(newUser);
                });


        return new DefaultOAuth2User(Collections.singleton(() -> "ROLE_" + user.getRole().name()),
                oAuth2User.getAttributes(), "id");



    }

}






















