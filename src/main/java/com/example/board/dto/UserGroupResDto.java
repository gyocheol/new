package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserGroupResDto {
    private List<UserResDto> admins;
    private List<UserResDto> users;
}
