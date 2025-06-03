package com.example.springboot.taskmanagementapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

import com.example.springboot.taskmanagementapi.dto.UsuarioDTO;
import com.example.springboot.taskmanagementapi.entities.Usuario;
import com.example.springboot.taskmanagementapi.repositories.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        // Limpiar datos anteriores
        usuarioRepository.deleteAll();
        
        // Configurar datos de prueba
        usuario = new Usuario();
        usuario.setNombreUsuario("testuser");
        usuario.setCorreo("test@example.com");
        usuario.setClave(passwordEncoder.encode("password123"));
        usuario = usuarioRepository.save(usuario);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombreUsuario("newuser");
        usuarioDTO.setCorreo("newuser@example.com");
        usuarioDTO.setClave("newpassword123");
    }

    @Test
    @WithMockUser(roles = "USER")
    void listShouldReturnUsuarios() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$[0].correo").value("test@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createShouldReturnNewUsuario() throws Exception {
        mockMvc.perform(post("/api/v1/usuarios")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreUsuario").value("newuser"))
                .andExpect(jsonPath("$.correo").value("newuser@example.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void createShouldReturnErrorWhenUsuarioExists() throws Exception {
        usuarioDTO.setNombreUsuario("testuser");
        mockMvc.perform(post("/api/v1/usuarios")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Mensaje").value("Usuario ya existe"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateShouldReturnUpdatedUsuario() throws Exception {
        usuarioDTO.setNombreUsuario("updateduser");
        mockMvc.perform(put("/api/v1/usuarios/" + usuario.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreUsuario").value("updateduser"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteShouldReturnDeletedUsuario() throws Exception {
        mockMvc.perform(delete("/api/v1/usuarios/" + usuario.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void viewShouldReturnUsuario() throws Exception {
        mockMvc.perform(get("/api/v1/usuarios/" + usuario.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsuario").value("testuser"))
                .andExpect(jsonPath("$.correo").value("test@example.com"));
    }
} 