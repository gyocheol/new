package com.example.board.controller;

import com.example.board.dto.UserGroupResDto;
import com.example.board.service.BoardService;
import com.example.board.service.MyPageService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final MyPageService myPageService;
    private final BoardService boardService;

    @GetMapping
    public String adminPage(Model model) {
        UserGroupResDto userGroupResDto = userService.getAllUserGroup();
        model.addAttribute("userGroup", userGroupResDto);
        return "admin/user";
    }

    @GetMapping("/detail/{userId}")
    public String getUserDetail(@PathVariable String userId, @RequestParam String username, Model model) {
        myPageService.findAllMyBoard(model, username);
        myPageService.findAllMyComment(model, username);
        model.addAttribute("targetUsername", username);
        return "admin/userDetail";
    }

    @PostMapping("/toggle-hidden/{boardId}")
    public ResponseEntity<String> toggleHidden(@PathVariable Long boardId, Principal principal) {
        boolean hidden = boardService.toggleHidden(boardId, principal);
        return ResponseEntity.ok(hidden ? "숨김 처리됨":"숨김 해제됨");
    }
}
