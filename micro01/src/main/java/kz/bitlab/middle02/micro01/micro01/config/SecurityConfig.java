package kz.bitlab.middle02.micro01.micro01.config;

import kz.bitlab.middle02.micro01.micro01.converter.KeycloakRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/user/create").permitAll()
                                .requestMatchers("/api/user/token").permitAll()
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).oauth2ResourceServer(
                        o -> o
//                                .jwt(Customizer.withDefaults())
                                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(keycloakRoleConverter()))
                );
        return http.build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public KeycloakRoleConverter keycloakRoleConverter() {
        return new KeycloakRoleConverter();
    }


}
