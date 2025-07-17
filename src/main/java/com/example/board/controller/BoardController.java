package com.example.board.controller;

import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 새 게시글 작성
     * @param dto
     * @param principal
     * @return home.jsp
     */
    @PostMapping("/write")
    public String writeBoard(@Valid @ModelAttribute BoardWriteDto dto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        dto.setAuthorName(principal.getName());  // 로그인한 사용자 이름 설정

        boardService.createBoard(dto);
        return "redirect:/";
    }

    /**
     * 게시글 작성 form
     * @return write-form.jsp
     */
    @GetMapping("/board/write-form")
    public String showWriteForm() {
        return "board/write-form";
    }

    /**
     * 게시글 삭제
     * @param id
     * @param principal
     * @return home.jsp
     */
    @DeleteMapping("/board/delete")
    public String deleteBoard(@RequestParam Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        boardService.deleteBoard(id, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String viewBoard(@PathVariable Long id, Model model, Principal principal) {
        String loginUsername = (principal != null) ? principal.getName() : "";

        Board board = boardService.getBoard(id);

        model.addAttribute("board", board);
        model.addAttribute("loginUsername", loginUsername);

        return "board/view";
    }

}
