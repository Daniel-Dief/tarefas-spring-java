package com.ifsc.tarefas.auth;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

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
    public String registerPage() {
        return "cadastro";
    }

    // API QUE VAI FZR O LOGIN
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
            @RequestParam String password,
            @RequestParam(name = "redirect", required = false) String redirect, Model model,
            HttpServletResponse response) {


            Optional<String> token = authRepository.login(username, password);
            if(token.isEmpty()){
                model.addAttribute("error", "Login ou senha incorretos");
                model.addAttribute("redirect", redirect);
                return "login";
            }

            String tokenValue = token.get();

            Cookie cookie = new Cookie("AUTH_TOKEN", tokenValue);
            
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            String target = (redirect == null || redirect.isBlank()) ? "/templates" : redirect;

            if(target.contains("://")){
                target = "/templates";
            }

            if(!target.startsWith("/")) {
                target = "/" + target;
            }

            return "redirect:" + redirect ;
    }

}
