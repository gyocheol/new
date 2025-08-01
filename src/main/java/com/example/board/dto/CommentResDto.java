package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    public Date getCreatedAtDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Builder
    public CommentResDto(String content, String author, boolean hidden, Comment parent, LocalDateTime createdAt) {
        this.content = content;
        this.author = author;
        this.hidden = hidden;
        this.parent = parent;
        this.createdAt = createdAt;
    }
}
