package com.example.board.controller;

import com.example.board.dto.UserGroupResDto;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping
    public String adminPage(Model model) {
        UserGroupResDto userGroupResDto = userService.getAllUserGroup();
        model.addAttribute("userGroup", userGroupResDto);
        return "admin/user";
    }
}
