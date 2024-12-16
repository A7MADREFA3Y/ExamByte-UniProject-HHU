package org.example.exambyte.WebSecurityConfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class  AppUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        OAuth2User originalUser = defaultService.loadUser(userRequest);


        //a List with Our GitHubs username :)
        Set<String> githubLegends = new HashSet<>();
        githubLegends.add("A7MADREFA3Y"); // Korrektor role
        githubLegends.add("uehit100"); // Admin role
        githubLegends.add("bak33jok"); // Admin role
        githubLegends.add("zuhibsparadoxon"); // Admin role


        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));


//        for (String githubsName : githubLegends) {
//
//            if (githubsName.equals(originalUser.getAttribute("login"))) {
//                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//
//            }else if (githubsName.equals(originalUser.getAttribute("login"))) {
//                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_KORREKTOR"));
//            }else{
//                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            }
//        }
//
        return new DefaultOAuth2User(mappedAuthorities, originalUser.getAttributes(), "id");
    }

}






















