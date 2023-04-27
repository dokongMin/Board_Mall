package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.user.User;
import com.dokong.board.repository.user.UserRepository;
import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.userdto.*;
import com.dokong.board.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Tag(name = "User", description = "User API Document")
public class RestUserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @Operation(summary = "회원 저장 API")
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

    @Operation(summary = "회원 조회 API")
    @GetMapping("/my-page/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User user = userService.findById(id);
        ShowUserDto showUserDto = ShowUserDto.of(user);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(showUserDto)
                .build();
        return ResponseEntity.status(SuccessCode.CREATE_REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    @Operation(summary = "회원 수정 API")
    @PostMapping("/my-page/modify/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserDto updateUserDto, BindingResult bindingResult) {

        bindingRuntimeException(bindingResult);

        UpdateUserResponseDto updateUserResponseDto = userService.updateUser(id, updateUserDto);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.UPDATE_REQUEST_SUCCESS.getMessage())
                .body(updateUserResponseDto)
                .build();
        return ResponseEntity.status(SuccessCode.UPDATE_REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    @Operation(summary = "회원 삭제 API")
    @PostMapping("/my-page/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long userId = userService.deleteUser(id);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(userId)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    @Operation(summary = "회원 비밀번호 찾기 API")
    @GetMapping("/find-pw/{username}")
    public ResponseEntity<?> findUserByPassword(@PathVariable String username) {
        User user = userService.findByUsername(username);
        UserPasswordDto passwordDto = UserPasswordDto.of(user);
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(passwordDto)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    @Operation(summary = "회원 전체 조회 API")
    @GetMapping("/user-list")
    public ResponseEntity<?> findAllUser() {
        List<User> allUser = userService.findAllUser();
        List<ShowUserDto> collect = allUser.stream()
                .map(a -> ShowUserDto.of(a))
                .collect(Collectors.toList());
        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(collect)
                .build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    @Operation(summary = "회원 검색 API")
    @GetMapping("/search")
    public List<SearchUserDto> searchUser(UserSearchCondition condition) {
        return userRepository.search(condition);
    }

    @Operation(summary = "회원 페이징 API")
    @GetMapping("/user-list/page")
    public Page<SearchUserDto> searchUserPage(UserSearchCondition condition, Pageable pageable) {
        return userRepository.searchPageComplex(condition, pageable);
    }

    private void bindingRuntimeException(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
    }
}
