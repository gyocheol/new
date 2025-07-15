package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob        // Large Object의 줄임말, 대형 객체 데이터 저장을 위한 가변 길이 데이터 유형
    private String content;
    private LocalDateTime createAt;
    private boolean hidden = false;
    @ManyToOne
    private User author;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
}
