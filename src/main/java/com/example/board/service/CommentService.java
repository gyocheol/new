package com.example.board.service;

import com.example.board.dto.CommentReqDto;
import com.example.board.dto.CommentResDto;
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

    List<CommentResDto> findAllComment(Long boardId);
}
