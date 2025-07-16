package com.example.board.service;

import com.example.board.dto.BoardAllListResDto;
import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<BoardAllListResDto> findAllBoard() {
        List<Board> boards = boardRepository.findByHiddenFalse();

        return boards.stream()
                .map(board -> new BoardAllListResDto(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getAuthor().getUsername(),
                        board.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void hideBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();       // 코드 설명 / .orElseThrow() 관련
        board.setHidden(true);
        boardRepository.save(board);
    }

    @Override
    public void createBoard(BoardWriteDto dto) {
        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .author(userRepository.findByUsername(dto.getAuthorName()).orElseThrow())
                .build();
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(String userName) {

    }

    @Override
    public void updateBoard(String userName) {

    }
}
