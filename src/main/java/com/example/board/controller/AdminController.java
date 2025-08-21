package com.example.board.controller;

import com.example.board.dto.UserGroupResDto;
import com.example.board.service.MyPageService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final MyPageService myPageService;

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
}
