package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@AllArgsConstructor
public class MyCommentResDto {
    private Long id;
    private String content;
    private Board board;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MyCommentResDto(String content, Board board, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.content = content;
        this.board = board;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAtDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getUpdatedAtDate() {
        return updatedAt != null ? Date.from(updatedAt.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
