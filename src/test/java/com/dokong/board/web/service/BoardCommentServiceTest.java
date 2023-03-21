package com.dokong.board.web.service;

import com.dokong.board.domain.board.Board;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.BoardCommentRepository;
import com.dokong.board.web.dto.BoardCommentDto;
import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.service.BoardCommentService;
import com.dokong.board.web.service.BoardService;
import com.dokong.board.web.service.LoginService;
import com.dokong.board.web.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BoardCommentServiceTest {

    @Autowired
    private BoardCommentService boardCommentService;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;
    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("댓글_생성")
    public void saveBoardComment() throws Exception {
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        User user = userService.findById(sessionUserDto.getId());

        SaveBoardReqDto saveBoardReqDto = getSaveBoardReqDto();
        SaveBoardRespDto boardRespDto = boardService.saveBoard(saveBoardReqDto, sessionUserDto);
        Board board = boardService.findById(boardRespDto.getId());

        BoardCommentDto boardCommentDto = getBoardComment();
        boardCommentService.saveBoardComment(boardCommentDto, sessionUserDto, board.getId());
        // then
        assertThat(boardCommentRepository.findAll().get(0).getCommentContent()).isEqualTo(boardCommentDto.getCommentContent());
        assertThat(user.getBoardComments().get(0).getCommentContent()).isEqualTo("댓글입니다.");
        assertThat(user.getBoardComments().size()).isEqualTo(1);
        assertThat(board.getBoardComments().get(0).getCommentContent()).isEqualTo("댓글입니다.");
        assertThat(board.getBoardComments().size()).isEqualTo(1);

    }


    @Test
    @DisplayName("댓글_수정")
    public void updateBoardComment() throws Exception {
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        User user = userService.findById(sessionUserDto.getId());

        SaveBoardReqDto saveBoardReqDto = getSaveBoardReqDto();
        SaveBoardRespDto boardRespDto = boardService.saveBoard(saveBoardReqDto, sessionUserDto);
        Board board = boardService.findById(boardRespDto.getId());

        BoardCommentDto boardCommentDto = getBoardComment();
        boardCommentService.saveBoardComment(boardCommentDto, sessionUserDto, board.getId());

        BoardCommentDto updateBoardCommentDto = BoardCommentDto.builder()
                .commentContent("수정된 댓글입니다.")
                .build();
        boardCommentService.updateBoardComment(boardCommentRepository.findAll().get(0).getId(), updateBoardCommentDto);
        // then
        assertThat(boardCommentRepository.findAll().get(0).getCommentContent()).isEqualTo(updateBoardCommentDto.getCommentContent());
        assertThat(user.getBoardComments().get(0).getCommentContent()).isEqualTo("수정된 댓글입니다.");
        assertThat(board.getBoardComments().get(0).getCommentContent()).isEqualTo("수정된 댓글입니다.");
    }

    @Test
    @DisplayName("댓글_수정_예외")
    public void updateBoardCommentException() throws Exception {
        // given
        JoinUserDto joinUserDto = getJoinUserDto();
        userService.saveUser(joinUserDto);

        LoginUserDto loginUserDto = getLoginUserDto(joinUserDto);
        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        User user = userService.findById(sessionUserDto.getId());

        SaveBoardReqDto saveBoardReqDto = getSaveBoardReqDto();
        SaveBoardRespDto boardRespDto = boardService.saveBoard(saveBoardReqDto, sessionUserDto);
        Board board = boardService.findById(boardRespDto.getId());

        BoardCommentDto boardComment = getBoardComment();
        boardCommentService.saveBoardComment(boardComment, sessionUserDto, board.getId());
        // when
        BoardCommentDto updateBoardCommentDto = BoardCommentDto.builder()
                .commentContent("수정된 댓글입니다.")
                .build();
        //then
        assertThatThrownBy(() -> boardCommentService.updateBoardComment(100L, updateBoardCommentDto))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 댓글은 존재하지 않습니다.");

    }


    private BoardCommentDto getBoardComment() {
        return BoardCommentDto.builder()
                .commentContent("댓글입니다.")
                .build();
    }

    private SaveBoardReqDto getSaveBoardReqDto() {
        return SaveBoardReqDto.builder()
                .boardTitle("더미 제목")
                .boardContent("더미 내용")
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