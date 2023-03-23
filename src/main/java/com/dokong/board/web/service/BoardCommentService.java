package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardCommentRepository;
import com.dokong.board.web.dto.boardcommentdto.BoardCommentDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;

    private final UserService userService;

    private final BoardService boardService;
    @Transactional
    public BoardCommentDto saveBoardComment(BoardCommentDto boardCommentDto, SessionUserDto sessionUserDto, Long boardId) {
        BoardComment boardComment = boardCommentRepository.save(boardCommentDto.toEntity());
        User user = userService.findById(sessionUserDto.getId());
        Board board = boardService.findById(boardId);
        boardComment.writeComment(user, board);
        return BoardCommentDto.of(boardComment);
    }

    @Transactional
    public BoardCommentDto updateBoardComment(Long id, BoardCommentDto boardCommentDto) {
        BoardComment boardComment = checkExistBoardComment(id);
        boardComment.updateBoardComment(boardCommentDto.getCommentContent());
        return BoardCommentDto.of(boardComment);
    }

    public BoardComment checkExistBoardComment(Long id) {
        return boardCommentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 댓글은 존재하지 않습니다.");
        });
    }
}
