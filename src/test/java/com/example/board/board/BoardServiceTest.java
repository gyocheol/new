package com.example.board.board;

import com.example.board.dto.BoardUpdateReqDto;
import com.example.board.dto.BoardWriteDto;
import com.example.board.dto.CommentResDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import com.example.board.service.BoardServiceImpl;
import com.example.board.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
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
    @Mock
    private CommentService commentService;

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

    @Test
    void update_정상수정() {
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        BoardUpdateReqDto dto = new BoardUpdateReqDto();
        dto.setTitle("새 제목");
        dto.setContent("새 내용");

        boardService.updateBoard(1L, "testuser", dto);

        assertThat(board.getTitle()).isEqualTo("새 제목");
        assertThat(board.getContent()).isEqualTo("새 내용");
        verify(boardRepository).save(board);                            // verify(boardRepository.save(board)); 는 boardRepository.save(board)의 결과를 던지기 기대한 mock 객체가 아니라 오류 발생 따라서 왼쪽의 코드와 같이 mock 객체를 던지고 뒤의 메서드가 호출 되는지 확인해야한다.
    }

    @Test
    void getBoard_정상조회() {
        // 1. 의존 모킹 : 게시글 조회
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        // 2. 의존 모킹 : 댓글 조회
        CommentResDto commentDto = CommentResDto.builder()
                .content("댓글 내용")
                .author("댓글 작성자")
                .hidden(false)
                .parent(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
        when(commentService.findAllComment(1L)).thenReturn(List.of(commentDto));

        // 3. 모델 생성 : 서비스가 담을 데이터를 저장할 model 생성
        Model model = new ConcurrentModel();

        // 4. 실행 : 서비스 메서드 호출
        boardService.getBoard(1L, model, () -> "testUser");

        // 5. 검증
        assertThat(model.getAttribute("board")).isEqualTo(board);
        assertThat(model.getAttribute("comments")).isInstanceOf(List.class);
        Object commentsObj = model.getAttribute("comments");
        assertThat(commentsObj).isInstanceOf(List.class);

        // Object -> List<CommentResDto> 안전하게 변환
        List<?> rawComments = (List<?>) commentsObj;
        List<CommentResDto> comments = rawComments.stream()
                .map(o -> {
                    assertThat(o).isInstanceOf(CommentResDto.class); // 개별 요소 타입 검증
                    return (CommentResDto) o;
                })
                .toList();
        assertThat(comments).contains(commentDto);
        assertThat(model.getAttribute("loginUsername")).isEqualTo("testUser");
    }
}
