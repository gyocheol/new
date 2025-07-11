package com.example.board.service;

import com.example.board.dto.UserRegisterDto;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegisterDto dto) {
        if(!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(userRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);
    }
}
