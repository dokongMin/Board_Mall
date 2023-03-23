package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boarddto.UpdateBoardDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    @Transactional
    public SaveBoardRespDto saveBoard(SaveBoardReqDto boardReqDto, SessionUserDto sessionUserDto) {
        Board board = boardRepository.save(boardReqDto.toEntity());
        User user = userService.findById(sessionUserDto.getId());
        board.writeBoard(user);
        return SaveBoardRespDto.of(board);
    }

    @Transactional
    public UpdateBoardDto updateBoard(Long id, UpdateBoardDto boardReqDto) {
        Board board = findById(id);
        board.updateBoard(boardReqDto.getBoardTitle(), boardReqDto.getBoardContent());
        return UpdateBoardDto.of(board);
    }
    
    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        });
    }
}
