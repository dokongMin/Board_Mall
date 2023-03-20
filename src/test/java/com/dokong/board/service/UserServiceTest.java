package com.dokong.board.service;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.userdto.JoinUserDto;
import com.dokong.board.web.dto.userdto.UpdateUserDto;
import com.dokong.board.web.dto.userdto.JoinUserResponseDto;
import com.dokong.board.web.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원_가입")
    public void joinUser() throws Exception {
        // given
        Address address = new Address("서울", "110-332", "0000");

        JoinUserDto userDto = getJoinUserDto(address);

        JoinUserDto userDto2 = JoinUserDto.builder()
                .username("alsghks2")
                .password("1234")
                .name("정민환")
                .address(address)
                .email("alsghks@naver.com")
                .gender("M")
                .phoneNumber("1233-23423")
                .build();

        // when
        JoinUserResponseDto joinUserResponseDto = userService.saveUser(userDto);
        userService.saveUser(userDto2);

        User user = userRepository.findByUsername(joinUserResponseDto.getUsername()).orElseThrow(() -> {
            throw new IllegalStateException("회원이 없습니다.");
        });
        // then
        assertThat(user.getUsername()).isEqualTo("alsghks");
        assertThat(userRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원_가입_예외")
    public void joinUserException() throws Exception {
        // given
        JoinUserDto user1 = JoinUserDto.builder()
                .username("aaa")
                .password("1234")
                .build();
        JoinUserDto user2 = JoinUserDto.builder()
                .username("aaa")
                .password("1234")
                .build();
        // when
        userService.saveUser(user1);
        // then
        assertThrows(IllegalStateException.class, () -> {
            userService.saveUser(user2);
        });

    }

    @Test
    @DisplayName("회원_수정")
    public void updateUser() throws Exception {
        // given
        Address address = new Address("서울", "110-332", "0000");
        Address newAddress = new Address("경기", "999-999", "9999");

        JoinUserDto userDto = getJoinUserDto(address);

        userService.saveUser(userDto);

        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .username(userDto.getUsername())
                .password("999")
                .address(newAddress)
                .email("9999@naver.com")
                .build();
        // when
        userService.updateUser(updateUserDto);
        User findUser = userRepository.findByUsername("alsghks").get();
        // then
        assertThat(findUser.getPassword()).isEqualTo("999");
        assertThat(findUser.getAddress()).isEqualTo(newAddress);
    }

    private JoinUserDto getJoinUserDto(Address address) {
        JoinUserDto userDto = JoinUserDto.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환")
                .address(address)
                .email("alsghks@naver.com")
                .gender("M")
                .phoneNumber("1233-23423")
                .build();
        return userDto;
    }
}