package com.example.board.dto;

import com.example.board.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@AllArgsConstructor
public class CommentResDto {
    private Long id;
    private String content;
    private String author;
    private boolean hidden;
    private Comment parent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CommentResDto(String content, String author, boolean hidden, Comment parent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.content = content;
        this.author = author;
        this.hidden = hidden;
        this.parent = parent;
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
