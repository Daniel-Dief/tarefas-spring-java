package com.ifsc.tarefas.auth;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    // final, não da de mudar ;)
    private final AuthService authService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final JwtUtil jwtUtil;

    public AuthFilter(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    // apis que podem acessar sem o token
    private static final Set<String> PUBLIC_PATTERNS = Set.of(
        "/login", 
        "/login/**", 
        "/register", 
        "/register/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/webjars/**",
        "/h2-console/**",
        "/"
    );

    // Filtra as requisições que precisam de autenticação, algumas não precisam exemplo o login
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        // procura nas urls publicas e se encontrar retorna true pode passar sem autenticação
        for(String url: PUBLIC_PATTERNS) {
            if(antPathMatcher.match(url, path)) {
                return true;
            }
        }

        // permitir o OPTIONS, exemplo o filtro de cors
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // se nao encontrar retorna false e precisa autenticação
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        // pega o token
        String token = extractTokenFromCookie(request, "AUTH_TOKEN");

        // Se não achar no cookie, verificar se existe no header
        if(token == null || token.isBlank()) {
            String authHeader = request.getHeader("Authorization");
            // se achou o token e ele começa com o "Bearer "
            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                // Removemos o bearer da string
                token = authHeader.substring(7);
            }
     
        }

    }

    // função para extrair os cookies
    private String extractTokenFromCookie(HttpServletRequest request, String cookieAutentificacao) {
        // pega oq o usuario enviou e especificamente pega os cookies
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;

        for(Cookie cookie : cookies) {
            // se encontrar o cookie de autenticação retorna ele 
            if(cookieAutentificacao.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        // se nao encontrar o cookie de autentificação, retorna null
        return null;
        
    }


}
