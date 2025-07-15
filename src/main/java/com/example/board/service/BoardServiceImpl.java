package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<Board> findAllBoard() {
        return boardRepository.findByHiddenFalse();
    }

    @Override
    public void hideBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();       // 코드 설명 / .orElseThrow() 관련
        board.setHidden(true);
        boardRepository.save(board);
    }
}
