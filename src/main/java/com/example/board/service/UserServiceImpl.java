package com.example.board.service;

import com.example.board.dto.UserGroupResDto;
import com.example.board.dto.UserRegisterDto;
import com.example.board.dto.UserResDto;
import com.example.board.entity.Role;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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

    @Override
    public void forwardUsername(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
    }

    @Override
    public UserGroupResDto getAllUserGroup() {
        List<User> allUsers = userRepository.findAll();

        List<UserResDto> admins = allUsers.stream()
                .filter(user -> user.getRole() == Role.ROLE_ADMIN)
                .map(user -> new UserResDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRole()
                ))
                .toList();
        List<UserResDto> users = allUsers.stream()
                .filter(user -> user.getRole() == Role.ROLE_USER)
                .map(user -> new UserResDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRole()
                ))
                .toList();

        return UserGroupResDto.builder()
                .admins(admins)
                .users(users)
                .build();
    }
}
