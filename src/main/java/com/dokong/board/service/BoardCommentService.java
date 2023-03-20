package com.dokong.board.service;

import com.dokong.board.domain.board.BoardComment;
import com.dokong.board.repository.BoardCommentRepository;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.repository.dto.BoardCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;

    @Transactional
    public BoardCommentDto saveBoardComment(BoardCommentDto boardCommentDto) {
        BoardComment boardComment = boardCommentRepository.save(boardCommentDto.toEntity(boardCommentDto));
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
