package com.example.board.service;

import com.example.board.dto.BoardResDto;
import com.example.board.dto.BoardUpdateReqDto;
import com.example.board.dto.BoardWriteDto;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

public interface BoardService {

    /**
     * role에 따라 히든 처리 된 게시판과 전체 게시판 조회
     * Board를 ResDto로 처리
     * @return 게시글 리스트
     */
    List<BoardResDto> findBoardsByRole(Principal principal);

    /**
     * 게시글 hidden 처리 TODO : 관리자 설정 이후 구현하기
     * @param id
     * @param principal
     * return boolean
     */
    boolean toggleHidden(Long id, Principal principal);

    /**
     * 게시글 생성
     * @param dto
     * @return id
     */
    Long createBoard(BoardWriteDto dto);

    /**
     * 게시글 삭제
     * @param id
     * @param username
     */
    void deleteBoard(Long id, String username);

    /**
     * 수정을 위한 이전 게시글 데이터 전송
     * @param id
     * @param model
     * @param principal
     * @return
     */
    void getBoardForUpdate(Long id, Model model, Principal principal);

    /**
     * 게시글 수정
     * @param id
     * @param username
     * @param dto
     */
    void updateBoard(Long id, String username, BoardUpdateReqDto dto);

    /**
     * 게시글 한 개 조회
     * @param id
     * @return
     */
    void getBoard(Long id, Model model, Principal principal);
}
