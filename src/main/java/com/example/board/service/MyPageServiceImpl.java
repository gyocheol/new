package com.example.board.service;

import com.example.board.dto.MyBoardResDto;
import com.example.board.dto.MyCommentResDto;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void findAllMyBoard(Model model, Principal principal) {
        List<Board> boards = boardRepository.findByAuthorOrderByCreatedAtAsc(getUser(principal));
        List<MyBoardResDto> boardList = boards.stream()
                .map(board -> new MyBoardResDto(
                        board.getId(),
                        board.getTitle(),
                        board.getCreatedAt(),
                        board.getUpdatedAt()
                ))
                .toList();
        model.addAttribute("myBoardList", boardList);
    }

    @Override
    public void findAllMyComment(Model model, Principal principal) {
        List<Comment> comments = commentRepository.findByAuthorOrderByCreatedAtAsc(getUser(principal));

        List<MyCommentResDto> commentList = comments.stream()
                .map(comment -> new MyCommentResDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getBoard(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .toList();
        model.addAttribute("myCommentList", commentList);
    }

    private User getUser(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        return user;
    }
}
