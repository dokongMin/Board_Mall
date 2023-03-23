package com.dokong.board.web.service;

import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.userdto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public JoinUserResponseDto saveUser(JoinUserDto userDto) {
        validateUsername(userDto);
        return JoinUserResponseDto.of(userRepository.save(userDto.toEntity()));
    }

    @Transactional
    public UpdateUserResponseDto updateUser(UpdateUserDto userDto) {
        User user = checkExistUser(userDto);
        user.updateUser(userDto.getPassword(), userDto.getEmail(), userDto.getAddress());
        return UpdateUserResponseDto.of(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new NoExistUserException("해당 회원은 존재하지 않습니다.");
        });
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new NoExistUserException("해당 회원은 존재하지 않습니다.");
        });
    }

    public User checkExistUser(UpdateUserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername()).orElseThrow(() -> {
            throw new NoExistUserException("해당 회원은 존재하지 않습니다.");
        });
    }

    public void validateUsername(JoinUserDto userDto) {
        boolean checkUsername = userRepository.findByUsername(userDto.getUsername()).isPresent();
        if (checkUsername) {
            throw new IllegalStateException("이미 존재하는 회원 아이디입니다.");
        }
    }
}
