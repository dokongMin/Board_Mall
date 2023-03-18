package com.dokong.board.service;

import com.dokong.board.domain.User;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.repository.dto.userdto.JoinUserDto;
import com.dokong.board.repository.dto.userdto.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(JoinUserDto userDto) {
        validateUsername(userDto);
        User user = userRepository.save(userDto.toEntity());
        return user.getId();
    }

    @Transactional
    public Long updateUser(UpdateUserDto userDto) {
        User user = checkValidUser(userDto);
        user.updateUser(userDto.getPassword(), userDto.getEmail(), userDto.getAddress());
        return user.getId();
    }

    private User checkValidUser(UpdateUserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> {
            throw new IllegalStateException("해당 회원은 존재하지 않습니다.");
        });
        return user;
    }

    private void validateUsername(JoinUserDto userDto) {
        boolean checkUsername = userRepository.findByUsername(userDto.getUsername()).isPresent();
        if (checkUsername) {
            throw new IllegalStateException("이미 존재하는 회원 아이디입니다.");
        }
    }
}
