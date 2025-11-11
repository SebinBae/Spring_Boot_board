package com.sebin.sebin_spring_boot_board.controller;

import com.sebin.sebin_spring_boot_board.dto.UserDto;
import com.sebin.sebin_spring_boot_board.entity.User;
import com.sebin.sebin_spring_boot_board.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    // 회원 가입 페이지
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("registerRequest", new UserDto.RegisterRequest());
        return "user/register";
    }

    // 회원 가입 처리
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") UserDto.RegisterRequest registerRequest,
                           BindingResult bindingResult, Model model){

        // 유효성 검사 실패시에 회원가입 페이지로 다시 이동함.
        if(bindingResult.hasErrors()){
            return "user/register";
        }

        try{
            userService.register(registerRequest);
            return "redirect:/user/login?registered";
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }
    }

    /**
     * 로그인 페이지 표시
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new UserDto.LoginRequest());
        return "user/login";
    }


    // 로그인 페이지 표시
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") UserDto.LoginRequest loginRequest,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        // 유효성 검사 실패 시 로그인 페이지로 다시 이동
        if (bindingResult.hasErrors()) {
            return "user/login";
        }

        // 로그인 처리
        Optional<User> userOptional = userService.login(loginRequest);

        if (userOptional.isPresent()) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            User user = userOptional.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());

            return "redirect:/board/list";
        } else {
            // 로그인 실패 시 에러 메시지 표시
            model.addAttribute("errorMessage", "사용자 이름 또는 비밀번호가 올바르지 않습니다.");
            return "user/login";
        }
    }

    /**
     * 로그아웃 처리
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션 무효화
        session.invalidate();
        return "redirect:/user/login?logout";
    }




}
