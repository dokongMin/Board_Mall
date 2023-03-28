package com.dokong.board.web.controller.restcontroller;

import com.dokong.board.domain.user.User;
import com.dokong.board.web.controller.SessionUserConst;
import com.dokong.board.web.dto.logindto.LoginUserDto;
import com.dokong.board.web.dto.userdto.SessionUserDto;
import com.dokong.board.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginUserDto loginUserDto, BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {

        SessionUserDto sessionUserDto = loginService.login(loginUserDto);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionUserConst.LOGIN_MEMBER, sessionUserDto);

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
            return "home";
        }
        SessionUserDto sessionUserDto = (SessionUserDto) session.getAttribute(SessionUserConst.LOGIN_MEMBER);
        if (sessionUserDto == null) {
            return "home";
        }
        model.addAttribute("user", sessionUserDto);
        return "loginHome";
    }
}
