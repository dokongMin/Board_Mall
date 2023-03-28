package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.exception.FiveBoardPostPerDay;
import com.dokong.board.repository.BoardRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boarddto.UpdateBoardDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
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
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;
    @Test
    @DisplayName("게시글_생성")
    public void saveBoard() throws Exception {
        // given
        JoinUserDto joinUserDto = getUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);


        SaveBoardReqDto boardDto = getBoard();
        // when
        SaveBoardRespDto board = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board2 = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board3 = boardService.saveBoard(boardDto, sessionUserDto);
        // then
        assertThat(boardDto.getBoardContent()).isEqualTo(board.getBoardContent());
        assertThat(boardDto.getBoardTitle()).isEqualTo(board.getBoardTitle());
        assertThat(boardRepository.findAll().size()).isEqualTo(3);
        assertThat(userService.findById(sessionUserDto.getId()).getBoards().size()).isEqualTo(3);
        assertThat(userService.findById(sessionUserDto.getId()).getBoards().get(0).getBoardTitle()).isEqualTo("첫 게시글");
    }

    @Test
    @DisplayName("게시글_수정")
    public void updateBoard() throws Exception {
        // given
        JoinUserDto joinUserDto = getUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        SaveBoardReqDto boardDto = getBoard();
        boardService.saveBoard(boardDto, sessionUserDto);

        UpdateBoardDto updateBoardDto = UpdateBoardDto.builder()
                .boardTitle("바뀐내용")
                .boardContent("bbb")
                .build();
        // when
        boardService.updateBoard(boardRepository.findAll().get(0).getId(), updateBoardDto);
        Board findBoard = boardRepository.findAll().get(0);

        // then
        assertThat(findBoard.getBoardTitle()).isEqualTo(updateBoardDto.getBoardTitle());
        assertThat(findBoard.getBoardContent()).isEqualTo(updateBoardDto.getBoardContent());
        assertThat(userService.findById(sessionUserDto.getId()).getBoards().get(0).getBoardTitle()).isEqualTo("바뀐내용");
        assertThat(userService.findById(sessionUserDto.getId()).getBoards().get(0).getBoardContent()).isEqualTo("bbb");
    }

    @Test
    @DisplayName("게시글_수정_예외")
    public void updateBoardException() throws Exception{
        // given
        JoinUserDto joinUserDto = getUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        SaveBoardReqDto boardDto = getBoard();
        boardService.saveBoard(boardDto, sessionUserDto);

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
    @Test
    @DisplayName("게시글_하루_제한_예외")
    public void fiveBoardPostPerDayException() throws Exception {
        // given
        JoinUserDto joinUserDto = getUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        User user = userService.findById(sessionUserDto.getId());

        SaveBoardReqDto boardDto = getBoard();
        // when
        SaveBoardRespDto board = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board2 = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board3 = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board4 = boardService.saveBoard(boardDto, sessionUserDto);
        SaveBoardRespDto board5 = boardService.saveBoard(boardDto, sessionUserDto);
        // then
        assertThatThrownBy(() -> boardService.saveBoard(boardDto, sessionUserDto))
                .isExactlyInstanceOf(FiveBoardPostPerDay.class)
                .hasMessageContaining("게시글은 하루에 5개만 작성 가능합니다.");
    }
//    @Test
//    @DisplayName("게시글_쿠폰_발급")
//    public void boardPostCouponIssue() throws Exception {
//        // given
//        JoinUserDto joinUserDto = getUserDto();
//        userService.saveUser(joinUserDto);
//
//        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
//        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
//        User user = userService.findById(sessionUserDto.getId());
//
//        SaveBoardReqDto boardDto = getBoard();
//        // when
//        SaveBoardRespDto board = boardService.saveBoard(boardDto, sessionUserDto);
//        SaveBoardRespDto board2 = boardService.saveBoard(boardDto, sessionUserDto);
//        SaveBoardRespDto board3 = boardService.saveBoard(boardDto, sessionUserDto);
//        SaveBoardRespDto board4 = boardService.saveBoard(boardDto, sessionUserDto);
//        SaveBoardRespDto board5 = boardService.saveBoard(boardDto, sessionUserDto);
////        when(clock.instant()).thenReturn(Instant.parse("2023-10-10"))
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//        boardService.saveBoard(boardDto, sessionUserDto);
//    }
    private SaveBoardReqDto getBoard() {
        return SaveBoardReqDto.builder()
                .boardTitle("첫 게시글")
                .boardContent("안녕하세요.")
                .build();
    }

    private LoginUserDto getLoginUserDto(JoinUserDto userDto) {
        return LoginUserDto.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    private JoinUserDto getUserDto() {
        return JoinUserDto.builder()
                .username("aaa")
                .password("bbb")
                .build();
    }
}