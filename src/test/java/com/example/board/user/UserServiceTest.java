package com.example.board.user;

import com.example.board.dto.UserRegisterDto;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import com.example.board.service.UserService;
import com.example.board.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * 단위 테스트
 */
public class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach                                                                 // JUnit5에서 제공하는 어노테이션으로, 각 테스트 메서드 실행 전에 항상 먼저 실행되는 메서드를 지정하는 것, 테스트의 독립성을 보장한다.
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);     // new 키워드로 인스턴스화는 구현체를 대상으로 해야한다.
    }

    @Test
    void 회원가입_성공() {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("testuser1");
        dto.setPassword("1234");
        dto.setConfirmPassword("1234");

        when(userRepository.findByUsername("testuser1")).thenReturn(Optional.empty());    // 아이디 조회
        when(passwordEncoder.encode("1234")).thenReturn("encoded_pw");         // 저장된 비밀번호가 인코딩된 값인지 확인

        userService.register(dto);                                                          // 회원가입 수행(행위)

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);                  // ArgumentCaptor 생성은 save(...) 호출 시 전달된 실제 User 인스턴스를 잡아내기 위한 도구
        verify(userRepository).save(captor.capture());                                      // 행위 검증 : save(...)가 적어도 한 번 호출됐는지 확인 / captor.capture()로 전달된 User 포착

        User savedUser = captor.getValue();                                                 // 캡쳐 된 인자를 꺼내서 User 필드 확인

        // 결과(값) 확인
        assertThat(savedUser.getUsername()).isEqualTo("testuser1");
        assertThat(savedUser.getPassword()).isEqualTo("encoded_pw");
        assertThat(savedUser.getRole()).isEqualTo(Role.ROLE_USER);
    }

    @Test
    void 회원가입_실패_비밀번호불일치() {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("testuser1");
        dto.setPassword("1234");
        dto.setConfirmPassword("5678");

        assertThatThrownBy(() -> userService.register(dto))                                 // 실행 시 예외가 던져지는지 감시 / () -> ... 형태의 람다는 실행을 지연시키는 용도
                .isInstanceOf(IllegalArgumentException.class)                               // IllegalArgumentException 은 자바 표준 예외 클래스, 메서드에 전달된 인자의 값이 잘못되었을 때 던지는 예외
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 회원가입_실패_중복아이디() {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("testuser1");
        dto.setPassword("1234");
        dto.setConfirmPassword("1234");

        when(userRepository.findByUsername("testuser1"))
                .thenReturn(Optional.of(User.builder()
                        .username("testuser1")
                        .password("encoded_pw")
                        .role(Role.ROLE_USER)
                        .build()));

        assertThatThrownBy(() -> userService.register(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}
