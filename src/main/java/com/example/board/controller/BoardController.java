package com.example.board.controller;

import com.example.board.dto.BoardUpdateReqDto;
import com.example.board.dto.BoardWriteDto;
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

        Long id = boardService.createBoard(dto);
        return "redirect:/board/view/" + id;
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
    @PostMapping("/board/delete/{id}")     // HTML <form> 태그는 delete method를 지원하지 않는다.
    public String deleteBoard(@RequestParam Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        boardService.deleteBoard(id, principal.getName());
        return "redirect:/";
    }

    /**
     * 게시글 한 개 조회
     * @param id
     * @param model
     * @param principal
     * @return view.jsp
     */
    @GetMapping("/board/view/{id}")
    public String viewBoard(@PathVariable Long id, Model model, Principal principal) {
        boardService.getBoard(id, model, principal);
        return "board/view";
    }

    /**
     * 게시글 수정 전 게시글 내용 Get
     * @param id
     * @param model
     * @param principal
     * @return edit-form.jsp
     */
    @GetMapping("/board/edit/{id}")
    public String showBoardEditForm(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        boardService.getBoardForUpdate(id, model, principal);
        return "board/edit-form";
    }

    /**
     * 게시글 수정
     * @param id
     * @param dto
     * @param principal
     * @return
     */
    @PostMapping("/board/edit/{id}")
    public String updateBoard(@PathVariable Long id, @Valid @ModelAttribute BoardUpdateReqDto dto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        boardService.updateBoard(id, principal.getName(), dto);
        return "redirect:/board/view/" +id;
    }
}
