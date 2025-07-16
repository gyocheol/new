package com.example.board.controller;

import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/write")
    public String writeBoard(@Valid @ModelAttribute BoardWriteDto dto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        dto.setAuthorName(principal.getName());  // 로그인한 사용자 이름 설정

        boardService.createBoard(dto);
        return "redirect:/";
    }

    @GetMapping("/board/write-form")
    public String showWriteForm() {
        return "board/write-form";
    }

}
