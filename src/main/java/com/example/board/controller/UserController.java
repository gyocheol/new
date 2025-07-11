package com.example.board.controller;

import com.example.board.dto.UserRegisterDto;
import com.example.board.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userDto", new UserRegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDto") UserRegisterDto dto, Model model) {
        try {
            userService.register(dto);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        System.out.println("home 컨트롤러 진입");
        return "home";
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/dashboard";
    }
}
