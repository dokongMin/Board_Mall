//package com.dokong.board.web.service.redis;
//
//import com.dokong.board.domain.board.Board;
//import com.dokong.board.domain.user.User;
//import com.dokong.board.web.dto.boarddto.SaveBoardReqDto;
//import com.dokong.board.web.dto.boarddto.SaveBoardRespDto;
//import com.dokong.board.web.dto.userdto.JoinUserDto;
//import com.dokong.board.web.dto.userdto.JoinUserResponseDto;
//import com.dokong.board.web.service.BoardService;
//import com.dokong.board.web.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class RedisViewCountServiceTest {
//
//    @Autowired
//    private RedisViewCountService redisViewCountService;
//
//    @Autowired
//    private RedisBoardService redisBoardService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private BoardService boardService;
//
//    @Test
//    public void viewCountTest () throws Exception{
//
//        for (int i = 0; i < 30; i++) {
//            JoinUserDto joinUserDto = getJoinUserDto(i + 1);
//            userService.saveUser(joinUserDto);
//        }
//
//        SaveBoardReqDto saveBoardReqDto = getBoardReqDto(1L);
//        SaveBoardRespDto saveBoardRespDto = boardService.saveBoard(saveBoardReqDto);
//        Board board = boardService.findById(saveBoardRespDto.getId());
//
//        redisBoardService.addViewCountInRedis(board);
//        redisBoardService.addViewCountInRedis(board);
//        redisBoardService.addViewCountInRedis(board);
//
//    }
//
//    @Test
//    public void duplicateTest () throws Exception{
//        JoinUserDto joinUserDto = getJoinUserDto();
//        JoinUserResponseDto joinUserResponseDto = userService.saveUser(joinUserDto);
//        User user = userService.findById(joinUserResponseDto.getId());
//
//        SaveBoardReqDto saveBoardReqDto = getBoardReqDto(1L);
//        SaveBoardRespDto saveBoardRespDto = boardService.saveBoard(saveBoardReqDto);
//        Board board = boardService.findById(saveBoardRespDto.getId());
//
//        redisBoardService.checkDuplicateRequest(user.getUsername(), board.getId());
//     }
//
//    private SaveBoardReqDto getBoardReqDto(Long id) {
//        return SaveBoardReqDto.builder()
//                .boardTitle("tempBoardTitle")
//                .boardContent("tempBoardContent")
//                .userId(id)
//                .build();
//    }
//
//    private JoinUserDto getJoinUserDto(int i) {
//        return JoinUserDto.builder()
//                .username("user" + i)
//                .password("aa" + i)
//                .name("min" + i)
//                .email("alsghks@naver.com" + i)
//                .build();
//    }
//    private JoinUserDto getJoinUserDto() {
//        return JoinUserDto.builder()
//                .username("user")
//                .password("aa")
//                .name("min")
//                .email("alsghks@naver.com")
//                .build();
//    }
//}