package com.example.board.service;

import com.example.board.dto.UserGroupResDto;
import com.example.board.dto.UserRegisterDto;
import org.springframework.ui.Model;

import java.security.Principal;

public interface UserService {

    /**
     * 회원가입
     * @param dto
     */
    void register(UserRegisterDto dto);

    /**
     * frontend에게 로그인한 유저 이름을 전달
     * @param model
     * @param principal
     * @return
     */
    void forwardUsername(Model model, Principal principal);

    UserGroupResDto getAllUserGroup();
}
