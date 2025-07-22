package com.example.board.service;

import com.example.board.dto.CommentReqDto;
import org.springframework.ui.Model;

import java.security.Principal;

public interface CommentService {

    /**
     * 댓글 작성
     * @param boardId
     * @param parentId
     * @param dto
     * @param principal
     */
    void saveComment(Long boardId, Long parentId, CommentReqDto dto, Principal principal);
}
