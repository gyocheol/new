package com.example.board.service;

import com.example.board.dto.MyBoardResDto;
import com.example.board.dto.MyCommentResDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
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

    @Override
    public List<MyBoardResDto> findAllMyBoard(Model model, Principal principal) {
        return List.of();
    }

    @Override
    public List<MyCommentResDto> findAllMyComment(Model model, Principal principal) {
        return List.of();
    }
}
