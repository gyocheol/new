package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    // hidden 처리가 안된 게시글만 조회
    List<Board> findByHiddenFalse();

}
