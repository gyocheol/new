package com.example.board.service;

import com.example.board.config.NewCustomException;
import com.example.board.dto.BoardResDto;
import com.example.board.dto.BoardUpdateReqDto;
import com.example.board.dto.BoardWriteDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<BoardResDto> findAllBoard() {
        List<Board> boards = boardRepository.findByHiddenFalse();

        return boards.stream()
                .map(board -> new BoardResDto(
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
        Board board = boardRepository.findById(id).orElseThrow();
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
    public void deleteBoard(Long id, String username) {
        Board board = validationBoard(id, username);
        boardRepository.delete(board);
    }

    @Override
    public BoardResDto updateBoard(Long id, String username, BoardUpdateReqDto dto) {
        Board board = validationBoard(id, username);

        // board update
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCreatedAt(LocalDateTime.now());

        boardRepository.save(board);

        // 프론트 전달용 dto
        BoardResDto boardResDto = BoardResDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor().getUsername())
                .createdAt(board.getCreatedAt())
                .build();
        return boardResDto;
    }

    @Override
    public void getBoard(Long id, Model model, Principal principal) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NewCustomException("게시글이 없습니다."));

        String loginUsername = (principal != null) ? principal.getName() : "";

        model.addAttribute("board", board);
        model.addAttribute("loginUsername", loginUsername);
    }

    /**
     * 자신의 게시글 유효성 확인
     * @param id
     * @param username
     * @return
     */
    private Board validationBoard(Long id, String username) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NewCustomException("게시글이 없습니다."));
        if (!board.getAuthor().getUsername().equals(username)) {
            throw new NewCustomException("자신의 글이 아닙니다.");
        }
        return board;
    }
}
