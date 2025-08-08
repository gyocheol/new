package com.example.board.controller;

import com.example.board.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping
    public String getMyPage(Model model,
                            Principal principal) {
        myPageService.findAllMyBoard(model, principal);
        myPageService.findAllMyComment(model, principal);
        return "auth/mypage";
    }
}
