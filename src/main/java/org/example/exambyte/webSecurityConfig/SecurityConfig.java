package org.example.exambyte.webSecurityConfig;

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

    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
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
//                               appUserService have the important GitHub users(admin and Korrektor)
                    .userService(appUserService))
//                        this will redirect admin/user/korrektor to the right Controller
            .successHandler(customAuthenticationSuccessHandler()));

        return chainBuilder.build();
    }


}
