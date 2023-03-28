package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.board.BoardLike;
import com.dokong.board.repository.BoardLikeRepository;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.boardlikedto.BoardLikeRespDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BoardLikeServiceTest {


    @Autowired
    UserService userService;
    @Autowired
    LoginService loginService;
    @Autowired
    BoardService boardService;
    @Autowired
    BoardLikeService boardLikeService;
    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Test
    @DisplayName("게시글_좋아요_생성")
    public void pushBoardLike () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        SaveBoardReqDto boardDto = getBoard();
        SaveBoardRespDto saveBoardRespDto = boardService.saveBoard(boardDto, sessionUserDto);
        Board board = boardService.findById(saveBoardRespDto.getId());
        boardLikeService.pushBoardLike(sessionUserDto, board.getId());
        boardLikeService.pushBoardLike(sessionUserDto, board.getId());
        BoardLikeRespDto boardLikeRespDto = boardLikeService.pushBoardLike(sessionUserDto, board.getId());

        BoardLike boardLike = boardLikeService.findById(boardLikeRespDto.getBoardLikeId());
        // then
        assertThat(boardLike.getBoard().getId()).isEqualTo(board.getId());
        assertThat(boardLike.getUser().getId()).isEqualTo(sessionUserDto.getId());
        assertThat(boardLikeRepository.findAll().size()).isEqualTo(1);
     }

    @Test
    @DisplayName("게시글_좋아요_취소")
    public void pushBoardLikeCancel () throws Exception{
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        SaveBoardReqDto boardDto = getBoard();
        SaveBoardRespDto saveBoardRespDto = boardService.saveBoard(boardDto, sessionUserDto);
        Board board = boardService.findById(saveBoardRespDto.getId());
        boardLikeService.pushBoardLike(sessionUserDto, board.getId());
        boardLikeService.pushBoardLike(sessionUserDto, board.getId());
        // then
        assertThat(boardLikeRepository.findAll().size()).isEqualTo(0);
        assertThat(board.getBoardLikes().size()).isEqualTo(0);
    }

    private SaveBoardReqDto getBoard() {
        return SaveBoardReqDto.builder()
                .boardTitle("첫 게시글")
                .boardContent("안녕하세요.")
                .build();
    }

    private LoginUserDto getLoginUserDto(JoinUserDto joinUserDto) {
        return LoginUserDto.builder()
                .username(joinUserDto.getUsername())
                .password(joinUserDto.getPassword())
                .build();
    }

    private JoinUserDto getJoinUserDto() {
        return JoinUserDto.builder()
                .username("aaa")
                .password("bbb")
                .build();
    }
}