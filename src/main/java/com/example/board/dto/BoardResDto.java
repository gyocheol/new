package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@AllArgsConstructor
public class BoardResDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public BoardResDto(String title, String content, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.author = author;
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
