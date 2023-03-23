package com.dokong.board.web.service;


import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardLikeRepository;
import com.dokong.board.web.dto.boardlikedto.BoardLikeDto;
import com.dokong.board.web.dto.boardlikedto.BoardLikeRespDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardLikeService {

    private final BoardLikeRepository boardLikeRepository;

    private final UserService userService;

    private final BoardService boardService;

    @Transactional
    public BoardLikeRespDto pushBoardLike(SessionUserDto sessionUserDto, Long boardId) {
        Board board = boardService.findById(boardId);
        User user = userService.findById(sessionUserDto.getId());

        if (checkExistBoardLike(board, user))
            return BoardLikeRespDto.builder().errorDescription("좋아요를 취소하였습니다.").build();

        BoardLikeDto boardLikeDto = BoardLikeDto.builder().build();
        BoardLike boardLike = boardLikeRepository.save(boardLikeDto.toEntity());

        board.pushBoardLike(user, boardLike);

        return BoardLikeRespDto.of(user, board, boardLike);
    }

    private boolean checkExistBoardLike(Board board, User user) {
        Optional<BoardLike> existLike = boardLikeRepository.findExistLike(user.getId(), board.getId());
        if (existLike.isPresent()){
            board.cancelBoardLike(existLike.get(), user, board);
            boardLikeRepository.delete(existLike.get());
            return true;
        }
        return false;
    }

    public BoardLike findById(Long id) {
        return boardLikeRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글 좋아요는 존재하지 않습니다.");
        });
    }
}
