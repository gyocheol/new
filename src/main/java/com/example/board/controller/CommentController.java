package com.example.board.controller;

import com.example.board.dto.CommentReqDto;
import com.example.board.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/write")
    public String writeComment(@RequestParam Long boardId, @RequestParam(required = false) Long parentId, @Valid @ModelAttribute CommentReqDto dto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        commentService.saveComment(boardId, parentId, dto, principal);
        return "redirect:/board/view/" + boardId;
    }
}
