package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private boolean hidden = false;     // 관리자용
    @ManyToOne
    private User author;
    @ManyToOne
    private Board board;
    @ManyToOne
    private Comment parent;     // 대댓글용

    @Builder
    public Comment(String content, LocalDateTime createdAt, boolean hidden, User author, Board board, Comment parent) {
        this.content = content;
        this.createdAt = createdAt;
        this.hidden = hidden;
        this.author = author;
        this.board = board;
        this.parent = parent;
    }

    public Date getCreatedAtDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
