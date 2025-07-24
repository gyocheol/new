package com.example.board.config;

import com.example.board.service.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((request) ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()      // 포워딩 허용!! 무한 리다이렉트 오류 해결 코드
                        .requestMatchers("/", "/home", "/register", "/login", "/css/**", "/js/**", "/board/view/*").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()       // 정적 리소스 접근 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
//                        .defaultSuccessUrl("/", false)
                        .successHandler((request, response, authentication) -> {
                            String redirectURL = request.getParameter("redirectURL");
                            if (redirectURL != null && !redirectURL.isBlank()) {
                                response.sendRedirect(redirectURL);
                            } else {
                                response.sendRedirect("/");
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}