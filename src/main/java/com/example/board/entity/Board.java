package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob        // Large Object의 줄임말, 대형 객체 데이터 저장을 위한 가변 길이 데이터 유형
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean hidden = false;
    @ManyToOne
    private User author;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, User author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
    }

    public Date getCreatedAtDate() {
        return Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getUpdatedAtDate() {
        return updatedAt != null ? Date.from(updatedAt.atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
}
