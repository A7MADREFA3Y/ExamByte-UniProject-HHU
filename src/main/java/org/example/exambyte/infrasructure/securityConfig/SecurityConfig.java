package org.example.exambyte.infrasructure.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserService appUserService;

    public SecurityConfig(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
        chainBuilder.authorizeHttpRequests(
            configure -> configure
                    .requestMatchers("/","/css/**", "/login", "/oauth2/**","/userDashBoard/**").permitAll()
                    .requestMatchers("/adminDashBoard/**").hasRole("ADMIN")
                    .requestMatchers("/correctorDashBoard/**").hasAnyRole("CORRECTOR","ADMIN")
//                    .requestMatchers("/userDashBoard/**").hasAnyRole("USER","ADMIN")
                    .anyRequest().authenticated())

                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(appUserService)
                        )
                        .defaultSuccessUrl("/redirctingWithUser", true)
                );



        return chainBuilder.build();
    }


}
