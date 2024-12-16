package org.example.exambyte.WebSecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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
                                .requestMatchers("/","/css/*").permitAll()
                                .requestMatchers("/adminDashBoard/**").hasRole("ADMIN")
                                .requestMatchers("/KorrektorDashBoard/**").hasAnyRole("KORREKTOR","ADMIN")
                                .requestMatchers("/userDashBoard/**").hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated())
//                .formLogin(withDefaults())

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(appUserService))
                        .successHandler(customAuthenticationSuccessHandler()));

        return chainBuilder.build();
    }

    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
