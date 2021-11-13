package edu.ifam.aranoua.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifam.aranoua.security.domain.Credencial;
import edu.ifam.aranoua.security.domain.JwtUser;
import edu.ifam.aranoua.security.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtil jwtUtil;
    private AuthenticationManager authManager;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtUtil jwtUtil){
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
        try {
            Credencial credencial = new ObjectMapper().readValue(request.getInputStream(), Credencial.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credencial.getCpf(), credencial.getSenha(), new ArrayList<>());

            Authentication auth = authManager.authenticate(authToken);
            return auth;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException{
        String cpf = ((JwtUser) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.gerarToken(cpf);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
