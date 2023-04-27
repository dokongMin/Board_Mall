package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.web.controller.SessionUserConst;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginUserDto loginUserDto, BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL, HttpSession session) {

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            return "login/loginForm";
        }

        session.setAttribute(SessionUserConst.LOGIN_MEMBER, sessionUserDto.getUsername());
        return "redirect:"+ redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login";
        }
//        SessionUserDto sessionUserDto = (SessionUserDto) session.getAttribute(SessionUserConst.LOGIN_MEMBER);
//        if (sessionUserDto == null) {
//            return "login";
//        }
        String sessionUser = (String) session.getAttribute(SessionUserConst.LOGIN_MEMBER);
        if (sessionUser == null) {
            return "login";
        }
        model.addAttribute("user", sessionUser);
        return "login";
    }

}
