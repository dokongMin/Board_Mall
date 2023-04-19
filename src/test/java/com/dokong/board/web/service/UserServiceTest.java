package com.dokong.board.web.service;

import com.dokong.board.domain.Address;
import com.dokong.board.domain.user.User;
import com.dokong.board.domain.user.UserRole;
import com.dokong.board.repository.user.UserRepository;
import com.dokong.board.web.dto.userdto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        JoinUserResponseDto joinUserResponseDto = userService.saveUser(userDto);

        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .password("999")
                .address(newAddress)
                .email("9999@naver.com")
                .build();
        // when
        userService.updateUser(joinUserResponseDto.getId(), updateUserDto);
        User findUser = userRepository.findByUsername("alsghks").get();
        // then
        assertThat(findUser.getPassword()).isEqualTo("999");
        assertThat(findUser.getAddress()).isEqualTo(newAddress);
    }

    @Test
    @DisplayName("회원_탈퇴")
    public void deleteUser() throws Exception{
        // given
        Address address = new Address("서울", "110-332", "0000");
        JoinUserDto joinUserDto = getJoinUserDto(address);
        JoinUserDto joinUserDto2 = getJoinUserDto2(address);
        // when
        JoinUserResponseDto joinUserResponseDto = userService.saveUser(joinUserDto);
        JoinUserResponseDto joinUserResponseDto2 = userService.saveUser(joinUserDto2);
        User user = userService.findById(joinUserResponseDto.getId());
        User user2 = userService.findById(joinUserResponseDto2.getId());
        Long id = userService.deleteUser(user.getId());
        Long id2 = userService.deleteUser(user2.getId());
        // then
        assertThat(user.getUsername()).isEqualTo(null);
        assertThat(user.getUserRole()).isEqualTo(UserRole.DROP_OUT);
        assertThat(user2.getUserRole()).isEqualTo(UserRole.DROP_OUT);
        assertThat(user2.getUsername()).isEqualTo(null);
     }

     @Test
     public void searchTest () throws Exception{
         // given
         Address address = new Address("서울", "110-332", "0000");
         JoinUserDto joinUserDto = getJoinUserDto(address);
         JoinUserResponseDto joinUserResponseDto = userService.saveUser(joinUserDto);

         UserSearchCondition userSearchCondition = new UserSearchCondition();
         userSearchCondition.setUsername("alsghks");
         // when
         List<SearchUserDto> search = userRepository.search(userSearchCondition);
         // then
         assertThat(search).extracting("username").containsExactly("alsghks");

      }

    private JoinUserDto getJoinUserDto(Address address) {
        JoinUserDto userDto = JoinUserDto.builder()
                .username("alsghks")
                .password("1234")
                .name("정민환1")
                .address(address)
                .email("alsghks@naver.com")
                .gender("M")
                .phoneNumber("1233-23423")
                .build();
        return userDto;
    }
    private JoinUserDto getJoinUserDto2(Address address) {
        JoinUserDto userDto = JoinUserDto.builder()
                .username("alsghks2")
                .password("1234")
                .name("정민환2")
                .address(address)
                .email("alsghks@naver.com")
                .gender("M")
                .phoneNumber("1233-23423")
                .build();
        return userDto;
    }
}