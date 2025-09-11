package com.ifsc.tarefas.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    
    // pagina de login 
    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    // pagina de cadastro
    @GetMapping("/register")
    public String registerPage(){
        return "cadastro";
    }
}
