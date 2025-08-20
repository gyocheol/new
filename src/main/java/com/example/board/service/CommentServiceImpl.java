package com.example.board.service;

import com.example.board.config.NewCustomException;
import com.example.board.dto.CommentReqDto;
import com.example.board.dto.CommentResDto;
import com.example.board.dto.CommentUpdateReqDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void saveComment(Long boardId, Long parentId, CommentReqDto dto, Principal principal) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        User author = userRepository.findByUsername(principal.getName()).orElseThrow();

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .board(board)
                .author(author)
                .createdAt(LocalDateTime.now())
                .build();

        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId).orElseThrow();
            comment.setParent(parent);
        }
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResDto> findAllComment(Long boardId) {
        List<Comment> comments = commentRepository.findByBoardIdOrderByCreatedAtAsc(boardId);

        return comments.stream()
                .map(comment -> new CommentResDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor().getUsername(),
                        comment.isHidden(),
                        comment.getParent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public void deleteComment(Long commentId, Principal principal) {
        Comment comment = validationComment(commentId, principal);
        commentRepository.delete(comment);
    }

    @Override
    public void getCommentForUpdate(Long commentId, Model model, Principal principal) {
        Comment comment = validationComment(commentId, principal);

        CommentResDto commentResDto = CommentResDto.builder()
                .content(comment.getContent())
                .author(comment.getAuthor().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
        model.addAttribute("comment", commentResDto);
        model.addAttribute("id", commentId);
    }

    @Override
    public void updateComment(Long commentId, Principal principal, CommentUpdateReqDto dto) {
        Comment comment = validationComment(commentId, principal);

        comment.setContent(dto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }

    /**
     * 자신의 댓글 유효성 확인
     * @param commentId
     * @param principal
     * @return
     */
    private Comment validationComment(Long commentId, Principal principal) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NewCustomException("댓글이 없습니다."));
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new NewCustomException("자신의 댓글이 아닙니다.");
        }
        return comment;
    }
}
