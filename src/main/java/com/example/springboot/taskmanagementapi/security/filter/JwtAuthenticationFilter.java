package com.example.springboot.taskmanagementapi.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.example.springboot.taskmanagementapi.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;  

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
           
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Usuario usuario = null;
        String nombre = null;
        String clave = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(),Usuario.class);
            nombre = usuario.getNombreUsuario();
            clave = usuario.getClave();
        } catch (StreamReadException e) {            
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {            
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nombre, clave);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
            String usuario = user.getUsername();

            List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            
             Claims claims = Jwts.claims()
             .add("authorities", new ObjectMapper().writeValueAsString(roles))
             .add("username", usuario)             
             .build();        

            String token = Jwts.builder()
            .subject(usuario)
            .claims(claims)
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .issuedAt(new Date())
            .signWith(SECRET_KEY)
            .compact();

            response.addHeader(HEADER_AUTHOTIZATION, PREFIX_TOKEN + token);

            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            body.put("username", usuario);
            body.put("mensaje", String.format("Hola %s has iniciado sesion con exito.", usuario));

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
       Map<String, String> body = new HashMap<>();
       body.put("mensaje", "Error en la autenticacion, nombre o clave incorrectos!");
       body.put("Error", failed.getMessage());
       
       response.getWriter().write(new ObjectMapper().writeValueAsString(body));
       response.setStatus(401);
       response.setContentType(CONTENT_TYPE);
    }
}
