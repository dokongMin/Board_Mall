package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import com.dokong.board.repository.user.UserRepository;
import com.dokong.board.web.dto.userdto.*;
import com.dokong.board.web.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @DisplayName("회원을 저장한다")
    @Test
    void saveUser() throws Exception {
        // given
        JoinUserDto request = getJoinUserDto();
        // when 
        mockMvc.perform(post("/users/sign-in")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.msg").value("Create Request Success"))
                .andExpect(status().isCreated());
    }


    @DisplayName("마이페이지를 통해 회원 한명을 조회한다.")
    @Test
    void findUser() throws Exception {
        // given
        User request = getUser();
        when(userService.findById(anyLong())).thenReturn(request);
        // when
        mockMvc.perform(get("/users/my-page/" + anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.msg").value("Request Success"))
                .andExpect(jsonPath("$.body").isNotEmpty());
    }


    @DisplayName("마이페이지에서 회원의 정보를 수정한다.")
    @Test
    void updateUser() throws Exception {
        // given
        Address address = new Address("서울", "110-332", "0000");
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .address(address)
                .password("asldfjs")
                .email("alsghdk@naver.com")
                .build();
        UpdateUserResponseDto request = UpdateUserResponseDto.builder()
                .username("alsghks")
                .password("alsghks123")
                .email("alsghks@naver.com")
                .address(address)
                .build();
        when(userService.updateUser(1L, updateUserDto)).thenReturn(request);

        // when
        mockMvc.perform(post("/users/my-page/modify/" + 1L)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("CREATED"))
                .andExpect(jsonPath("$.msg").value("Update Request Success"));
        // then
    }

    
    
    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteUser () throws Exception{

        Long id = 1L;
        // given
        when(userService.deleteUser(id)).thenReturn(id);
        // when 
        mockMvc.perform(post("/users/my-page/delete/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK"))
                .andExpect(jsonPath("$.msg").value("Request Success"));
     }


     @DisplayName("아이디를 통해 회원의 비밀번호를 찾는다.")
     @Test
     void findPassword () throws Exception{
         // given
         String username = "test";
         User user = getUser();
         // when
         when(userService.findByUsername(username)).thenReturn(user);
         // then
         mockMvc.perform(get("/users/find-pw/" + username))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.code").value("OK"))
                 .andExpect(jsonPath("$.msg").value("Request Success"));
      }


      @DisplayName("회원 전체를 조회한다.")
      @Test
      void findAllUser () throws Exception{
          // given
          User user1 = getUser();
          User user2 = getUser2();
          List<User> userList = List.of(user1, user2);

          when(userService.findAllUser()).thenReturn(userList);
          // when
          mockMvc.perform(get("/users/user-list"))
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(jsonPath("$.code").value("OK"))
                  .andExpect(jsonPath("$.msg").value("Request Success"));
       }


       @Disabled
       @DisplayName("회원을 검색한다.")
       @Test
       void searchUser () throws Exception{
           // given
           UserSearchCondition userSearchCondition = new UserSearchCondition();
           userSearchCondition.setUsername("alsghks");

           SearchUserDto searchUserDto = new SearchUserDto();
           searchUserDto.setUsername("alsghks");
           List<SearchUserDto> request = List.of(searchUserDto);
           when(userRepository.search(userSearchCondition)).thenReturn(request);
           // when
           mockMvc.perform(get("/users/search"))
                   .andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.code").value("OK"))
                   .andExpect(jsonPath("$.msg").value("Request Success"));
        }
    private JoinUserDto getJoinUserDto() {
        return JoinUserDto.builder()
                .username("alsghks")
                .password("alsghks123")
                .name("alsghks")
                .phoneNumber("01023023322")
                .email("alsghks@naver.com")
                .build();
    }

    private User getUser() {
        return User.builder()
                .username("alsghks")
                .password("alsghks123")
                .name("alsghks")
                .phoneNumber("01023023322")
                .email("alsghks@naver.com")
                .userRole(UserRole.BRONZE)
                .build();
    }
    private User getUser2() {
        return User.builder()
                .username("alsghks2")
                .password("alsghks123")
                .name("alsghks2")
                .phoneNumber("01023023322")
                .email("alsghks@naver.com")
                .userRole(UserRole.BRONZE)
                .build();
    }

}