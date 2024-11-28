package kz.bitlab.middle02redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключение CSRF
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/**").permitAll() // Открытые эндпоинты
                        .requestMatchers("/**").authenticated() // Защищенные эндпоинты
                )
                .httpBasic(); // Включение базовой аутентификации

        return http.build();
    }
}