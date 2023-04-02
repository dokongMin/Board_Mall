package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardCommentRepository;
import com.dokong.board.web.dto.boardcommentdto.BoardCommentDto;
import com.dokong.board.web.dto.boardcommentdto.FindBoardCommentRespDto;
import com.dokong.board.web.dto.boardcommentdto.UpdateBoardCommentDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;

    private final UserService userService;

    private final BoardService boardService;
    @Transactional
    public BoardCommentDto saveBoardComment(BoardCommentDto boardCommentDto, Long boardId) {
        User user = userService.findById(boardCommentDto.getUserId());
        Board board = boardService.findById(boardId);
        BoardComment boardComment = boardCommentRepository.save(boardCommentDto.toEntity());
        boardComment.writeComment(user, board);
        return BoardCommentDto.of(boardComment);
    }

    @Transactional
    public BoardCommentDto updateBoardComment(Long id, UpdateBoardCommentDto updateBoardCommentDto) {
        BoardComment boardComment = findById(id);
        boardComment.updateBoardComment(updateBoardCommentDto.getCommentContent());
        return BoardCommentDto.of(boardComment);
    }

    @Transactional
    public FindBoardCommentRespDto deleteBoard(Long boardCommentId) {
        BoardComment boardComment = findById(boardCommentId);
        boardComment.deleteComment();
        return FindBoardCommentRespDto.of(boardComment);
    }
    public List<FindBoardCommentRespDto> findByBoardId(Long boardId) {
        return boardCommentRepository.findByBoardId(boardId).stream()
                .map(c -> FindBoardCommentRespDto.of(c))
                .collect(Collectors.toList());
    }
    public BoardComment findById(Long id) {
        return boardCommentRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 댓글은 존재하지 않습니다.");
        });
    }
}
