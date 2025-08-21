package com.example.board.controller;

import com.example.board.dto.BoardResDto;
import com.example.board.service.BoardService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final BoardService boardService;

    /**
     * home으로 다이렉트 할 때 로그인한 상태라면 username을 넘기고,
     * 로그인 유무 상관없이 게시글 목록을 볼 수 있도록 함.
     * @param model
     * @param principal
     * @return username, boardList
     */
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // 로그인한 사용자 이름을 모델로 전달, 없다면 null
        userService.forwardUsername(model, principal);

        // 사용자의 권한을 모델로 전달
        userService.forwardRole(model, principal);

        // 게시글 목록도 모델에 전달
        List<BoardResDto> boards = boardService.findAllBoard();
        model.addAttribute("boardList", boards);

        return "home";
    }
}
