package com.example.board.board;

import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import com.example.board.service.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardServiceTest {

    @Mock                                                       // 가짜 객체(mock)를 생성, Setup에 있는 코드가 없으면 작동하지 않음 : 1. 가짜 의존성 생성
    private BoardRepository boardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks                                                // 테스트할 실제 객체를 생성하고 여기에 @Mock로 만든 가짜 객체들을 자동으로 주입, Spring의 @Autowired와 비슷한 역할 : 2. 테스트할 클래스 생성 + 1번 가짜 의존성 자동 주입
    private BoardServiceImpl boardService;

    // 클래스 필드로 선언해서 모든 테스트 메서드에서 공통적으로 사용 가능하게 만듦. BeforeEach에서 테스트마다 fresh 객체를 만들어주도록.
    private User user;
    private Board board;

    @BeforeEach                                                 // 4. 1~3번의 과정을 각 테스트 실행 전에 새로 수행하여 테스트의 독립성 보장
    void setUp() {
        MockitoAnnotations.openMocks(this);             // @Mock 객체를 자동으로 만들어줌 / UserServiceTest의 mock(...) 처럼 작성하지 않아도 됨! / 장점으로 가독성이 좋고, 코드 수정이 적어짐 : 3. Mock, InjectMocks를 실제로 초기화(가짜 객체(Mock) 생성 + 주입)

        user = new User();
        user.setUsername("testuser");
        user.setRole(Role.ROLE_USER);

        board = Board.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .createdAt(LocalDateTime.now())
                .author(user)
                .build();
    }

    @Test
    void createBoard_정상생성() {
        // 1. 입력 DTO 준비
        BoardWriteDto dto = new BoardWriteDto();
        dto.setTitle("테스트 제목");
        dto.setContent("테스트 내용");
        dto.setAuthorName("testuser");
        
        // 2. 의존 모킹 : 작성자 조회
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));                      // when(...).thenReturn(...) : 고정된 리턴 값을 줄 때 사용
        
        // 3. 의존 모킹 : 저장 시 동작 커스텀
        when(boardRepository.save(Mockito.any(Board.class))).thenAnswer(invocation -> {     // when(...).thenAnswer(...) : 호출 순간의 인자값을 받아 동적으로 처리할 때 사용
            Board saved = invocation.getArgument(0);      // save(여기 들어온 Board)                       // Mockito.any(Board.class) : save에 어떤 Board가 오든 매칭.
            saved.setId(99L);                               // DB가 PK 채우는 것 처럼 ID 부여
            return saved;                                   // Repository가 반환
        });

        // 4. 실행(행위) : 서비스 메서드 호출
        Long boardId = boardService.createBoard(dto);

        // 5. 1차 검증 : 반환값 ID 확인
        assertThat(boardId).isEqualTo(99L);

        // 6. save 호출 시 전달된 실제 Board 캡쳐
        ArgumentCaptor<Board> captor = ArgumentCaptor.forClass(Board.class);                                // ArgumentCaptor<Board>와 verify와 함께 써서, save에 전달된 객체를 빼와 필드를 검증
        verify(boardRepository).save(captor.capture());
        Board savedBoard = captor.getValue();

        // 7. 2차 검증 : 저장 대상 Board의 필드 값 검증
        assertThat(savedBoard.getTitle()).isEqualTo(dto.getTitle());
        assertThat(savedBoard.getContent()).isEqualTo(dto.getContent());
        assertThat(savedBoard.getAuthor()).isEqualTo(user);
        assertThat(savedBoard.getCreatedAt()).isNotNull();
    }
}
