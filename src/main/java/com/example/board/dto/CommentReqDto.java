package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReqDto {
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
