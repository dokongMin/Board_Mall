package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.board.BoardStatus;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import com.dokong.board.repository.board.BoardRepository;
import com.dokong.board.web.controller.SessionUserConst;
import com.dokong.board.web.dto.boarddto.*;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.handler.GlobalExceptionHandler;
import com.dokong.board.web.service.BoardService;
import com.dokong.board.web.service.UserService;
import com.dokong.board.web.service.redis.RedisBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RestBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @MockBean
    private HttpSession session;

    @AfterEach
    void tearDown() {
        boardRepository.deleteAllInBatch();
    }

    @DisplayName("게시글을 작성한다.")
    @Test
    void saveBoard () throws Exception{
        // given
        SaveBoardReqDto saveBoardReqDto = getBoard();
        SaveBoardRespDto request = getSaveBoardRespDto();

        // when
        when(boardService.saveBoard(saveBoardReqDto)).thenReturn(request);

        // then
        mockMvc.perform(post("/board/write")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.msg").value("Create Request Success"));
     }


     @DisplayName("게시글 ID 로 게시글을 찾아서 게시글을 수정한다.")
     @Test
     void updateBoard () throws Exception{
         // given
         UpdateBoardDto request = getUpdateBoardDto();
         Long id = 1L;
         when(boardService.updateBoard(id, request)).thenReturn(request);
         // when
         mockMvc.perform(post("/board/update/" + id)
                         .content(objectMapper.writeValueAsString(request))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andDo(print())
                 .andExpect(status().isCreated())
                 .andExpect(jsonPath("$.code").value("CREATED"))
                 .andExpect(jsonPath("$.msg").value("Update Request Success"));
      }


      @DisplayName("게시글 ID 를 통해 게시글을 삭제한다.")
      @Test
      void deleteBoard () throws Exception{
          // given
          DeleteBoardRespDto request = getDeleteBoardRespDto();
          Long id = 1L;
          when(boardService.deleteBoard(id)).thenReturn(request);
          // when
          mockMvc.perform(put("/board/delete/" + id))
                  .andDo(print())
                  .andExpect(status().isCreated())
                  .andExpect(jsonPath("$.code").value("CREATED"))
                  .andExpect(jsonPath("$.msg").value("Delete Request Success"));
       }

       @DisplayName("게시글을 조회한다.")
       @Test
       void getBoards () throws Exception{
           // given
           Long id = 1L;
           FindBoardDto dto = getFindBoardDto();
           String username = "test";
           when(session.getAttribute(SessionUserConst.LOGIN_MEMBER)).thenReturn(username);
           when(boardService.addViewCountInRedis(id, username)).thenReturn(dto);
           // when
           mockMvc.perform(get("/board/" + id)
                           .content(objectMapper.writeValueAsString(dto))
                           .contentType(MediaType.APPLICATION_JSON))
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.code").value("OK"))
                   .andExpect(jsonPath("$.msg").value("Request Success"));

        }



        @DisplayName("게시글 전체를 조회한다.")
        @Test
        void findAllBoard () throws Exception{
            // given
            FindBoardDto board1 = getFindBoardDto();
            FindBoardDto board2 = getFindBoardDto();
            List<FindBoardDto> request = List.of(board1, board2);

            when(boardService.findAll()).thenReturn(request);
            // when
            mockMvc.perform(get("/board/list/all")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(jsonPath("$.code").value("OK"))
                    .andExpect(jsonPath("$.msg").value("Request Success"));
         }


         @DisplayName("게시글 상태에 따라 전체를 조회한다.")
         @Test
         void findAllByBoardStatusCreated () throws Exception{
             // given
             FindBoardDto board1 = getFindBoardDto();
             FindBoardDto board2 = getFindBoardDto2();
             List<FindBoardDto> request = List.of(board1, board2);

             when(boardService.findAllByBoardStatusCreated()).thenReturn(request);
             // when
             mockMvc.perform(get("/board/list")
                             .content(objectMapper.writeValueAsString(request))
                             .contentType(MediaType.APPLICATION_JSON))
                     .andDo(print())
                     .andExpect(jsonPath("$.code").value("OK"))
                     .andExpect(jsonPath("$.msg").value("Request Success"));
          }
    private FindBoardDto getFindBoardDto() {
        return FindBoardDto.builder()
                .boardId(1L)
                .boardTitle("aaa")
                .boardContent("bbb")
                .likeCount(0L)
                .viewCount(1L)
                .boardStatus(BoardStatus.CREATED)
                .build();
    }
    private FindBoardDto getFindBoardDto2() {
        return FindBoardDto.builder()
                .boardId(2L)
                .boardTitle("aaa")
                .boardContent("bbb")
                .likeCount(0L)
                .viewCount(1L)
                .boardStatus(BoardStatus.CREATED)
                .build();
    }
    private DeleteBoardRespDto getDeleteBoardRespDto() {
        return DeleteBoardRespDto.builder()
                .boardContent("aaa")
                .boardTitle("bbb")
                .boardStatus(BoardStatus.CREATED).build();
    }

    private UpdateBoardDto getUpdateBoardDto() {
        return UpdateBoardDto.builder()
                .boardContent("aaa")
                .boardTitle("bbb")
                .build();
    }
    private UpdateBoardDto getUpdateBoardDto2() {
        return UpdateBoardDto.builder()
                .boardContent("cccc")
                .boardTitle("bbb")
                .build();
    }

    private SaveBoardRespDto getSaveBoardRespDto() {
        return SaveBoardRespDto.builder()
                .boardContent("bbb")
                .boardTitle("aaa")
                .likeCount(0)
                .userId(1L)
                .build();
    }

    private SaveBoardReqDto getBoard() {
        return SaveBoardReqDto.builder()
                .boardTitle("aaa")
                .boardContent("bbb")
                .boardStatus(BoardStatus.CREATED)
                .userId(1L)
                .build();
    }

}