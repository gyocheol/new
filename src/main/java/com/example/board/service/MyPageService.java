package com.example.board.service;

import com.example.board.dto.MyBoardResDto;
import com.example.board.dto.MyCommentResDto;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

public interface MyPageService {

    /**
     * 내가 쓴 게시글 조회
     * @param model
     * @param principal
     * @return
     */
    List<MyBoardResDto> findAllMyBoard(Model model, Principal principal);

    /**
     * 내가 쓴 댓글 조회
     * @param model
     * @param principal
     * @return
     */
    List<MyCommentResDto> findAllMyComment(Model model, Principal principal);
}
