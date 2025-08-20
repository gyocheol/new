package com.example.board.config;

import com.example.board.service.CustomUserDetailsService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;


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
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {
                                // 로그인한 사용자의 권한 중 ROLE_ADMIN이 있는지 확인
                                boolean isAdmin = authentication.getAuthorities().stream()
                                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                                if (isAdmin) {
                                    response.sendRedirect("/admin"); // 관리자는 /admin/ 으로 즉시 리다이렉트
                                } else {
                                    // 기존 로그인 성공 로직 유지: redirectURL 파라미터 확인 후 이동, 없으면 /home
                                    String redirectURL = request.getParameter("redirectURL");
                                    if (redirectURL != null && !redirectURL.isBlank()) {
                                        response.sendRedirect(redirectURL);
                                    } else {
                                        response.sendRedirect("/"); // 일반 사용자는 / 또는 요청했던 페이지로 리다이렉트
                                    }
                                }
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