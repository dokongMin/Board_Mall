package com.dokong.board.web.service;

import com.dokong.board.domain.user.User;
import com.dokong.board.exception.NoExistUserException;
import com.dokong.board.repository.UserRepository;
import com.dokong.board.web.dto.userdto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final UserRepository userRepository;

    public SessionUserDto login(LoginUserDto userDto) {

        User user = checkExistUser(userDto);

        return SessionUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    private User checkExistUser(LoginUserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername())
                .filter(u -> u.getPassword().equals(userDto.getPassword()))
                .orElseThrow(() -> {
                    throw new NoExistUserException("일치하는 사용자가 없습니다.");
                });
    }
}
