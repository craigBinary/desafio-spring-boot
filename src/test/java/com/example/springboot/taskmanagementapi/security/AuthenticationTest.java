package com.example.springboot.taskmanagementapi.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario testUser;

    @BeforeEach
    void setUp() {
        // Limpiar datos anteriores
        usuarioRepository.deleteAll();
        
        // Crear usuario de prueba
        testUser = new Usuario();
        testUser.setNombreUsuario("testuser");
        testUser.setClave(passwordEncoder.encode("password123"));
        testUser.setCorreo("testuser@test.com");
        usuarioRepository.save(testUser);
    }

    @Test
    void loginWithValidUserThenAuthenticated() throws Exception {
        Usuario loginUser = new Usuario();
        loginUser.setNombreUsuario("testuser");
        loginUser.setClave("password123");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void loginWithInvalidUserThenUnauthenticated() throws Exception {
        Usuario loginUser = new Usuario();
        loginUser.setNombreUsuario("invalid");
        loginUser.setClave("invalidpass");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void accessProtectedResourceUnauthenticatedThenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void accessProtectedResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void loginWithValidUserButWrongPasswordThenUnauthenticated() throws Exception {
        Usuario loginUser = new Usuario();
        loginUser.setNombreUsuario("testuser");
        loginUser.setClave("wrongpassword");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUser)))
                .andExpect(status().isUnauthorized());
    }
} 