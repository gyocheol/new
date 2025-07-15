package com.example.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private boolean hidden;
    @ManyToOne
    private User author;
    @ManyToOne
    private Board board;
    @ManyToOne
    private Comment parent;     // 대댓글용
}
