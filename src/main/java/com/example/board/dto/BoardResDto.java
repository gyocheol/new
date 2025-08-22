package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class BoardResDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private boolean isHidden;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Date getCreatedAtDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getUpdatedAtDate() {
        return updatedAt != null ? Date.from(updatedAt.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
