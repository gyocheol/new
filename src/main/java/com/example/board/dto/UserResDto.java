package com.example.board.dto;

import com.example.board.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResDto {
    private Long id;
    private String username;
    private Role role;
}
