package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * hidden 처리가 안된 게시글만 조회
     * @return 히든 처리 되지 않은 전체 게시글
     */
    List<Board> findByHiddenFalse();

    /**
     * 사용자의 전체 게시글 조회
     * @param author
     * @return 로그인한 사용자의 전체 게시글
     */
    List<Board> findByAuthorOrderByCreatedAtAsc(User author);
}
