package com.example.board.service;

import com.example.board.dto.CommentReqDto;
import com.example.board.dto.CommentResDto;
import com.example.board.dto.CommentUpdateReqDto;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

public interface CommentService {

    /**
     * 댓글 작성
     * @param boardId
     * @param parentId
     * @param dto
     * @param principal
     */
    void saveComment(Long boardId, Long parentId, CommentReqDto dto, Principal principal);

    /**
     * 게시글의 댓글 조회
     * @param boardId
     * @return
     */
    List<CommentResDto> findAllComment(Long boardId);

    /**
     * 자신의 댓글 삭제
     * @param commentId
     * @param principal
     */
    void deleteComment(Long commentId, Principal principal);

    /**
     * 수정 시 원래 이전 데이터 전송
     * @param commentId
     * @param model
     * @param principal
     */
    void getCommentForUpdate(Long commentId, Model model, Principal principal);

    /**
     * 자신의 댓글 수정
     * @param commentId
     * @param principal
     */
    void updateComment(Long commentId, Principal principal, CommentUpdateReqDto dto);
}
