package com.example.board.service;

import com.example.board.entity.Board;

import java.util.List;

public interface BoardService {

    /**
     * hidden 처리 되지 않은 게시글만 조회
     * @return 게시글 리스트
     */
    List<Board> findAllBoard();

    /**
     * 게시글 hidden 처리
     * @param id
     */
    void hideBoard(Long id);
}
