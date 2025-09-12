package com.example.board.comment;

import com.example.board.dto.CommentReqDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import com.example.board.service.CommentService;
import com.example.board.service.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private Principal principal;

    private CommentServiceImpl commentService;
    private User user;
    private Board board;
    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentServiceImpl(commentRepository, boardRepository, userRepository);
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
    void createComment_정상생성() {
        CommentReqDto dto = new CommentReqDto();
        dto.setContent("댓글");

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        when(commentRepository.save(Mockito.any(Comment.class))).thenAnswer(invocation -> {
            Comment saved = invocation.getArgument(0);
            saved.setId(99L);
            return saved;
        });
        commentService.saveComment(1L, null, dto, principal);

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(captor.capture());
        Comment savedComment = captor.getValue();

        assertThat(savedComment.getContent()).isEqualTo("댓글");
        assertThat(savedComment.getBoard()).isEqualTo(board);
        assertThat(savedComment.getAuthor()).isEqualTo(user);
        assertThat(savedComment.getParent()).isNull();
    }

    @Test
    void deleteComment_정상삭제() {
        comment = new Comment();
        comment.setId(100L);
        comment.setContent("기존 댓글");
        comment.setAuthor(user);

        when(principal.getName()).thenReturn("testuser");
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        commentService.deleteComment(comment.getId(), principal);
        verify(commentRepository).delete(comment);
    }

    @Test
    void deleteComment_자신의_글이_아닐때() {
        comment = new Comment();
        comment.setId(100L);
        comment.setContent("기존 댓글");
        comment.setAuthor(user);

        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setUsername("otherUser");
        otherUser.setRole(Role.ROLE_USER);

        comment.setAuthor(otherUser);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

        assertThatThrownBy(() -> commentService.deleteComment(comment.getId(), principal))
                .isInstanceOf(RuntimeException.class); // 실제 예외 타입으로 변경
        verify(commentRepository, never()).delete(any());
    }
}
