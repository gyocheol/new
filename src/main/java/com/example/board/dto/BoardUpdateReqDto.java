package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateReqDto {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 20, message = "제목은 최대 20자까지 입력 가능합니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
}
