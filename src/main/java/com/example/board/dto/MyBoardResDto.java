package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@AllArgsConstructor
public class MyBoardResDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public MyBoardResDto(String title, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
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
