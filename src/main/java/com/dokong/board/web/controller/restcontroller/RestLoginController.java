package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.CommonResponseDto;
import com.dokong.board.web.controller.SessionUserConst;
import com.dokong.board.web.controller.SuccessCode;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class RestLoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> loginRest(@Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult, HttpServletRequest request) {

        bindingIllegalArgumentException(bindingResult);

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);
        HttpSession session = request.getSession();
        session.setAttribute(SessionUserConst.LOGIN_MEMBER, sessionUserDto);

        CommonResponseDto<Object> commonResponseDto = CommonResponseDto.builder()
                .code(SuccessCode.REQUEST_SUCCESS.getHttpStatus())
                .msg(SuccessCode.REQUEST_SUCCESS.getMessage())
                .body(sessionUserDto).build();
        return ResponseEntity.status(SuccessCode.REQUEST_SUCCESS.getHttpStatus()).body(commonResponseDto);
    }

    private void bindingIllegalArgumentException(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new IllegalArgumentException(errorMap.toString());
        }
    }
}
