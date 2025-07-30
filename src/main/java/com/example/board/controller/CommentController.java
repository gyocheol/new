package com.example.board.controller;

import com.example.board.dto.CommentReqDto;
import com.example.board.dto.CommentUpdateReqDto;
import com.example.board.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param boardId
     * @param parentId
     * @param dto
     * @param principal
     * @return
     */
    @PostMapping("/write")
    public String writeComment(@RequestParam Long boardId,
                               @RequestParam(required = false) Long parentId,       // 대댓글에만 parentId가 들어가기 때문에 값이 안 들어오는 일반 댓글을 위해 required = false를 추가해야한다.
                               @Valid @ModelAttribute CommentReqDto dto,
                               BindingResult bindingResult,
                               Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (parentId == null) {

        }

        commentService.saveComment(boardId, parentId, dto, principal);
        return "redirect:/board/view/" + boardId;
    }

    /**
     * 댓글 삭제
     * @param commentId
     * @param boardId
     * @param principal
     * @return
     */
    @PostMapping("/delete/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @RequestParam Long boardId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        commentService.deleteComment(commentId, principal);
        return "redirect:/board/view/" + boardId;
    }

    /**
     * 댓글 수정
     * @param commentId
     * @param boardId
     * @param dto
     * @param principal
     * @return
     */
    @PostMapping("/edit/{commentId}")
    public String updateComment(@PathVariable Long commentId,
                                @RequestParam Long boardId,
                                @Valid @ModelAttribute CommentUpdateReqDto dto,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        commentService.updateComment(commentId, principal, dto);
        return "redirect:/board/view/" + boardId;
    }

    /**
     * 댓글 수정 전 댓글 내용 Get
     * @param commentId
     * @param boardId
     * @param model
     * @param principal
     * @return
     */
    @GetMapping("/edit/{commentId}")
    public String showCommentEditForm(@PathVariable Long commentId, @RequestParam Long boardId, Model model, Principal principal) {
        commentService.getCommentForUpdate(commentId, model, principal);
        model.addAttribute("boardId", boardId);
        return "redirect:/board/view/" + boardId;
    }
}
