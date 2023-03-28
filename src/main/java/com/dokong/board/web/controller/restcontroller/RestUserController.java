package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.user.User;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.userdto.*;
import com.dokong.board.web.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class RestUserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> joinUser(@RequestBody @Validated JoinUserDto joinUserDto, BindingResult bindingResult) {

        bindingRuntimeException(bindingResult);

        JoinUserResponseDto joinUserResponseDto = userService.saveUser(joinUserDto);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.CREATE_REQUEST_SUCCESS.getMessage())
                .body(joinUserResponseDto)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/my-page/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User user = userService.findById(id);
        ShowUserDto showUserDto = ShowUserDto.of(user);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(showUserDto)
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponseDto);
    }

    @PostMapping("/my-page/modify/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserDto updateUserDto, BindingResult bindingResult) {

        bindingRuntimeException(bindingResult);

        UpdateUserResponseDto updateUserResponseDto = userService.updateUser(id, updateUserDto);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(updateUserResponseDto)
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponseDto);
    }

    @PostMapping("/my-page/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long userId = userService.deleteUser(id);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(userId)
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponseDto);
    }

    @GetMapping("/find-pw/{username}")
    public ResponseEntity<?> findUserByPassword(@PathVariable String username) {
        User user = userService.findByUsername(username);
        UserPasswordDto passwordDto = UserPasswordDto.of(user);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(passwordDto)
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commonResponseDto);
    }



    private void bindingRuntimeException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
    }
}
