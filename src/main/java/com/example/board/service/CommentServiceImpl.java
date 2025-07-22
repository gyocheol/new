package com.example.board.service;

import com.example.board.dto.CommentReqDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

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
}
