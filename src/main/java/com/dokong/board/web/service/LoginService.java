package com.dokong.board.web.service;

import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.repository.user.UserRepository;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    public SessionUserDto login(LoginUserDto userDto) {

        User user = loginCheck(userDto);

        return SessionUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    private User loginCheck(LoginUserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername())
                .filter(u -> u.getPassword().equals(userDto.getPassword()))
                .orElseThrow(() -> {
                    throw new NoExistUserException("아이디 또는 비밀번호가 맞지 않습니다.");
                });
    }
}
