package com.example.board.service;

import org.springframework.ui.Model;

import java.security.Principal;

public interface MyPageService {

    /**
     * 내가 쓴 게시글 조회
     * @param model
     * @param username
     */
    void findAllMyBoard(Model model, String username);

    /**
     * 내가 쓴 댓글 조회
     * @param model
     * @param username
     */
    void findAllMyComment(Model model, String username);
}
