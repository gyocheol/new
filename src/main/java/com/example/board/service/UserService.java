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
     */
    void forwardUsername(Model model, Principal principal);

    /**
     * frontend에게 로그인한 유저의 권한을 전달
     * @param model
     * @param principal
     */
    void forwardRole(Model model, Principal principal);

    /**
     * 모든 유저 및 관리자 그룹 조회
     * @return 모든 관리자와 유저
     */
    UserGroupResDto getAllUserGroup();
}
