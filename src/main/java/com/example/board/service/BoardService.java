package com.example.board.service;

import com.example.board.dto.BoardAllListResDto;
import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;

import java.util.List;

public interface BoardService {

    /**
     * hidden 처리 되지 않은 게시글만 조회
     * Board를 ResDto로 처리
     * @return 게시글 리스트
     */
    List<BoardAllListResDto> findAllBoard();

    /**
     * 게시글 hidden 처리
     * @param id
     */
    void hideBoard(Long id);

    /**
     * 게시글 생성
     * @param dto
     */
    void createBoard(BoardWriteDto dto);

    /**
     * 게시글 삭제
     * @param userName
     */
    void deleteBoard(String userName);

    /**
     * 게시글 수정
     * @param userName
     */
    void updateBoard(String userName);
}
