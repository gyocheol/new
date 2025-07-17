package com.example.board.service;

import com.example.board.dto.BoardResDto;
import com.example.board.dto.BoardUpdateReqDto;
import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

public interface BoardService {

    /**
     * hidden 처리 되지 않은 게시글만 조회
     * Board를 ResDto로 처리
     * @return 게시글 리스트
     */
    List<BoardResDto> findAllBoard();

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
     * @param id
     * @param username
     */
    void deleteBoard(Long id, String username);

    /**
     * 게시글 수정
     * @param id
     * @param username
     * @param dto
     */
    BoardResDto updateBoard(Long id, String username, BoardUpdateReqDto dto);

    /**
     * 게시글 한 개 조회
     * @param id
     * @return
     */
    Board getBoard(Long id);
}
