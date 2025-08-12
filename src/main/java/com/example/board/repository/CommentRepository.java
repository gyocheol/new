package com.example.board.repository;

import com.example.board.entity.Comment;
import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 게시글의 전체 댓글 및 대댓글 조회
     * @param boardId
     * @return 게시글의 전체 댓글 및 대댓글
     */
    List<Comment> findByBoardIdOrderByCreatedAtAsc(Long boardId);

    /**
     * 사용자의 전체 댓글 조회
     * @param author
     * @return 로그인한 사용자의 전체 댓글
     */
    List<Comment> findByAuthorOrderByCreatedAtAsc(User author);
}
