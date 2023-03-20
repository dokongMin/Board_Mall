package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boarddto.UpdateBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public SaveBoardRespDto saveBoard(SaveBoardReqDto boardReqDto) {
        Board board = boardRepository.save(boardReqDto.toEntity());
        return SaveBoardRespDto.of(board);
    }

    @Transactional
    public UpdateBoardDto updateBoard(Long id, UpdateBoardDto boardReqDto) {
        Board board = checkExistBoard(id);
        board.updateBoard(boardReqDto.getBoardTitle(), boardReqDto.getBoardContent());
        return UpdateBoardDto.of(board);
    }

    //    public Board checkExistBoard(String boardTitle) {
//        return boardRepository.findByBoardTitle(boardTitle).orElseThrow(() -> {
//            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
//        });
//    }
    public Board checkExistBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.");
        });
    }
}
