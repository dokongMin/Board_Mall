package com.dokong.board.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boarddto.UpdateBoardDto;
import com.dokong.board.web.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시글_생성")
    public void saveBoard() throws Exception {
        // given
        SaveBoardReqDto boardDto = getBoard();
        // when
        SaveBoardRespDto board = boardService.saveBoard(boardDto);
        SaveBoardRespDto board2 = boardService.saveBoard(boardDto);
        SaveBoardRespDto board3 = boardService.saveBoard(boardDto);
        // then
        assertThat(boardDto.getBoardContent()).isEqualTo(board.getBoardContent());
        assertThat(boardDto.getBoardTitle()).isEqualTo(board.getBoardTitle());
        assertThat(boardRepository.findAll().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("게시글_수정")
    public void updateBoard() throws Exception {
        // given
        SaveBoardReqDto boardDto = getBoard();
        boardService.saveBoard(boardDto);

        UpdateBoardDto updateBoardDto = UpdateBoardDto.builder()
                .boardTitle("바뀐내용")
                .boardContent("bbb")
                .build();
        // when
        boardService.updateBoard(1L, updateBoardDto);
        Board findBoard = boardRepository.findById(1L).get();

        // then
        assertThat(findBoard.getBoardTitle()).isEqualTo(updateBoardDto.getBoardTitle());
        assertThat(findBoard.getBoardContent()).isEqualTo(updateBoardDto.getBoardContent());
    }

    @Test
    @DisplayName("게시글_수정_예외")
    public void updateBoardException() throws Exception{
        // given
        SaveBoardReqDto boardDto = getBoard();
        boardService.saveBoard(boardDto);

        UpdateBoardDto updateBoardDto = UpdateBoardDto.builder()
                .boardTitle("바뀐내용")
                .boardContent("bbb")
                .build();
        // when
        assertThatThrownBy(() -> boardService.updateBoard(100L, updateBoardDto))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 게시글을 찾을 수 없습니다.");

        // then

     }

    private SaveBoardReqDto getBoard() {
        return SaveBoardReqDto.builder()
                .boardTitle("첫 게시글")
                .boardContent("안녕하세요.")
                .build();
    }

    private User getUser() {
        return User.builder()
                .username("alsghks")
                .password("12345")
                .name("정민환")
                .build();
    }

}