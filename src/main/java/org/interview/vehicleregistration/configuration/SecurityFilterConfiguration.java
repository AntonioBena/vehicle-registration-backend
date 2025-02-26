package org.interview.vehicleregistration.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityFilterConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatchers((matchers) ->
                        matchers
                                .requestMatchers("/api/v1/account/**")
                                //swagger
                                .requestMatchers("/v2/api/docs")
                                .requestMatchers("/v3/api/docs")
                                .requestMatchers("/v3/api-docs/**")
                                .requestMatchers("/swagger-resources")
                                .requestMatchers("/swagger-resources/**")
                                .requestMatchers("/configuration/ui")
                                .requestMatchers("/configuration/security")
                                .requestMatchers("/swagger-ui/**")
                                .requestMatchers("/webjars/**")
                                .requestMatchers("/swagger-ui.html")
                )
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}