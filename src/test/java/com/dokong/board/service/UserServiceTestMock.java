package com.dokong.board.service;

import com.dokong.board.domain.user.User;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.JoinUserResponseDto;
import com.dokong.board.web.dto.userdto.UpdateUserDto;
import com.dokong.board.web.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTestMock {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Mock_회원가입")
    public void joinUser() throws Exception {
        // given
        JoinUserDto joinUserDto = joinUserDto();
        User dummyUser = joinUserDto.toEntity();

        doReturn(dummyUser).when(userRepository).save(any(User.class));
        // when
        JoinUserResponseDto user = userService.saveUser(joinUserDto);
        // then
        assertThat(user.getUsername()).isEqualTo(joinUserDto.getUsername());

        //verify
        verify(userRepository, times(1)).save(any(User.class));

    }

//    @Test
//    @DisplayName("Mock_회원수정")
//    public void updateUser () throws Exception{
//        // given
//        UpdateUserDto updateUserDto = updateUserDto();
//        User dummyUser = updateUserDto.toEntity();
//        // when
//        doReturn(dummyUser)
//        // then
//
//     }

    private UpdateUserDto updateUserDto() {
        return UpdateUserDto.builder()
                .username("alsghks")
                .password("bbb")
                .email("aaa")
                .build();
    }


    private JoinUserDto joinUserDto() {
        return JoinUserDto.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환")
                .email("alsghks@naver.com")
                .gender("M")
                .phoneNumber("1233-23423")
                .build();
    }

}